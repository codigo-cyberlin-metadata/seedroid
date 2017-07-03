package id.codigo.seedroid.configs;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.codigo.seedroid.helper.JsonHelper;

/**
 * Created by Gayo on 5/23/2017.
 */
public class AppConfigs {
    // value of authentication basic auth
    public static boolean isDebugMode = false;

    // value of Json Mapper config
    public static ObjectMapper jsonMapper() {
        return JsonHelper.getInstance().getObjectMapper();
    }
}
