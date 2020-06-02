import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import streamops.StreamOperations;

public class Evaluate_test {

    @Test
    public void simpleAddition() {
        Map<String, String> replace = new HashMap<String, String>();
        String[] inputArr = { "4", "+", "6" };
        Integer expected = 10;

        Stream<String> inputStr = Stream.of(inputArr);

        Integer result = StreamOperations.evaluate(inputStr, replace);

        assertEquals(expected, result);
    }

    @Test
    public void simpleAdditionWithReplace() {
        Map<String, String> replace = new HashMap<String, String>();
        replace.put("plus", "+");
        replace.put("sechs", "6");
        replace.put("vier", "4");
        String[] inputArr = { "vier", "plus", "sechs" };
        Integer expected = 10;

        Stream<String> inputStr = Stream.of(inputArr);

        Integer result = StreamOperations.evaluate(inputStr, replace);

        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongStart() {
        Map<String, String> replace = new HashMap<String, String>();
        String[] inputArr = { "+", "4", "+", "6" };

        Stream<String> inputStr = Stream.of(inputArr);

        StreamOperations.evaluate(inputStr, replace);

    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongEnd() {
        Map<String, String> replace = new HashMap<String, String>();
        String[] inputArr = { "4", "+", "6", "+" };

        Stream<String> inputStr = Stream.of(inputArr);

        StreamOperations.evaluate(inputStr, replace);

    }

    @Test(expected = IllegalArgumentException.class)
    public void divByZero() {
        Map<String, String> replace = new HashMap<String, String>();
        String[] inputArr = { "4", "/", "0" };

        Stream<String> inputStr = Stream.of(inputArr);

        StreamOperations.evaluate(inputStr, replace);

    }

    @Test(expected = IllegalArgumentException.class)
    public void noNumbers() {
        Map<String, String> replace = new HashMap<String, String>();
        String[] inputArr = { "a", "-", "b" };

        Stream<String> inputStr = Stream.of(inputArr);

        StreamOperations.evaluate(inputStr, replace);

    }

    @Test
    public void expressionWithAllOps() {
        Map<String, String> replace = new HashMap<String, String>();
        String[] inputArr = { "4", "+", "6", "-", "3", "*", "4", "/", "4", "%", "3" };
        Integer expected = 1;

        Stream<String> inputStr = Stream.of(inputArr);

        Integer result = StreamOperations.evaluate(inputStr, replace);

        assertEquals(expected, result);
    }

}
