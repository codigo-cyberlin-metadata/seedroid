package id.codigo.seedroid_makelove;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by papahnakal on 20/02/18.
 */

public interface Converter<F,T> {
    T convert(F value) throws IOException;

    /** Creates {@link Converter} instances based on a type and target usage. */
    abstract class Factory {
        public @Nullable Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                                          Annotation[] annotations, MakeLove makeLove) {
            return null;
        }

        public @Nullable Converter<?, RequestBody> requestBodyConverter(Type type,
                                                                        Annotation[] parameterAnnotations, Annotation[] methodAnnotations, MakeLove makeLove) {
            return null;
        }

        public @Nullable
        Converter<?, String> stringConverter(Type type, Annotation[] annotations,
                                             MakeLove makeLove) {
            return null;
        }

        protected static Type getParameterUpperBound(int index, ParameterizedType type) {
            return Utils.getParameterUpperBound(index, type);
        }

        protected static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }
    }
}
