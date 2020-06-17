import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import sciencelab.AlgorithmType;
import sciencelab.Scientist;

public class AlgoB extends TestToolkit {

    AlgorithmType algo = AlgorithmType.B;

    @Test
    public void Vier_Wissenschaftler_Mit_Drei_Runden() throws InterruptedException {

        System.out.println();
        System.out.println("Vier_Wissenschaftler_Mit_Drei_Runden");
        System.out.println();

        final int numofScientist = 3;

        final int tryoutTime = 100;
        final int tinkeringTime = 100;
        final int saneTime = 1000;
        final int numSteps = 2;
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

    @Test
    public void Drei_Wissenschaftler_Mit_Einer_Runde() throws InterruptedException {

        System.out.println();
        System.out.println("Drei_Wissenschaftler_Mit_Einer_Runde");
        System.out.println();

        final int numofScientist = 3;

        final int tryoutTime = 100;
        final int tinkeringTime = 200;
        final int saneTime = 1000;
        final int numSteps = 1;
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

    @Test
    public void StartAll_Mit_Res_Null() throws InterruptedException {

        System.out.println();
        System.out.println("StartAll_Mit_Res_Null");
        System.out.println();

        final int numofScientist = 3;

        final int tryoutTime = 100;
        final int tinkeringTime = 200;
        final int saneTime = 1000;
        final int numSteps = 1;
        final int timing = (tinkeringTime * numSteps * numofScientist) + (tryoutTime);

        c.tick();

        final Thread[] startAll = m.startAll(numofScientist, numSteps, tinkeringTime, tryoutTime,
                saneTime, algo, null);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", numofScientist,
                startAll.length);

        for (Thread t : startAll) {
            t.join();
            System.out.println("joined " + t.getName());
        }

        c.tick();
        assertTiming(timing);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());
    }

    @Test
    public void Zwei_Wissenschaftler_Sehr_Kurze_Zeiten() throws InterruptedException {

        System.out.println();
        System.out.println("Zwei_Wissenschaftler_Sehr_Kurze_Zeiten");
        System.out.println();

        final int numofScientist = 2;

        final int tryoutTime = 1;
        final int tinkeringTime = 1;
        final int saneTime = 1000;
        final int numSteps = 1;
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
