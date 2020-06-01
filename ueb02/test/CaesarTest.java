import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

}
