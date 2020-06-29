import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import server.PrimeManager;

public class BeispieltestPrimeManager {

    @Test
    public void testBasicNextPrime() throws InterruptedException {

        final PrimeManager g = new PrimeManager(100);

        // Berechnung starten
        g.startWorker(10);
        Thread.sleep(100); // Wir wollen nur bereits sicher schon berechnete Zahlen testen

        // Hinweis: sequentiell
        Assert.assertEquals(2, g.nextPrime(0));
        Thread.sleep(10); // Berechnungen sollen weitergehen
        Assert.assertEquals(2, g.nextPrime(2));
        Thread.sleep(10);
        Assert.assertEquals(2, g.nextPrime(1));
        Thread.sleep(10);

        g.stopWorker();

        List<String> glog = g.getLog();

        List<String> entries_in_order = new LinkedList<>(Arrays.asList("requested: nextprime,0",
                "response: nextprime,0,2", "requested: nextprime,2", "response: nextprime,2,2",
                "requested: nextprime,1", "response: nextprime,1,2"));

        // mindestens die 2 sollte er schon gefunden haben
        Assert.assertTrue(glog.contains("found prime: 2"));

        // nur die interessanten Logeinträge herausfiltern
        List<String> glog_stripped = new LinkedList<>();
        for (String s : glog) {
            if (s.contains("requested") || s.contains("response")) {
                glog_stripped.add(s);
            }
        }

        // Listen auf gleiche Einträge prüfen
        Assert.assertEquals(glog_stripped, entries_in_order);
    }

