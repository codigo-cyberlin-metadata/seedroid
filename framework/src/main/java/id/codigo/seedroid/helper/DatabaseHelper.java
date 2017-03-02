package id.codigo.seedroid.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import id.codigo.seedroid.SeedroidApplication;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Gayo on 3/2/2017.
 */
public class DatabaseHelper<T extends RealmObject> {
    private Realm realm;
    private Class<T> type;

    private DatabaseHelper() {
        Realm.init(SeedroidApplication.getInstance());
        realm = Realm.getDefaultInstance();

        Type type = getClass().getGenericSuperclass();

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != DatabaseHelper.class) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }

        this.type = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    /**
     * Retrieve list of data from db
     *
     * @param listener Listener when query to db
     */
    public RealmResults<T> finds(DatabaseReadListener<T> listener) {
        RealmQuery<T> query = realm.where(type);
        listener.onQuery(query);
        return query.findAll();
    }

    /**
     * Retrieve one of data by condition from db
     *
     * @param findBy    Name of field to compare
     * @param findWhere Value of field to compare
     */
    public T find(String findBy, final String findWhere) {
        RealmQuery<T> query = realm.where(type);
        query.equalTo(findBy, findWhere);
        return query.findFirst();
    }

    /**
     * Insert or update data to db
     *
     * @param data Data to insert or update
     */
    public void insertOrUpdate(final T data) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
    }

    /**
     * Delete one of data from db
     *
     * @param deleteBy    Name of field to compare
     * @param deleteWhere Value of field to compare
     */
    public void delete(final String deleteBy, final String deleteWhere) {
        realm.beginTransaction();
        T result = realm.where(type).equalTo(deleteBy, deleteWhere).findFirst();
        result.deleteFromRealm();
        realm.commitTransaction();
    }

    public abstract class DatabaseReadListener<T extends RealmObject> {
        public abstract void onQuery(RealmQuery query);
    }
}
