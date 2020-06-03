import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.PrintableChar;
import streamops.StreamOperations;

public class CaesarTest {

    @Test
    public void simpleEncrypt() {

        String inpuString = " abc";
        String expected = "!bcd";

        boolean encrypt = true;
        int rotation = 1;

        String res = StreamOperations.caesar(inpuString, rotation, encrypt);

        assertEquals(expected, res);
    }

    @Test
    public void simpleDecrypt() {

        String inpuString = " bc";
        String expected = "~ab";

        boolean encrypt = false;
        int rotation = 1;

        String res = StreamOperations.caesar(inpuString, rotation, encrypt);

        assertEquals(expected, res);
    }

    @Test
    public void simpleDecryptLong() {

        String inpuString = "abcdefghijklmnopqrstuvwxyz";
        String expected = "defghijklmnopqrstuvwxyz{|}";
        boolean encrypt = true;
        int rotation = 3;

        String res = StreamOperations.caesar(inpuString, rotation, encrypt);

        assertEquals(expected, res);
    }

    @Test
    public void complexEnAndDecrypt() {

        String inpuString = " bcdsadagacdwsdagfwadf% _-*#";
        String expected = " bcdsadagacdwsdagfwadf% _-*#";

        boolean encrypt = false;
        int rotation = 6;

        String res = StreamOperations.caesar(StreamOperations.caesar(inpuString, rotation, true),
                rotation, encrypt);

        assertEquals(expected, res);
    }

    @Test
    public void allPrintableWithFullRange() {
        Stream<PrintableChar> all = IntStream.iterate(PrintableChar.LOWER, i -> i + 1)
                .limit(PrintableChar.RANGE).mapToObj(c -> new PrintableChar(c));

        String allStr = all.map(Object::toString).collect(Collectors.joining());

        boolean comp;

        for (int i = 0; i < PrintableChar.RANGE; i++) {

            String temp = StreamOperations.caesar(allStr, i, true);

            String res = StreamOperations.caesar(temp, i, false);

            comp = res.equals(allStr);

            if (!comp) {
                System.out.println("Error at rot: " + i);
                System.out.println("allStr: \n" + allStr);
                System.out.println("res: \n" + res);
                assertTrue(false);
            }

        }

        assertTrue(true);
    }

}
