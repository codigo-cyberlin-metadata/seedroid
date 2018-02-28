package id.codigo.seedroid_retrofit.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by papahnakal on 26/02/18.
 */

public class ItemTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (type.getRawType()!= Data.class) return null;

        TypeAdapter<BaseResponse> defaultAdapter = (TypeAdapter<BaseResponse>) gson.getDelegateAdapter(this, type);
        return (TypeAdapter<T>) new MyResultObjectAdapter(defaultAdapter);
    }

    public class MyResultObjectAdapter extends TypeAdapter<BaseResponse> {

        protected TypeAdapter<BaseResponse> defaultAdapter;


        public MyResultObjectAdapter(TypeAdapter<BaseResponse> defaultAdapter) {
            this.defaultAdapter = defaultAdapter;
        }

        @Override
        public void write(JsonWriter out, BaseResponse value) throws IOException {
            defaultAdapter.write(out, value);
        }

        @Override
        public BaseResponse read(JsonReader in) throws IOException {
            /*
            This is the critical part. So if the value is a string,
            Skip it (no exception) and return null.
            */
            if (in.peek() == JsonToken.BEGIN_ARRAY) {
                in.skipValue();
                return null;
            }
            return defaultAdapter.read(in);
        }
    }

}
