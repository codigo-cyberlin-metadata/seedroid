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

    private DatabaseHelper() {
        Realm.init(SeedroidApplication.getInstance());
        realm = Realm.getDefaultInstance();
    }

    public RealmResults<T> gets(DatabaseReadListener<T> listener) {
        RealmQuery<T> query = realm.where(listener.getType());
        listener.onQuery(query);
        return query.findAll();
    }

    public void insertOrUpdate(final T row, final DatabaseWriteListener<T> listener) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(row);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.onFailed(error.getLocalizedMessage());
            }
        });
    }

    public void delete(final String deleteBy, final String selector, final DatabaseWriteListener<T> listener) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                T result = realm.where(listener.getType()).equalTo(deleteBy, selector).findFirst();
                result.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.onFailed(error.getLocalizedMessage());
            }
        });
    }

    public abstract class DatabaseWriteListener<T extends RealmObject> {
        private Class<T> type;

        public DatabaseWriteListener() {
            Type type = getClass().getGenericSuperclass();

            while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != DatabaseWriteListener.class) {
                if (type instanceof ParameterizedType) {
                    type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
                } else {
                    type = ((Class<?>) type).getGenericSuperclass();
                }
            }

            this.type = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }

        public Class<T> getType() {
            return type;
        }

        public abstract void onSuccess();

        public abstract void onFailed(String message);
    }

    public abstract class DatabaseReadListener<T extends RealmObject> {
        private Class<T> type;

        public DatabaseReadListener() {
            Type type = getClass().getGenericSuperclass();

            while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != DatabaseWriteListener.class) {
                if (type instanceof ParameterizedType) {
                    type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
                } else {
                    type = ((Class<?>) type).getGenericSuperclass();
                }
            }

            this.type = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }

        public Class<T> getType() {
            return type;
        }

        public abstract void onQuery(RealmQuery query);
    }
}
