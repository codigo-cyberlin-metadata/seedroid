package id.codigo.seedroid.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import id.codigo.seedroid.configs.RestConfigs;
import id.codigo.seedroid.model.json.version.BaseVersionModel;
import id.codigo.seedroid.service.ServiceListener;

/**
 * Created by papahnakal on 22/06/17.
 */

public class VersionHelper {
    private Context CONTEXT;
    private String APP_VERSION;
    private String APP_ID;
    public VersionHelper(final Context CONTEXT, String APP_VERSION, String APP_ID){
        this.CONTEXT = CONTEXT;
        this.APP_VERSION = APP_VERSION;
        this.APP_ID = APP_ID;
        getVersionApps(new ServiceListener<BaseVersionModel>() {
            @Override
            public void onSuccess(BaseVersionModel data) {
                if(data.getStatus().toString().equalsIgnoreCase("200")) {
                    if (data.getMetadata().getForceUpdate() == true) {
                        CONTEXT.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(data.getMetadata().getLinkUpdate())));
                    } else {
                        Log.d("seedDroid","no update version!");
                    }
                }else{
                    Log.d("seedDroid","unknown error!");
                }
            }

            @Override
            public void onFailed(String message) {
                Log.d("seedDroid","onFailed!");
            }
        });
    }
    public void getVersionApps(ServiceListener<BaseVersionModel> callback){
        HttpHelper.getInstance().get(
                "http://10.4.3.239:8080" +
                        "/appmanager/mobile/api/v1/version?"+
                        "app_id="+APP_ID+
                        "&app_version="+APP_VERSION+
                        "&app_platform=android",
                callback
        );

    }

}
