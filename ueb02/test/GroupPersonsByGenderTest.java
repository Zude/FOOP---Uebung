import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.StreamOperations;

public class GroupPersonsByGenderTest {

    @Test
    public void test() {

        Stream<String> inputStream = Stream.of("hallo", "a", "", "-.,");

        Stream<Character> resultStream = StreamOperations.stringsToChars(inputStream);
        ;

        Stream<Character> expectedStream = Stream.of('h', 'a', 'l', 'l', 'o', 'a', '-', '.', ',');

        assertEquals(resultStream.collect(Collectors.toList()),
                expectedStream.collect(Collectors.toList()));
    }
}
