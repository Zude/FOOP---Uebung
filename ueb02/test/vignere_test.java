import static org.junit.Assert.assertEquals;

import org.junit.Test;

import streamops.StreamOperations;

public class vignere_test {

    @Test
    public void simpleTest() {
        String start = "Hallo Welt";
        String pwd = "Foo";
        String tes = "/q|S 0>u|[";

        String temp = StreamOperations.vigenere(start, pwd, true);
        assertEquals(tes, temp);

        String actual = StreamOperations.vigenere(temp, pwd, false);

        assertEquals(start, actual);

    }

    @Test
    public void shiftOne() {
        String start = "Hallo Welt";
        String pwd = "oooooooooooooo";

        String temp = StreamOperations.vigenere(start, pwd, true);

        String actual = StreamOperations.vigenere(temp, pwd, false);

        assertEquals(start, actual);
    }

}
