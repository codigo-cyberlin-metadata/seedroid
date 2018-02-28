package id.codigo.automation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import id.codigo.automation.model.MyEntity;

import static id.codigo.automation.AlwaysListTypeAdapterFactory.getAlwaysListTypeAdapterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test("");
        test("{\"id\":1,\"name\":\"name\"}");
        test("[{\"id\":1,\"name\":\"name\"},{\"id\":1,\"name\":\"name\"}]");
        test("[]");
    }
    private static final Type responseItemListType = new TypeToken<List<MyEntity>>() {
    }.getType();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(getAlwaysListTypeAdapterFactory())
            .create();

    public static void main(final String... args) {

    }

    private static void test(final String incomingJson) {
        final List<MyEntity> list = gson.fromJson(incomingJson, responseItemListType);
        System.out.print("LIST=");
        System.out.println(list);
        System.out.print("JSON=");
        gson.toJson(list, responseItemListType, System.out); // no need to create an intermediate string, let it just stream
        System.out.println();
        System.out.println("-----------------------------------");
    }
}
