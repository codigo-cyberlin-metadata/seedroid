package id.codigo.seedroid.service;

/**
 * Created by Lukma on 3/29/2016.
 */
public class BaseRestService {
    public interface RestServiceListener<T> {
        void onReceive(boolean status, String message, T data);
    }
}
