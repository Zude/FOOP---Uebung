import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.StreamOperations;

public class StringToCharsTest {

    @Test
    public void simpleStringToChars() {

        Stream<String> inputStream = Stream.of("hallo", "a", "", "-.,");

        Stream<Character> resultStream = StreamOperations.stringsToChars(inputStream);
        ;

        Stream<Character> expectedStream = Stream.of('h', 'a', 'l', 'l', 'o', 'a', '-', '.', ',');

        assertEquals(resultStream.collect(Collectors.toList()),
                expectedStream.collect(Collectors.toList()));
    }

    @Test
    public void emptyStringToChars() {

        Stream<String> inputStream = Stream.of("", "");

        Stream<Character> resultStream = StreamOperations.stringsToChars(inputStream);
        ;

        Stream<Character> expectedStream = Stream.of();

        assertEquals(resultStream.collect(Collectors.toList()),
                expectedStream.collect(Collectors.toList()));
    }

}
