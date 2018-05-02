package id.codigo.seedroid_makelove;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by papahnakal on 21/02/18.
 */

final class DefaultCallAdapterFactory extends CallAdapter.Factory {
    static final CallAdapter.Factory INSTANCE = new DefaultCallAdapterFactory();

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, MakeLove makeLove) {
        if (getRawType(returnType) != CallMakeLove.class) {
            return null;
        }

        final Type responseType = Utils.getCallResponseType(returnType);
        return new CallAdapter<Object, CallMakeLove<?>>() {
            @Override public Type responseType() {
                return responseType;
            }

            @Override public CallMakeLove<Object> adapt(CallMakeLove<Object> callMakeLove) {
                return callMakeLove;
            }
        };
    }
}
