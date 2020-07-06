package wson.annotations;

/**
 * Typ für Annotationen, die zu nutzende Werte für Felder angeben, denen Strings zugewiesen werden
 * können
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 *
 */

public @interface StoreAs {
    String value() default "default-value";
}
