import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.StreamOperations;

public class StringToCharsTest {

    @Test
    public void simpleCountCharsWithLimit() {

        Map<Character, Integer> expextedMap = new HashMap<Character, Integer>();

        char from = 'a';
        char to = 'd';
        Long limit = new Long(5);

        expextedMap.put('a', 2);
        expextedMap.put('b', 1);
        expextedMap.put('d', 2);

        Stream<Character> stream = Stream.of('a', 'b', 'a', 'd', 'd', 'd');

        Map<Character, Integer> res = StreamOperations.countChars(stream, from, to, limit);

        assertEquals(expextedMap, res);
    }
}
