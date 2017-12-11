package id.codigo.seedroid_core.helper;

//import java.util.ArrayList;
//
//import id.codigo.seedroid.model.db.AutoIncrementModel;
//import io.realm.Realm;
//import io.realm.RealmObject;
//import io.realm.RealmQuery;
//import io.realm.RealmResults;

/**
 * Created by Gayo on 3/2/2017.
 */
public class DatabaseHelper
//        <T extends RealmObject>
{
//    private Realm realm;
//    private Class<T> type;
//
//    public DatabaseHelper(Class<T> type) {
//        realm = Realm.getDefaultInstance();
//
//        this.type = type;
//    }
//
//    /**
//     * Retrieve list of data from db
//     *
//     * @param listener Listener when query to db
//     */
//    public RealmResults<T> finds(DatabaseReadListener listener) {
//        RealmQuery<T> query = realm.where(type);
//        listener.onQuery(query);
//        return query.findAll();
//    }
//
//    /**
//     * Retrieve list of data from db
//     *
//     * @param limit    Parameter limit to send
//     * @param offset   Parameter offset to send
//     * @param listener Listener when query to db
//     */
//    public RealmResults<T> finds(int limit, int offset, DatabaseReadListener listener) {
//        RealmQuery<T> query = realm.where(type).between("id", offset, offset + limit + 1);
//        listener.onQuery(query);
//        return query.findAll();
//    }
//
//    /**
//     * Retrieve one of data by condition from db
//     *
//     * @param findBy    Name of field to compare
//     * @param findWhere Value of field to compare
//     */
//    public T find(String findBy, final String findWhere) {
//        RealmQuery<T> query = realm.where(type);
//        query.equalTo(findBy, findWhere);
//        return query.findFirst();
//    }
//
//    /**
//     * Insert or update data to db
//     *
//     * @param data Data to insert or update
//     */
//    public void insertOrUpdate(final T data) {
//        realm.beginTransaction();
//
//        Integer maxId = 1;
//        try {
//            maxId = realm.where(type).max("id").intValue();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (((AutoIncrementModel) data).getId() != null && ((AutoIncrementModel) data).getId() > maxId) {
//            ((AutoIncrementModel) data).setId(null);
//        }
//
//        if (((AutoIncrementModel) data).getId() == null) {
//            ((AutoIncrementModel) data).setId(maxId + 1);
//        }
//
//        realm.copyToRealmOrUpdate(data);
//
//        realm.commitTransaction();
//    }
//
//    /**
//     * Insert multiple data to db
//     *
//     * @param data Data to insert or update
//     */
//    public void insertOrUpdateMultiple(final ArrayList<T> data) {
//        realm.beginTransaction();
//
//        Integer maxId = 1;
//        try {
//            maxId = realm.where(type).max("id").intValue();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        int length = data.size();
//        for (int i = 0; i < length; i++) {
//            if (((AutoIncrementModel) data.get(i)).getId() != null && ((AutoIncrementModel) data.get(i)).getId() > maxId) {
//                ((AutoIncrementModel) data.get(i)).setId(null);
//            }
//
//            if (((AutoIncrementModel) data.get(i)).getId() == null) {
//                ((AutoIncrementModel) data.get(i)).setId(maxId + 1);
//            }
//        }
//
//        realm.copyToRealmOrUpdate(data);
//
//        realm.commitTransaction();
//    }
//
//    /**
//     * Delete one of data from db
//     *
//     * @param deleteBy    Name of field to compare
//     * @param deleteWhere Value of field to compare
//     */
//    public void delete(final String deleteBy, final String deleteWhere) {
//        realm.beginTransaction();
//        T result = realm.where(type).equalTo(deleteBy, deleteWhere).findFirst();
//        result.deleteFromRealm();
//        realm.commitTransaction();
//    }
//
//    /**
//     * Delete multiple of data from db
//     *
//     * @param deleteBy    Name of field to compare
//     * @param deleteWhere Value of field to compare
//     */
//    public void deleteMultiple(final String deleteBy, final String deleteWhere) {
//        realm.beginTransaction();
//        RealmResults<T> result = realm.where(type).equalTo(deleteBy, deleteWhere).findAll();
//        result.deleteAllFromRealm();
//        realm.commitTransaction();
//    }
//
//    public interface DatabaseReadListener {
//        void onQuery(RealmQuery query);
//    }
}
