import static org.junit.Assert.assertEquals;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.StreamOperations;

public class vignere_test {

    @Test
    public void t() {

        String pwd = "Foo";

        IntStream is = IntStream.iterate(0, a -> {
            int res = pwd.charAt(a + 1);

            if (a <= pwd.length())
                a++;
            else
                a = 0;

            return res;
        });

        Stream<Integer> ms = Stream.generate(() -> {
            int a = 0;
            System.out.println(a);
            a++;
            return 0;
        });

        is.limit(5).forEach(System.out::println);
        // ms.limit(5).forEach(System.out::println);
    }

    @Test
    public void simpleTest() {
        String start = "HalloWelt";
        String pwd = "FooFooFooFoo";
        String expected = start;

        String tes = "/q|S0>u|[";

        String temp = StreamOperations.vigenere(start, pwd, true);
        assertEquals(tes, temp);
        String actual = StreamOperations.vigenere(temp, pwd, false);

        assertEquals(expected, actual);

    }

}
