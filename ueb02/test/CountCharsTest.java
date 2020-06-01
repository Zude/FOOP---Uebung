import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.StreamOperations;

public class CountCharsTest {

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

    @Test
    public void simpleCountCharsWithNullLimit() {

        Map<Character, Integer> expextedMap = new HashMap<Character, Integer>();

        char from = 'a';
        char to = 'd';
        Long limit = null;

        expextedMap.put('a', 2);
        expextedMap.put('b', 1);
        expextedMap.put('d', 3);

        Stream<Character> stream = Stream.of('a', 'b', 'a', 'd', 'd', 'd');

        Map<Character, Integer> res = StreamOperations.countChars(stream, from, to, limit);

        assertEquals(expextedMap, res);
    }

    @Test
    public void simpleCountCharsWithCharsOutOfRange() {

        Map<Character, Integer> expextedMap = new HashMap<Character, Integer>();

        char from = 'b';
        char to = 'z';
        Long limit = null;

        expextedMap.put('b', 1);
        expextedMap.put('d', 3);

        Stream<Character> stream = Stream.of('a', 'b', 'a', 'd', 'd', 'd', 'Z');

        Map<Character, Integer> res = StreamOperations.countChars(stream, from, to, limit);

        assertEquals(expextedMap, res);
    }

    @Test
    public void komplexTest() {

        Map<Character, Integer> expextedMap = new HashMap<Character, Integer>();

        char from = '#';
        char to = '^';
        Long limit = null;

        expextedMap.put('#', 5);
        expextedMap.put('A', 2);
        expextedMap.put('-', 1);
        expextedMap.put('B', 1);
        expextedMap.put('.', 1);

        Stream<Character> stream = Stream.of('!', '#', 'a', '#', 'A', '-', '.', '#', 'A', '#', 'd',
                'd', '!', '"', '_', '#', 'B', 'x');

        Map<Character, Integer> res = StreamOperations.countChars(stream, from, to, limit);

        assertEquals(expextedMap, res);
    }
}
