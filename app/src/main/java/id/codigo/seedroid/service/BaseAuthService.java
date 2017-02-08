package id.codigo.seedroid.service;

import java.util.HashMap;

import id.codigo.seedroid.configs.RestConfigs;
import id.codigo.seedroid.helper.HttpHelper;
import id.codigo.seedroid.model.json.auth.BaseAuthModel;

/**
 * Created by Gayo on 1/24/2017.
 */

public class BaseAuthService extends BaseService {
    /**
     * Access API refresh token
     *
     * @param listener Callback response from API
     */
    public static void refreshToken(final ServiceListener<BaseAuthModel> listener) {
        String url = RestConfigs.refreshTokenUrl;
        HttpHelper.getInstance().post(url, new HashMap<String, String>(), listener);
    }
}
