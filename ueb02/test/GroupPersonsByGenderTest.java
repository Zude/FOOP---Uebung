import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.Person;
import streamops.Person.Gender;
import streamops.StreamOperations;

public class GroupPersonsByGenderTest {

    @Test
    public void simpleGroupByGenderTest() {

        Stream<String> inputStream = Stream.of("hallo", "a", "", "-.,");

        Person person1 = new Person(Gender.MALE, "Alex", "Löffler", 21337, 10);
        Person person2 = new Person(Gender.MALE, "Lars", "Sander", 21337, 10);

        Set<Integer> zipcodesSet = new HashSet<Integer>();
        zipcodesSet.add(21337);

        Integer minIncome = 5;
        Integer maxIncome = 15;

        Map<Person.Gender, Set<Person>> resultMap;
        Map<Person.Gender, Set<Person>> expMap = new HashMap<Person.Gender, Set<Person>>();

        Set<Person> persons = new HashSet<Person>();
        persons.add(person1);
        persons.add(person2);

        expMap.put(Gender.MALE, persons);

        Stream<Person> personStream = Stream.of(person1, person2);

        resultMap = StreamOperations.groupPersonsByGender(personStream, zipcodesSet, minIncome,
                maxIncome);

        assertEquals(resultMap, expMap);
    }
}
