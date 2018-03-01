package id.codigo.seedroid_uikit.sdkutils;

import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by papahnakal on 01/03/18.
 */

public class JsonParse {
    private static final String TAG = JsonParse.class.getSimpleName();
    private static ObjectMapper mapper = new ObjectMapper();

    private JsonParse() {
    }

    public static <T> byte[] toJsonByte(T object) {
        String jsonString = toJsonString(object);
        return null == jsonString?null:jsonString.getBytes();
    }

    public static <T> String toJsonString(T object) {
        String result = null;

        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException var3) {
            Log.e(TAG, object.getClass().getName() + "to json  string exception");
            Log.e(TAG, var3.toString());
        }

        return result;
    }

    public static <T> T toObject(Class<T> klass, String json) {
        try {
            return toObjectCore(json, klass);
        } catch (JsonMappingException var3) {
            Log.e(TAG, klass.getClass().getName() + "to object exception");
            Log.w(TAG, var3.toString());
            return null;
        }
    }

    public static <T> T toObjectCore(String json, Class<T> klass) throws JsonMappingException {
        try {
            return mapper.readValue(json, klass);
        } catch (JsonMappingException var5) {
            Log.w(TAG, var5.toString());

            try {
                return mapper.readValue(redressJson(json), klass);
            } catch (IOException var4) {
                Log.e(TAG, klass.getClass().getName() + " all parseException to JsonMappingException");
                Log.w(TAG, var4.toString());
                throw new JsonMappingException("all parseException to JsonMappingException", var4);
            }
        } catch (IOException var6) {
            Log.e(TAG, klass.getClass().getName() + " all parseException to JsonMappingException");
            Log.w(TAG, var6);
            throw new JsonMappingException("all parseException to JsonMappingException", var6);
        }
    }

    public static <T> ArrayList<String> toJsonList(ArrayList<T> objectList) {
        ArrayList<String> jsonList = new ArrayList();

        for(int i = 0; i < objectList.size(); ++i) {
            jsonList.add(toJsonString(objectList.get(i)));
        }

        return jsonList;
    }

    public static <T> List<T> toObjectList(List<String> jsonList, Class<T> elementClass) {
        List<T> objectList = new ArrayList();

        for(int i = 0; i < jsonList.size(); ++i) {
            objectList.add(toObject(elementClass, (String)jsonList.get(i)));
        }

        return objectList;
    }

    private static String redressJson(String json) {
        if(TextUtils.isEmpty(json)) {
            return json;
        } else {
            String regular = "[^\"}\\]],\"[^:]";
            Pattern pattern = Pattern.compile(regular);
            Matcher matcher = pattern.matcher(json);
            StringBuffer sb = new StringBuffer();
            int startPosition = 0;

            int endPosition;
            for(endPosition = 0; matcher.find(); startPosition = endPosition) {
                endPosition = matcher.start() + 1;
                sb.append(json.substring(startPosition, endPosition) + "\"");
            }

            if(endPosition < json.length()) {
                sb.append(json.substring(endPosition));
            }

            return sb.toString();
        }
    }

    public static List jsonToList(String json, TypeReference valueTypeRef) {
        Object objectList = new ArrayList();

        try {
            if(!TextUtils.isEmpty(json)) {
                objectList = (List)mapper.readValue(json, valueTypeRef);
            }
        } catch (IOException var4) {
            Log.e(TAG, "lul");
            Log.e(TAG, var4.toString());
        }

        return (List)objectList;
    }

    public static String listToJson(List list) {
        String json = null;

        try {
            json = mapper.writeValueAsString(list);
        } catch (JsonProcessingException var3) {
            Log.e(TAG, "lul");
            Log.e(TAG, var3.toString());
        }

        return json;
    }
}
