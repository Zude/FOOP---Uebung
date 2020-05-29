import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import streamops.StreamOperations;

public class collatz_test {

    @Test
    public void StartWith4() {

        int start = 4;

        int[] expecteds = { 4, 2, 1, 4, 2, 1 };

        int[] actuals = StreamOperations.collatz(start).limit(expecteds.length).toArray();

        assertArrayEquals(expecteds, actuals);

    }

    @Test
    public void StartWith2() {

        int start = 2;

        int[] expecteds = { 2, 1, 4, 2, 1 };

        int[] actuals = StreamOperations.collatz(start).limit(expecteds.length).toArray();

        assertArrayEquals(expecteds, actuals);

    }

    @Test
    public void StartWith1() {

        int start = 1;

        int[] expecteds = { 1, 4, 2, 1, 4 };

        int[] actuals = StreamOperations.collatz(start).limit(expecteds.length).toArray();

        assertArrayEquals(expecteds, actuals);

    }

    @Test
    public void StartWith6() {

        int start = 6;

        int[] expecteds = { 6, 3, 10, 5, 16, 8, 4, 2, 1 };

        int[] actuals = StreamOperations.collatz(start).limit(expecteds.length).toArray();

        assertArrayEquals(expecteds, actuals);

    }

}
