import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import sciencelab.AlgorithmType;
import sciencelab.Scientist;

public class AlgoC extends TestToolkit {

    @Test
    public void Vier_Wissenschaftler() throws InterruptedException {

        System.out.println();
        System.out.println("Test C");
        System.out.println();

        c.tick();

        // Diesmal Liste mit Wissenschaftlern besorgen
        final List<Scientist> scientists = new LinkedList<>();

        final Thread[] startAll = m.startAll(4, 1, 100, 100, 1000, AlgorithmType.C, scientists);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", 4, startAll.length);
        Assert.assertEquals("Es wurden nicht alle Wissenschaftler erzeugt", 4, scientists.size());

        for (Thread t : startAll) {
            t.join();
            System.out.println("joined " + t.getName());
        }

        c.tick();
        assertTiming(400);

        Thread.sleep(EPS);
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());

        for (Scientist s : scientists) {
            Assert.assertEquals(false, s.isInsane());
        }
    }

}
