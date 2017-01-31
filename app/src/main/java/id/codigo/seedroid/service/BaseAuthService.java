package id.codigo.seedroid.service;

import java.util.HashMap;
import java.util.Map;

import id.codigo.seedroid.ApplicationMain;
import id.codigo.seedroid.R;
import id.codigo.seedroid.configs.RestConfigs;
import id.codigo.seedroid.helper.AuthHelper;
import id.codigo.seedroid.helper.HttpHelper;
import id.codigo.seedroid.helper.JsonHelper;
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

        HttpHelper.getInstance().post(url, parameters, new ServiceListener<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    listener.onSuccess((BaseAuthModel) JsonHelper.getInstance().toObject(response, BaseAuthModel.class));
                } catch (Exception e) {
                    onFailed(ApplicationMain.getInstance().getString(R.string.status_failed));
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String message) {
                listener.onFailed(message);
            }
        });
    }
}
