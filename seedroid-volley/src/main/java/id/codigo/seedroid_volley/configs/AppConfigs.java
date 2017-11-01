package id.codigo.seedroid_volley.configs;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.codigo.seedroid_volley.helper.JsonHelper;

/**
 * Created by Low on 11/1/17.
 */
public class AppConfigs {
    // value of authentication basic auth
    public static boolean isDebugMode = false;

    // value of Json Mapper config
    public static ObjectMapper jsonMapper() {
        return JsonHelper.getInstance().getObjectMapper();
    }
}
