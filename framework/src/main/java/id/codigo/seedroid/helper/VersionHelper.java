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
    private ServiceListener<BaseVersionModel> callback;
    private static VersionHelper instance;

    public VersionHelper(String appVersion, String appId){
        this.context = SeedroidApplication.getInstance();
        this.appVersion = appVersion;
        this.appId = appId;
    }
    private VersionHelper() {
        context = SeedroidApplication.getInstance();
    }
    public static synchronized VersionHelper getInstance() {
        if (instance == null) {
            instance = new VersionHelper();
        }
        return instance;
    }
    private void getVersionApps(ServiceListener<BaseVersionModel> callback){
        HttpHelper.getInstance().get(
                "http://10.4.3.239:8080" +
                        "/appmanager/mobile/api/v1/version?"+
                        "app_id="+appId+
                        "&app_version="+appVersion+
                        "&app_platform=android",
                callback
        );

    }
    public void get (){
        getVersionApps(new ServiceListener<BaseVersionModel>() {
            @Override
            public void onSuccess(BaseVersionModel data) {
                if(data.getStatus().toString().equalsIgnoreCase("200")) {
                    if (data.getMetadata().getForceUpdate() == true) {
                        Intent update = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getMetadata().getLinkUpdate()));
                        update.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(update);
                    } else {
                        Log.d("seedDroid","no update version!");
                    }
                }else{
                    Log.d("seedDroid","unknown error!"+" || "+data.getMessage());
                }
            }

            @Override
            public void onFailed(String message) {
                Log.d("seedDroid","onFailed!"+ " || "+message);
            }
        });
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
