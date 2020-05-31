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

        String inpuString = " bc";
        String expected = "~ab";

        String res = StreamOperations.caesar(inpuString, 1, false);

        assertEquals(expected, res);
    }

    @Test
    public void complexEnAndDecrypt() {

        String inpuString = " bcdsadagacdwsdagfwadf% _-*#";
        String expected = " bcdsadagacdwsdagfwadf% _-*#";

        String res =
                StreamOperations.caesar(StreamOperations.caesar(inpuString, 6, true), 6, false);

        assertEquals(expected, res);
    }

}
