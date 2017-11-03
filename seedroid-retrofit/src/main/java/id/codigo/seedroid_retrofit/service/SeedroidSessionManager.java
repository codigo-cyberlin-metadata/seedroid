package id.codigo.seedroid_retrofit.service;

/**
 * Created by papahnakal on 31/10/17.
 */

public interface SeedroidSessionManager {
    void setAuthorization(String authorization);
    String getAutorization();
    void clear();
}
