package id.codigo.seedroid_core.configs;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.codigo.seedroid_core.helper.JsonHelper;

/**
 * Created by papahnakal on 12/10/17.
 */

public class AppConfigs {
    // value of authentication basic auth
    public static boolean isDebugMode = false;

    // value of Json Mapper config
    public static ObjectMapper jsonMapper() {
        return JsonHelper.getInstance().getObjectMapper();
    }
}
