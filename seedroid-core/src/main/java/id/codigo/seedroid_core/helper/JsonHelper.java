package id.codigo.seedroid_core.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Lukma on 3/29/2016.
 */
public class JsonHelper {
    private static final String TAG = JsonHelper.class.getSimpleName();

    private static JsonHelper instance;

    private ObjectMapper objectMapper;

    private JsonHelper() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static JsonHelper getInstance() {
        if (instance == null) {
            instance = new JsonHelper();
        }
        return instance;
    }

    /**
     * Convert json to object
     *
     * @param content   Json string
     * @param valueType Object type
     */
    public <T> T toObject(String content, Class<T> valueType) throws IOException {
        LogHelper.i(TAG, "content : " + content);
        return objectMapper.readValue(content, valueType);
    }

    /**
     * Convert object to json
     *
     * @param value Object type
     */
    public <T> String toString(T value) throws IOException {
        return objectMapper.writeValueAsString(value);
    }

    /**
     * Getter objectMapper
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
