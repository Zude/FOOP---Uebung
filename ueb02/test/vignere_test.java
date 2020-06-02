import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        String pwd = "passwort";

        Stream<PrintableChar> all = IntStream.iterate(PrintableChar.LOWER, i -> i + 1)
                .limit(PrintableChar.RANGE).mapToObj(c -> new PrintableChar(c));

        String allStr = all.map(Object::toString).collect(Collectors.joining());

        String temp = StreamOperations.vigenere(allStr, pwd, true);

        String actual = StreamOperations.vigenere(temp, pwd, false);

        assertEquals(allStr, actual);
    }

    @Test
    public void allSymbolsAsPassword() {
        String start =
                "Ein ganz langer Satz um moeglichst viele Symbole aus dem PrintableChar zu testen. Und noch ein weiterer Satz der einfach nur lang sein soll";

        Stream<PrintableChar> all = IntStream.iterate(PrintableChar.LOWER, i -> i + 1)
                .limit(PrintableChar.RANGE).mapToObj(c -> new PrintableChar(c));

        String allStr = all.map(Object::toString).collect(Collectors.joining());

        String temp = StreamOperations.vigenere(start, allStr, true);

        String actual = StreamOperations.vigenere(temp, allStr, false);

        assertEquals(start, actual);
    }

    @Test
    public void allSymbolsInBoth() {
        Stream<PrintableChar> all = IntStream.iterate(PrintableChar.LOWER, i -> i + 1)
                .limit(PrintableChar.RANGE).mapToObj(c -> new PrintableChar(c));

        String allStr = all.map(Object::toString).collect(Collectors.joining());

        String temp = StreamOperations.vigenere(allStr, allStr, true);

        String actual = StreamOperations.vigenere(temp, allStr, false);

        assertEquals(allStr, actual);
    }

}
