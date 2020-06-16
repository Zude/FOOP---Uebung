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

        final int numofScientist = 3;

        final int tryoutTime = 100;
        final int tinkeringTime = 100;
        final int saneTime = 1000;
        final int numSteps = 2;
        final int timing = (tinkeringTime * numSteps * numofScientist) + (tryoutTime);

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
        assertTiming(timing);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());
    }

    @Test
    public void Drei_Wissenschaftler_Mit_Einer_Runde() throws InterruptedException {

        final int numofScientist = 3;

        final int tryoutTime = 100;
        final int tinkeringTime = 200;
        final int saneTime = 1000;
        final int numSteps = 1;
        final int timing = (tinkeringTime * numSteps * numofScientist) + (tryoutTime);

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
        assertTiming(timing);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());
    }

}
