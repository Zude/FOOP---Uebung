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

        Person person1 = new Person(Gender.MALE, "Alex", "Löffler", 21337, 10);
        Person person2 = new Person(Gender.MALE, "Lars", "Sander", 21337, 10);
        Person person3 = new Person(Gender.FEMALE, "Sara", "Löffler", 21337, 10);
        Person person4 = new Person(Gender.FEMALE, "Lara", "Sander", 21337, 10);
        Person person5 = new Person(Gender.DIVERSE, "Lisa", "Löffler", 21337, 10);
        Person person6 = new Person(Gender.DIVERSE, "Hanna", "Sander", 21337, 10);

        Set<Integer> zipcodesSet = new HashSet<Integer>();
        zipcodesSet.add(21337);

        Integer minIncome = 5;
        Integer maxIncome = 15;

        Map<Person.Gender, Set<Person>> resultMap;
        Map<Person.Gender, Set<Person>> expMap = new HashMap<Person.Gender, Set<Person>>();

        Set<Person> malePersons = new HashSet<Person>();
        malePersons.add(person1);
        malePersons.add(person2);

        Set<Person> femalePersons = new HashSet<Person>();
        femalePersons.add(person3);
        femalePersons.add(person4);

        Set<Person> divPersons = new HashSet<Person>();
        divPersons.add(person5);
        divPersons.add(person6);

        expMap.put(Gender.MALE, malePersons);
        expMap.put(Gender.FEMALE, femalePersons);
        expMap.put(Gender.DIVERSE, divPersons);

        Stream<Person> personStream =
                Stream.of(person1, person2, person3, person4, person5, person6);

        resultMap = StreamOperations.groupPersonsByGender(personStream, zipcodesSet, minIncome,
                maxIncome);

        assertEquals(resultMap, expMap);
    }

    @Test
    public void simpleTest() {

        Person person1 = new Person(Gender.MALE, "Alex", "Löffler", 21337, 10);
        Person person2 = new Person(Gender.MALE, "Alex", "Löffler", 21337, 10);
        Person person3 = new Person(Gender.MALE, "Alex", "Löffler", 21337, 10);

        Set<Integer> zipcodesSet = new HashSet<Integer>();
        zipcodesSet.add(21337);

        Integer minIncome = 5;
        Integer maxIncome = 15;

        Map<Person.Gender, Set<Person>> resultMap;
        Map<Person.Gender, Set<Person>> expMap = new HashMap<Person.Gender, Set<Person>>();

        Set<Person> malePersons = new HashSet<Person>();
        malePersons.add(person1);

        expMap.put(Gender.MALE, malePersons);

        Stream<Person> personStream = Stream.of(person1, person2, person3);

        resultMap = StreamOperations.groupPersonsByGender(personStream, zipcodesSet, minIncome,
                maxIncome);

        assertEquals(resultMap, expMap);
    }

    @Test
    public void filterByIncome() {

        Person person1 = new Person(Gender.MALE, "Alex", "Löffler", 21337, 123);
        Person person2 = new Person(Gender.MALE, "Lars", "Sander", 21337, 145);
        Person person3 = new Person(Gender.FEMALE, "Sara", "Löffler", 21337, 150);
        Person person4 = new Person(Gender.FEMALE, "Lara", "Sander", 21337, 100);
        Person person5 = new Person(Gender.DIVERSE, "Lisa", "Löffler", 21337, 111);
        Person person6 = new Person(Gender.DIVERSE, "Hanna", "Sander", 21337, 1130);

        Set<Integer> zipcodesSet = new HashSet<Integer>();
        zipcodesSet.add(21337);

        Integer minIncome = 125;
        Integer maxIncome = 300;

        Map<Person.Gender, Set<Person>> resultMap;
        Map<Person.Gender, Set<Person>> expMap = new HashMap<Person.Gender, Set<Person>>();

        Set<Person> malePersons = new HashSet<Person>();

        malePersons.add(person2);

        Set<Person> femalePersons = new HashSet<Person>();
        femalePersons.add(person3);

        expMap.put(Gender.MALE, malePersons);
        expMap.put(Gender.FEMALE, femalePersons);

        Stream<Person> personStream =
                Stream.of(person1, person2, person3, person4, person5, person6);

        resultMap = StreamOperations.groupPersonsByGender(personStream, zipcodesSet, minIncome,
                maxIncome);

        assertEquals(resultMap, expMap);
    }

    @Test
    public void filterByIncomeAndZip() {

        Person person1 = new Person(Gender.MALE, "Alex", "Löffler", 21337, 123);
        Person person2 = new Person(Gender.MALE, "Lars", "Sander", 21337, 145);
        Person person3 = new Person(Gender.FEMALE, "Sara", "Löffler", 21324, 150);
        Person person4 = new Person(Gender.FEMALE, "Lara", "Sander", 21337, 100);
        Person person5 = new Person(Gender.DIVERSE, "Lisa", "Löffler", 21412, 111);
        Person person6 = new Person(Gender.DIVERSE, "Hanna", "Sander", 21337, 250);

        Set<Integer> zipcodesSet = new HashSet<Integer>();
        zipcodesSet.add(21337);
        zipcodesSet.add(21412);

        Integer minIncome = 125;
        Integer maxIncome = 300;

        Map<Person.Gender, Set<Person>> resultMap;
        Map<Person.Gender, Set<Person>> expMap = new HashMap<Person.Gender, Set<Person>>();

        Set<Person> malePersons = new HashSet<Person>();
        malePersons.add(person2);

        Set<Person> divPersons = new HashSet<Person>();
        divPersons.add(person6);

        expMap.put(Gender.MALE, malePersons);
        expMap.put(Gender.DIVERSE, divPersons);

        Stream<Person> personStream =
                Stream.of(person1, person2, person3, person4, person5, person6);

        resultMap = StreamOperations.groupPersonsByGender(personStream, zipcodesSet, minIncome,
                maxIncome);

        assertEquals(resultMap, expMap);
    }

    @Test
    public void allFilterNull() {

        Person person1 = new Person(Gender.MALE, "Alex", "Löffler", 21337, 10);
        Person person2 = new Person(Gender.MALE, "Lars", "Sander", 21337, 10);
        Person person3 = new Person(Gender.FEMALE, "Sara", "Löffler", 21337, 10);
        Person person4 = new Person(Gender.FEMALE, "Lara", "Sander", 21337, 10);
        Person person5 = new Person(Gender.DIVERSE, "Lisa", "Löffler", 21337, 10);
        Person person6 = new Person(Gender.DIVERSE, "Hanna", "Sander", 21337, 10);

        Map<Person.Gender, Set<Person>> resultMap;
        Map<Person.Gender, Set<Person>> expMap = new HashMap<Person.Gender, Set<Person>>();

        Set<Person> malePersons = new HashSet<Person>();
        malePersons.add(person1);
        malePersons.add(person2);

        Set<Person> femalePersons = new HashSet<Person>();
        femalePersons.add(person3);
        femalePersons.add(person4);

        Set<Person> divPersons = new HashSet<Person>();
        divPersons.add(person5);
        divPersons.add(person6);

        expMap.put(Gender.MALE, malePersons);
        expMap.put(Gender.FEMALE, femalePersons);
        expMap.put(Gender.DIVERSE, divPersons);

        Stream<Person> personStream =
                Stream.of(person1, person2, person3, person4, person5, person6);

        resultMap = StreamOperations.groupPersonsByGender(personStream, null, null, null);

        assertEquals(resultMap, expMap);
    }
}
