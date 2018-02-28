package id.codigo.automation;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import id.codigo.automation.model.MyEntity;

import static id.codigo.automation.AlwaysListTypeAdapter.getAlwaysListTypeAdapter;

/**
 * Created by papahnakal on 27/02/18.
 */

final class AlwaysListTypeAdapterFactory implements TypeAdapterFactory {

    private static final TypeAdapterFactory alwaysListTypeAdapterFactory = new AlwaysListTypeAdapterFactory();

    private AlwaysListTypeAdapterFactory() {
    }

    static TypeAdapterFactory getAlwaysListTypeAdapterFactory() {
        return alwaysListTypeAdapterFactory;
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken)
            throws IllegalArgumentException {
        if ( List.class.isAssignableFrom(typeToken.getRawType()) ) {
            final Type elementType = getElementType(typeToken);
            // Class<T> instances can be compared with ==
            final TypeAdapter<?> elementTypeAdapter = elementType == MyEntity.class ? gson.getAdapter(MyEntity.class) : null;
            // Found supported element type adapter?
            if ( elementTypeAdapter != null ) {
                @SuppressWarnings("unchecked")
                final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) getAlwaysListTypeAdapter(elementTypeAdapter);
                return castTypeAdapter;
            }
        }
        // Not a type that can be handled? Let Gson pick a more appropriate one itself
        return null;
    }

    // Attempt to detect the list element type
    private static Type getElementType(final TypeToken<?> typeToken) {
        final Type listType = typeToken.getType();
        return listType instanceof ParameterizedType
                ? ((ParameterizedType) listType).getActualTypeArguments()[0]
                : Object.class;
    }
}
