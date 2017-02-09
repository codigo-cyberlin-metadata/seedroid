package id.codigo.seedroid.service;

/**
 * Created by Lukma on 3/29/2016.
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
