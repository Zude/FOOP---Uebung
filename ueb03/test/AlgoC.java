import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import sciencelab.AlgorithmType;
import sciencelab.Scientist;

public class AlgoC extends TestToolkit {

    AlgorithmType algo = AlgorithmType.C;

    @Test
    public void Zwei_Wissenschaftler_Eine_Runde() throws InterruptedException {

        System.out.println();
        System.out.println("Zwei_Wissenschaftler_Eine_Runde");
        System.out.println();

        final int numofScientist = 2;
        final int tryoutTime = 100;
        final int tinkeringTime = 200;
        final int saneTime = 1000;
        final int numSteps = 1;
        final int timing =
                (tinkeringTime * numSteps * numofScientist) + (tinkeringTime * numofScientist - 1);

        c.tick();

        // Diesmal Liste mit Wissenschaftlern besorgen
        final List<Scientist> scientists = new LinkedList<>();

        final Thread[] startAll = m.startAll(numofScientist, numSteps, tinkeringTime, tryoutTime,
                saneTime, algo, scientists);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", numofScientist,
                startAll.length);
        Assert.assertEquals("Es wurden nicht alle Wissenschaftler erzeugt", numofScientist,
                scientists.size());

        for (Thread t : startAll) {
            t.join();
            System.out.println("joined " + t.getName());
        }

        c.tick();
        assertTiming(500);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());

        for (Scientist s : scientists) {
            Assert.assertEquals(false, s.isInsane());
        }
    }

    @Test
    public void Zwei_Wissenschaftler_Drei_Runden() throws InterruptedException {

        System.out.println();
        System.out.println("Zwei_Wissenschaftler_Drei_Runden");
        System.out.println();

        final int numofScientist = 2;
        final int tryoutTime = 100;
        final int tinkeringTime = 200;
        final int saneTime = 1000;
        final int numSteps = 3;
        final int timing = (tinkeringTime * numSteps * numofScientist) + (tryoutTime);

        c.tick();

        final List<Scientist> scientists = new LinkedList<>();

        final Thread[] startAll = m.startAll(numofScientist, numSteps, tinkeringTime, tryoutTime,
                saneTime, algo, scientists);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", numofScientist,
                startAll.length);
        Assert.assertEquals("Es wurden nicht alle Wissenschaftler erzeugt", numofScientist,
                scientists.size());

        for (Thread t : startAll) {
            t.join();
            System.out.println("joined " + t.getName());
        }

        c.tick();
        assertTiming(timing);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());

        for (Scientist s : scientists) {
            Assert.assertEquals(false, s.isInsane());
        }
    }

    @Test
    public void Drei_Wissenschaftler_Drei_Runden() throws InterruptedException {

        System.out.println();
        System.out.println("Zwei_Wissenschaftler_Drei_Runden");
        System.out.println();

        final int numofScientist = 2;
        final int tryoutTime = 100;
        final int tinkeringTime = 200;
        final int saneTime = 1000;
        final int numSteps = 3;
        final int timing = (tinkeringTime * numSteps * numofScientist) + (tryoutTime);

        c.tick();

        final List<Scientist> scientists = new LinkedList<>();

        final Thread[] startAll = m.startAll(numofScientist, numSteps, tinkeringTime, tryoutTime,
                saneTime, algo, scientists);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", numofScientist,
                startAll.length);
        Assert.assertEquals("Es wurden nicht alle Wissenschaftler erzeugt", numofScientist,
                scientists.size());

        for (Thread t : startAll) {
            t.join();
            System.out.println("joined " + t.getName());
        }

        c.tick();
        assertTiming(timing);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());

        for (Scientist s : scientists) {
            Assert.assertEquals(false, s.isInsane());
        }
    }

    @Test
    public void Ein_Wissenschaftler_Wird_Wahnsinnig() throws InterruptedException {

        System.out.println();
        System.out.println("Ein_Wissenschaftler_Wird_Wahnsinnig");
        System.out.println();

        final int numofScientist = 1;
        final int tryoutTime = 100;
        final int tinkeringTime = 300;
        final int saneTime = 200;
        final int numSteps = 1;
        final int timing =
                (tinkeringTime * numSteps * numofScientist) + (tinkeringTime * numofScientist - 1);

        c.tick();

        final List<Scientist> scientists = new LinkedList<>();

        final Thread[] startAll = m.startAll(numofScientist, numSteps, tinkeringTime, tryoutTime,
                saneTime, algo, scientists);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", numofScientist,
                startAll.length);
        Assert.assertEquals("Es wurden nicht alle Wissenschaftler erzeugt", numofScientist,
                scientists.size());

        for (Thread t : startAll) {
            t.join();
            System.out.println("joined " + t.getName());
        }

        c.tick();
        assertTiming(saneTime);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());

        for (Scientist s : scientists) {
            Assert.assertEquals(true, s.isInsane());
        }
    }

    @Test
    public void Ein_Wissenschaftler_Drei_Runden() throws InterruptedException {

        System.out.println();
        System.out.println("Ein_Wissenschaftler_Drei_Runden");
        System.out.println();

        final int numofScientist = 1;

        final int tryoutTime = 5;
        final int tinkeringTime = 5;
        final int saneTime = 1000;
        final int numSteps = 3;
        final int timing = (tinkeringTime * numSteps * numofScientist) + (tryoutTime);

        c.tick();

        final List<Scientist> scientists = new LinkedList<>();

        final Thread[] startAll = m.startAll(numofScientist, numSteps, tinkeringTime, tryoutTime,
                saneTime, algo, scientists);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", numofScientist,
                startAll.length);
        Assert.assertEquals("Es wurden nicht alle Wissenschaftler erzeugt", numofScientist,
                scientists.size());

        for (Thread t : startAll) {
            t.join();
            System.out.println("joined " + t.getName());
        }

        c.tick();
        assertTiming(timing);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());
    }

}
