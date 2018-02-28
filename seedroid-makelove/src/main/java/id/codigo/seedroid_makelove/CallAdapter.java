package id.codigo.seedroid_makelove;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by papahnakal on 20/02/18.
 */

public interface CallAdapter <R, T> {

    Type responseType();

    T adapt(CallMakeLove<R> call);

    abstract class Factory {

        public abstract @Nullable
        CallAdapter<?, ?> get(Type returnType, Annotation[] annotations,
                              MakeLove makeLove);

        protected static Type getParameterUpperBound(int index, ParameterizedType type) {
            return Utils.getParameterUpperBound(index, type);
        }

        protected static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }
    }

}
