package id.codigo.seedroid.service;

import java.util.HashMap;
import java.util.Map;

import id.codigo.seedroid.configs.RestConfigs;
import id.codigo.seedroid.helper.AuthHelper;
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

        Map<String, String> parameters = new HashMap<>();
        parameters.put(RestConfigs.userIdUrlParameter, AuthHelper.getUserId());
        parameters.put(RestConfigs.userAccessTokenUrlParameter, AuthHelper.getUserAccessToken());

        HttpHelper.getInstance().post(url, parameters, listener);
    }
}
