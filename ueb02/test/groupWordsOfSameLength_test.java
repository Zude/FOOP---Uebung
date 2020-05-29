import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import streamops.StreamOperations;

public class groupWordsOfSameLength_test {

    Set<String> setLen1 = new HashSet<String>();
    Set<String> setLen2 = new HashSet<String>();
    Set<String> setLen3 = new HashSet<String>();

    @Before
    public void createSets() {
        setLen1.add("a");
        setLen1.add("b");
        setLen1.add("c");

        setLen2.add("ab");

        setLen3.add("abc");
        setLen3.add("bcd");
    }

    @Test
    public void OneGroup() {

        Set<String> source = new HashSet<String>();
        source.addAll(setLen1);

        Map<Integer, Set<String>> expected = new HashMap<Integer, Set<String>>();
        expected.put(1, setLen1);

        Map<Integer, Set<String>> actual = StreamOperations.groupWordsOfSameLength(source.stream());

        assertTrue(expected.size() == actual.size());
        assertTrue(expected.equals(actual));
    }

    @Test
    public void TwoGroups() {

        Set<String> source = new HashSet<String>();
        source.addAll(setLen1);
        source.addAll(setLen3);

        Map<Integer, Set<String>> expected = new HashMap<Integer, Set<String>>();
        expected.put(1, setLen1);
        expected.put(3, setLen3);

        Map<Integer, Set<String>> actual = StreamOperations.groupWordsOfSameLength(source.stream());

        assertTrue(expected.size() == actual.size());
        assertTrue(expected.equals(actual));
    }

    @Test
    public void ThreeGroups() {

        Set<String> source = new HashSet<String>();
        source.addAll(setLen1);
        source.addAll(setLen2);
        source.addAll(setLen3);

        Map<Integer, Set<String>> expected = new HashMap<Integer, Set<String>>();
        expected.put(1, setLen1);
        expected.put(2, setLen2);
        expected.put(3, setLen3);

        Map<Integer, Set<String>> actual = StreamOperations.groupWordsOfSameLength(source.stream());

        assertTrue(expected.size() == actual.size());
        assertTrue(expected.equals(actual));
    }

    @Test
    public void GroupsWithDups() {

        Set<String> source = new HashSet<String>();
        source.addAll(setLen1);
        source.addAll(setLen3);

        Stream<String> duplicate = Stream.concat(source.stream(), source.stream());

        Map<Integer, Set<String>> expected = new HashMap<Integer, Set<String>>();
        expected.put(1, setLen1);
        expected.put(3, setLen3);

        Map<Integer, Set<String>> actual = StreamOperations.groupWordsOfSameLength(duplicate);

        assertTrue(expected.size() == actual.size());
        assertTrue(expected.equals(actual));
    }

}
