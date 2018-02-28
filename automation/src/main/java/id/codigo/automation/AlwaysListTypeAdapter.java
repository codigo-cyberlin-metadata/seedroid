package id.codigo.automation;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.gson.stream.JsonToken.END_ARRAY;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;

/**
 * Created by papahnakal on 27/02/18.
 */

final class AlwaysListTypeAdapter <T>
        extends TypeAdapter<List<T>> {

    private final TypeAdapter<T> elementTypeAdapter;

    private AlwaysListTypeAdapter(final TypeAdapter<T> elementTypeAdapter) {
        this.elementTypeAdapter = elementTypeAdapter;
    }

    static <T> TypeAdapter<List<T>> getAlwaysListTypeAdapter(final TypeAdapter<T> elementTypeAdapter) {
        return new AlwaysListTypeAdapter<>(elementTypeAdapter);
    }

    @Override
    @SuppressWarnings("resource")
    public void write(final JsonWriter out, final List<T> list)
            throws IOException {
        if ( list == null ) {
            out.nullValue();
        } else {
            switch ( list.size() ) {
                case 0:
                    out.beginArray();
                    out.endArray();
                    break;
                case 1:
                    elementTypeAdapter.write(out, list.iterator().next());
                    break;
                default:
                    out.beginArray();
                    for ( final T element : list ) {
                        elementTypeAdapter.write(out, element);
                    }
                    out.endArray();
                    break;
            }
        }
    }

    @Override
    public List<T> read(final JsonReader in)
            throws IOException {
        final JsonToken token = in.peek();
        switch ( token ) {
            case BEGIN_ARRAY:
                final List<T> list = new ArrayList<>();
                in.beginArray();
                while ( in.peek() != END_ARRAY ) {
                    list.add(elementTypeAdapter.read(in));
                }
                in.endArray();
                return unmodifiableList(list);
            case BEGIN_OBJECT:
                return singletonList(elementTypeAdapter.read(in));
            case NULL:
                return null;
            case END_ARRAY:
            case END_OBJECT:
            case NAME:
            case STRING:
            case NUMBER:
            case BOOLEAN:
            case END_DOCUMENT:
                throw new MalformedJsonException("Unexpected token: " + token);
            default:
                // A guard case: what if Gson would add another token someday?
                throw new AssertionError("Must never happen: " + token);
        }
    }
}
