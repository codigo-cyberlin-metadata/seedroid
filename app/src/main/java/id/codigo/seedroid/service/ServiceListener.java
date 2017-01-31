package id.codigo.seedroid.service;

/**
 * Created by Gayo on 1/31/2017.
 */
public interface ServiceListener<T> {
    void onSuccess(T data);

    void onFailed(String message);
}
