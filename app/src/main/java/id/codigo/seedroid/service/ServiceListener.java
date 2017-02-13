package id.codigo.seedroid.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Gayo on 1/31/2017.
 */
public abstract class ServiceListener<T> {
    private Class<T> type;

    public ServiceListener() {
        Type type = getClass().getGenericSuperclass();

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != ServiceListener.class) {
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

    public abstract void onSuccess(T data);

    public abstract void onFailed(String message);

    public void onFailed(String message, int httpCode, String body) {
    }
}