    @Test
    public void testInterlockedNextPrime() throws InterruptedException {

        final PrimeManager g = new PrimeManager(100);

        // Berechnung starten
        g.startWorker(10);
        Thread.sleep(200); // Wir wollen nur bereits sicher schon berechnete Zahlen testen

        final Thread c1 = new Thread(new Runnable() {
            public void run() {
                try {
                    g.nextPrime(11);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                } // Eine Assertion käme da nie raus, Ergebnis in dem Fall wird über das Log
                  // getestet
            }
        });
        c1.start();

        final Thread c2 = new Thread(new Runnable() {
            public void run() {
                try {
                    g.nextPrime(4);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        });
        c2.start();

        // Warten bis beide Threads zurückgekehrt sind
        c1.join();
        c2.join();

        // Berechnung kann sicher gestoppt werden
        g.stopWorker();

        List<String> glog = g.getLog();

        // nur die ersten fünf interessanten Logeinträge herausfiltern
        List<String> glog_stripped = new LinkedList<>();
        int i = 0;
        for (String s : glog) {
            if (s.contains("found prime")) {
                glog_stripped.add(s);
                i++;
            }
            if (i == 5) {
                break;
            }
        }

        // Listen auf gleiche Einträge prüfen, mindestens die notwendigen Primzahlen gefunden?
        List<String> primes = new LinkedList<>(Arrays.asList("found prime: 2", "found prime: 3",
                "found prime: 5", "found prime: 7", "found prime: 11"));
        Assert.assertEquals(glog_stripped, primes);

        // Interessante Einträge stichprobenhaft überprüfen
        Assert.assertTrue(glog.contains("response: nextprime,4,5"));
        Assert.assertTrue(glog.contains("response: nextprime,11,11"));

    }

    @Test
    public void testMultiThreadWithWaitFirstNextPrime() throws InterruptedException {

        final PrimeManager g = new PrimeManager(100);

        // Berechnung starten
        g.startWorker(10);
        Thread.sleep(200); // Wir wollen nur bereits sicher schon berechnete Zahlen testen

        final Thread c1 = new Thread(new Runnable() {
            public void run() {
                try {
                    g.nextPrime(150);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                } // Eine Assertion käme da nie raus, Ergebnis in dem Fall wird über das Log
                  // getestet
            }
        });
        c1.start();

        final Thread c2 = new Thread(new Runnable() {
            public void run() {
                try {
                    g.nextPrime(4);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        });
        c2.start();

        // Warten bis beide Threads zurückgekehrt sind
        c1.join();
        c2.join();

        // Berechnung kann sicher gestoppt werden
        g.stopWorker();

        List<String> glog = g.getLog();

        // nur die ersten fünf interessanten Logeinträge herausfiltern
        List<String> glog_stripped = new LinkedList<>();
        int i = 0;
        for (String s : glog) {
            if (s.contains("found prime")) {
                glog_stripped.add(s);
                i++;
            }
            if (i == 5) {
                break;
            }
        }

        // Listen auf gleiche Einträge prüfen, mindestens die notwendigen Primzahlen gefunden?
        List<String> primes = new LinkedList<>(Arrays.asList("found prime: 2", "found prime: 3",
                "found prime: 5", "found prime: 7", "found prime: 11"));
        Assert.assertEquals(glog_stripped, primes);

        // Interessante Einträge stichprobenhaft überprüfen
        Assert.assertTrue(glog.contains("response: nextprime,150,151"));
        Assert.assertTrue(glog.contains("response: nextprime,4,5"));

    }

    @Test
    public void primeFactorsSimple() throws InterruptedException {

        final PrimeManager g = new PrimeManager(50);

        // Berechnung starten
        g.startWorker(10);
        Thread.sleep(200); // Wir wollen nur bereits sicher schon berechnete Zahlen testen

        final Thread c1 = new Thread(new Runnable() {
            public void run() {
                try {
                    g.primeFactors(100);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                } // Eine Assertion käme da nie raus, Ergebnis in dem Fall wird über das Log
                  // getestet
            }
        });
        c1.start();

        // Warten bis beide Threads zurückgekehrt sind
        c1.join();

        // Berechnung kann sicher gestoppt werden
        g.stopWorker();

        List<String> glog = g.getLog();

        // nur die ersten fünf interessanten Logeinträge herausfiltern
        List<String> glog_stripped = new LinkedList<>();
        int i = 0;
        for (String s : glog) {
            if (s.contains("found prime")) {
                glog_stripped.add(s);
                i++;
            }
            if (i == 5) {
                break;
            }
        }

        // Listen auf gleiche Einträge prüfen, mindestens die notwendigen Primzahlen gefunden?
        List<String> primes = new LinkedList<>(Arrays.asList("found prime: 2", "found prime: 3",
                "found prime: 5", "found prime: 7", "found prime: 11"));
        Assert.assertEquals(glog_stripped, primes);

        // Interessante Einträge stichprobenhaft überprüfen
        Assert.assertTrue(glog.contains("response: primefactors,100,[2, 2, 5, 5]"));

    }

    @Test
    public void primeFactorsComplex() throws InterruptedException {

        final PrimeManager g = new PrimeManager(5);

        // Berechnung starten
        g.startWorker(10);
        Thread.sleep(200); // Wir wollen nur bereits sicher schon berechnete Zahlen testen

        final Thread c1 = new Thread(new Runnable() {
            public void run() {
                try {
                    g.primeFactors(28);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                } // Eine Assertion käme da nie raus, Ergebnis in dem Fall wird über das Log
                  // getestet
            }
        });
        c1.start();

        // Warten bis beide Threads zurückgekehrt sind
        c1.join();

        // Berechnung kann sicher gestoppt werden
        g.stopWorker();

        List<String> glog = g.getLog();

        // nur die ersten fünf interessanten Logeinträge herausfiltern
        List<String> glog_stripped = new LinkedList<>();
        int i = 0;
        for (String s : glog) {
            if (s.contains("found prime")) {
                glog_stripped.add(s);
                i++;
            }
            if (i == 5) {
                break;
            }
        }

        // Listen auf gleiche Einträge prüfen, mindestens die notwendigen Primzahlen gefunden?
        List<String> primes = new LinkedList<>(Arrays.asList("found prime: 2", "found prime: 3",
                "found prime: 5", "found prime: 7", "found prime: 11"));
        Assert.assertEquals(glog_stripped, primes);

        // Interessante Einträge stichprobenhaft überprüfen
        Assert.assertTrue(glog.contains("response: primefactors,28,[2, 2, 7]"));

    }

    @Test
    public void primeFactorsComplex2() throws InterruptedException {

        final PrimeManager g = new PrimeManager(5);

        // Berechnung starten
        g.startWorker(10);
        Thread.sleep(200); // Wir wollen nur bereits sicher schon berechnete Zahlen testen

        final Thread c1 = new Thread(new Runnable() {
            public void run() {
                try {
                    g.primeFactors(45);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                } // Eine Assertion käme da nie raus, Ergebnis in dem Fall wird über das Log
                  // getestet
            }
        });
        c1.start();

        // Warten bis beide Threads zurückgekehrt sind
        c1.join();

        // Berechnung kann sicher gestoppt werden
        g.stopWorker();

        List<String> glog = g.getLog();

        // nur die ersten fünf interessanten Logeinträge herausfiltern
        List<String> glog_stripped = new LinkedList<>();
        int i = 0;
        for (String s : glog) {
            if (s.contains("found prime")) {
                glog_stripped.add(s);
                i++;
            }
            if (i == 5) {
                break;
            }
        }

        // Listen auf gleiche Einträge prüfen, mindestens die notwendigen Primzahlen gefunden?
        List<String> primes = new LinkedList<>(Arrays.asList("found prime: 2", "found prime: 3",
                "found prime: 5", "found prime: 7", "found prime: 11"));
        Assert.assertEquals(glog_stripped, primes);

        // Interessante Einträge stichprobenhaft überprüfen
        Assert.assertTrue(glog.contains("response: primefactors,45,[3, 3, 5]"));

    }

}
