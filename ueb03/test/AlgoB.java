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

        int numofScientist = 3;

        int tryoutTime = 100;
        int tinkeringTime = 100;
        int saneTime = 1000;
        int numSteps = 2;
        int timinig =
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
        assertTiming(timinig);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());
    }

    @Test
    public void Drei_Wissenschaftler_Mit_Einer_Runde() throws InterruptedException {

        int numofScientist = 3;

        int tryoutTime = 100;
        int tinkeringTime = 200;
        int saneTime = 1000;
        int numSteps = 1;
        int timinig =
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
        assertTiming(timinig);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());
    }

}
