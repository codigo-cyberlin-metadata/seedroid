package id.codigo.seedroid_uikit.sdkutils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by papahnakal on 01/03/18.
 */

public class ReflectUtil {
    public ReflectUtil() {
    }

    public static void changeAccesibility(final Field field) {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                field.setAccessible(true);
                return null;
            }
        });
    }

    public static void changeAccesibility(final Method method) {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                method.setAccessible(true);
                return null;
            }
        });
    }
}
