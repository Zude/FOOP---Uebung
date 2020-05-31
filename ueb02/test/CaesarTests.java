import static org.junit.Assert.assertEquals;

import org.junit.Test;

import streamops.StreamOperations;

public class CaesarTests {

    @Test
    public void test() {

        String plaintext = "abc";
        String result = "";
        String expected = "bcd";

        int rotation = 1;
        boolean encode = false;

        result = StreamOperations.caesar(plaintext, rotation, encode);

        assertEquals(result, expected);
    }

}
