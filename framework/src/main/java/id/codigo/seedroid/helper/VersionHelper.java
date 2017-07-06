package id.codigo.seedroid.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import id.codigo.seedroid.SeedroidApplication;
import id.codigo.seedroid.configs.RestConfigs;
import id.codigo.seedroid.model.json.version.BaseVersionModel;
import id.codigo.seedroid.service.ServiceListener;

/**
 * Created by papahnakal on 22/06/17.
 */

public class VersionHelper {
    private Context context;
    private String appVersion;
    private String appId;

    public VersionHelper(String appVersion, String appId){
        this.context = SeedroidApplication.getInstance();
        this.appVersion = appVersion;
        this.appId = appId;
        getVersionApps(new ServiceListener<BaseVersionModel>() {
            @Override
            public void onSuccess(BaseVersionModel data) {
                if(data.getStatus().toString().equalsIgnoreCase("200")) {
                    if (data.getMetadata().getForceUpdate() == true) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(data.getMetadata().getLinkUpdate())));
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
                        "app_id="+appId+
                        "&app_version="+appVersion+
                        "&app_platform=android",
                callback
        );

    }
    public VersionHelper getInstance(){
        this.context = SeedroidApplication.getInstance();
        return this;
    }
    public VersionHelper setAppVersion(String appVersion){
        this.appVersion = appVersion;
        return this;
    }
    public VersionHelper setAppID(String appId){
        this.appId = appId;
        return this;
    }

}
