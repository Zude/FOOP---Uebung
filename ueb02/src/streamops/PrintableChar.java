package streamops;
/**
 * Hilfsklasse zur Repräsenation eines Teilbereiches des ASCII-Zeichensatzes (druckbare Zeichen).
 * 
 * Dieser Klasse dürfen öffentliche Methoden hinzugefügt werden.
 * 
 * @author kar, mhe, TODO Namen ergänzen
 *
 */
public class PrintableChar {

    public static final char LOWER = ' ';
    public static final char UPPER = '~';
    public static final char RANGE = UPPER - LOWER + 1;

    private final char c;

    /**
     * Konstruktor.
     * 
     * @param c Zeichen
     * @throws IllegalArgumentException Zeichen nicht innerhalb der Grenzen
     */
    public PrintableChar(int c) {
        if (!isPrintableChar(c)) {
            throw new IllegalArgumentException();
        }

        this.c = (char) c;
    }

    /**
     * Überprüft ob das übergebene Zeichen innerhalb der vordefinierten Grenzen liegt.
     * 
     * @param c Zeichen
     * @return true, wenn das Zeichen im Bereich liegt, sonst false
     */
    public static boolean isPrintableChar(int c) {
        return c >= LOWER && c <= UPPER;
    }

    /**
     * Liefert das Zeichen zurück.
     * 
     * @return das Zeichen
     */
    public char toChar() {
        return c;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + c;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PrintableChar) {
            return (c == ((PrintableChar) obj).c);
        }
        return false;
    }

    @Override
    public String toString() {
        return "" + c;
    }
    
    

}
