import static org.junit.Assert.assertEquals;

import org.junit.Test;

import streamops.StreamOperations;

public class CaesarTest {

    @Test
    public void simpleEncrypt() {

        String inpuString = " abc";
        String expected = "!bcd";

        String res = StreamOperations.caesar(inpuString, 1, true);

        assertEquals(expected, res);

    }

    @Test
    public void simpleDecrypt() {

        String inpuString = " ";
        String expected = "~";

        String res = StreamOperations.caesar(inpuString, 1, false);

        assertEquals(expected, res);

    }

}
