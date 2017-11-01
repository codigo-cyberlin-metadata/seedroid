package id.codigo.seedroid_volley.service;

/**
 * Created by Low on 11/1/17.
 */
public class BaseService {
    private static BaseService instance;

    public static synchronized BaseService getInstance() {
        if (instance == null) {
            instance = new BaseService();
        }
        return instance;
    }
}
