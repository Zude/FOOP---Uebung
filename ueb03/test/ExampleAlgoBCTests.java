import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import sciencelab.AlgorithmType;
import sciencelab.Scientist;

/**
 * Beispieltests für Aufgabenteile B (und C).
 * 
 * @author kar, mhe
 * 
 */
public class ExampleAlgoBCTests extends TestToolkit {

    /**
     * B. 2 Wissenschaftler, 1 Durchlauf
     */
    @Test
    public void testB1() throws InterruptedException {

        System.out.println();
        System.out.println("Test B1");
        System.out.println();

        // Zeitmessung starten
        c.tick();

        // 2 Wissenschaftlerthreads erzeugen und starten
        final Thread[] startAll = m.startAll(2, 1, 500, 500, 0, AlgorithmType.B, null);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", 2, startAll.length);

        // Warten bis sich alle 2 Wissenschaftlerthreads beenden
        for (Thread t : startAll) {
            t.join();
            System.out.println("Joined: " + t.getName());
        }

        // Zeit nochmals messen
        c.tick();

        // Timing sicherstellen (mit Toleranz durch assertTiming-Methode)
        assertTiming(1500);
    }

    /**
     * B. 2 Wissenschaftler, 1 Durchlauf, Ausprobierenzeit länger als Bastelzeit.
     */
    @Test
    public void testB2a() throws InterruptedException {

        System.out.println();
        System.out.println("Test B2a");
        System.out.println();

        c.tick();

        final Thread[] startAll = m.startAll(2, 1, 250, 500, 0, AlgorithmType.B, null);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", 2, startAll.length);

        for (Thread t : startAll) {
            t.join();
            System.out.println("Joined: " + t.getName());
        }

        c.tick();
        assertTiming(1000);
    }

    /**
     * B. 2 Wissenschaftler, 1 Durchlauf. Ausprobierenzeit kürzer als Bastelzeit.
     */
    @Test
    public void test2b() throws InterruptedException {

        System.out.println();
        System.out.println("Test B2b");
        System.out.println();

        c.tick();

        final Thread[] startAll = m.startAll(2, 1, 200, 100, 0, AlgorithmType.B, null);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", 2, startAll.length);

        for (Thread t : startAll) {
            t.join();
            System.out.println("Joined: " + t.getName());
        }

        c.tick();
        assertTiming(500);
    }

    /**
     * B. 2 Wissenschaftler. 2 Durchlaeufe. Ausprobierenzeit kürzer als Bastelzeit.
     */
    @Test
    public void test2c() throws InterruptedException {

        System.out.println();
        System.out.println("Test B2c");
        System.out.println();

        c.tick();

        final Thread[] startAll = m.startAll(2, 2, 200, 100, 0, AlgorithmType.B, null);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", 2, startAll.length);

        for (Thread t : startAll) {
            t.join();
            System.out.println("Joined: " + t.getName());
        }

        c.tick();
        assertTiming(900);
    }

    /**
     * B. 3 Wissenschaftler. 3 Durchläufe.
     * 
     */
    @Test
    public void test3() throws InterruptedException {

        System.out.println();
        System.out.println("Test B3");
        System.out.println();

        c.tick();

        final Thread[] startAll = m.startAll(3, 3, 100, 100, 0, AlgorithmType.B, null);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", 3, startAll.length);

        for (Thread t : startAll) {
            t.join();
            System.out.println("Joined: " + t.getName());
        }

        c.tick();
        assertTiming(1000, 1200); // je nach Scheduling
    }

    /**
     * C. 2 Wissenschaftler. 1 Durchlauf. Test ohne wahnsinnig werdende Wissenschaftler.
     * 
     */
    @Test
    public void testC1() throws InterruptedException {

        System.out.println();
        System.out.println("Test C1");
        System.out.println();

        c.tick();

        // Diesmal Liste mit Wissenschaftlern besorgen
        final List<Scientist> scientists = new LinkedList<>();

        final Thread[] startAll = m.startAll(2, 1, 100, 100, 1000, AlgorithmType.C, scientists);

        Assert.assertEquals("Es wurden nicht alle Threads erzeugt", 2, startAll.length);
        Assert.assertEquals("Es wurden nicht alle Wissenschaftler erzeugt", 2, scientists.size());

        for (Thread t : startAll) {
            t.join();
            System.out.println("joined " + t.getName());
        }

        c.tick();
        assertTiming(300);

        Thread.sleep(EPS); // warten, bis sich auch die Timer beendet haben!
        Assert.assertEquals(NUM_NON_DEAMON, getNonDaemonThreads());

        // keiner darf wahnsinnig sein
        for (Scientist s : scientists) {
            Assert.assertEquals(false, s.isInsane());
        }

    }

}
