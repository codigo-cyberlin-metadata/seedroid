package id.codigo.seedroid_makelove.http;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by papahnakal on 20/02/18.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface PUT_LOVE {
    String value() default "";
}
