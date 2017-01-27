package id.codigo.seedroid.service;

import java.io.IOException;
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

public class BaseAuthService extends BaseRestService {
    /**
     * Access API refresh token
     *
     * @param listener Callback response from API
     */
    public static void refreshToken(final RestServiceListener<BaseAuthModel> listener) {
        String url = RestConfigs.URL_REFRESH_TOKEN;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("user_id", AuthHelper.getUserId());
        parameters.put("user_access_token", AuthHelper.getUserAccessToken());

        HttpHelper.getInstance().post(url, parameters, new HttpHelper.HttpListener<String>() {
            @Override
            public void onReceive(boolean status, String message, String response) {
                if (status) {
                    try {
                        listener.onReceive(true, message, (BaseAuthModel) JsonHelper.getInstance()
                                .toObject(response, BaseAuthModel.class));
                    } catch (IOException e) {
                        listener.onReceive(false, ApplicationMain.getInstance().getString(R.string.status_failed), null);
                    }
                } else {
                    listener.onReceive(false, message, null);
                }
            }
        });
    }
}
