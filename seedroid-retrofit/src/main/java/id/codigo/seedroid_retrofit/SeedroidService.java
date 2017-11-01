package id.codigo.seedroid_retrofit;

import javax.net.ssl.HostnameVerifier;

/**
 * Created by papahnakal on 31/10/17.
 */

public class SeedroidService {
    public static <I> I create(String HOST,Class<I> serviceClient) {
        return SeedroidServiceGenerator.createService(HOST,serviceClient);
    }

    public static <I> I createWithAuth(String HOST,Class<I> serviceClient , SeedroidSessionManager sessionManager) {
        return SeedroidServiceGenerator.createService(HOST,serviceClient , sessionManager);
    }

    public static <I> I ceateWithAuthAndArray(String HOST,Class<I> serviceClient,SeedroidSessionManager sessionManager) {
        return SeedroidServiceGenerator.createService(HOST,serviceClient, sessionManager);
    }
}
