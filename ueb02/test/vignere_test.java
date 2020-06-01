import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.PrintableChar;
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
        String pwd = "o";

        String temp = StreamOperations.vigenere(start, pwd, true);

        String actual = StreamOperations.vigenere(temp, pwd, false);

        assertEquals(start, actual);
    }

    @Test
    public void edgeCaseBottomStart() {
        String start = "    ";
        String pwd = "passwort";

        String temp = StreamOperations.vigenere(start, pwd, true);

        String actual = StreamOperations.vigenere(temp, pwd, false);

        assertEquals(start, actual);
    }

    @Test
    public void edgeCaseTopStart() {
        String start = "~~~~~~";
        String pwd = "passwort";

        String temp = StreamOperations.vigenere(start, pwd, true);

        String actual = StreamOperations.vigenere(temp, pwd, false);

        assertEquals(start, actual);
    }

    @Test
    public void randomPasswort() {
        String start = "!$%$223//()?`*'##";
        Stream<PrintableChar> startstream = PrintableChar.convertStringToStream(start);

        Stream<Integer> pwd = StreamOperations.oneTimePadPassphrase();
        Stream<Integer> pwd2 = StreamOperations.oneTimePadPassphrase();

        Stream<PrintableChar> temp = StreamOperations.oneTimePad(startstream, pwd, true);

        Stream<PrintableChar> actual = StreamOperations.oneTimePad(temp, pwd2, false);

        // String c1 = startstream.map(Object::toString).collect(Collectors.joining());
        String c2 = actual.map(Object::toString).collect(Collectors.joining());

        assertEquals(start, c2);
    }

    @Test
    public void allSymbols() {
        String start = "!#$%&'()*+,-./:;<=>?@[] ^  `|~{}";
        String pwd = "passwort";

        String temp = StreamOperations.vigenere(start, pwd, true);

        String actual = StreamOperations.vigenere(temp, pwd, false);

        assertEquals(start, actual);
    }

}
