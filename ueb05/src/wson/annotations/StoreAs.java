package wson.annotations;

import java.lang.annotation.*;

/**
 * Typ für Annotationen, die zu nutzende Werte für Felder angeben, denen Strings zugewiesen werden
 * können
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 *
 */

// TODO wird noch ein anderes Flag benötigt?
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StoreAs {
    String value() default "default-value";
}
