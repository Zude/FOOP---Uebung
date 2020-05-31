import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.StreamOperations;

public class CountCharsTest {

    @Test
    public void simpleTest() {

        Stream<Character> stream = Stream.of('a', 'b', 'a');

        Map<Character, Integer> res = StreamOperations.countChars(stream, 'a', 'b', (long) 0.0);

        assertEquals("", res);
    }
}
