package id.codigo.seedroid_volley.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Low on 11/1/17.
 */

public abstract class ServiceMultipartListener<T> {

    private Class<T> type;

    public ServiceMultipartListener(){
        Type type = getClass().getGenericSuperclass();

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != ServiceMultipartListener.class) {
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

    public abstract void onProgress(int progressPercent, long uploadedBytes, double getUploadRate);

    public abstract void onCanceled();

    public void onFailed(String message, int httpCode, String body) {
    }
}
