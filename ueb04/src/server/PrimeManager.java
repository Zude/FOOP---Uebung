package server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import helper.Logger;

/**
 * 
 * Generiert dauerhaft Primzahlen in einem eigenen Thread und bietet auf Basis der generierten
 * Primzahlen die Ermittlung der jeweils nächstgrößeren Primzahl zu einer übergebenen Zahl und die
 * Zerlegung einer übergebenen Zahl in Primfaktoren an.
 * 
 * Sofern die benötigte(n) Primzahl(en) zum Anfragezeitpunkt schon berechnet wurde(n), werden
 * Anfragen sofort beantwortet. Falls dies nicht der Fall ist, warten die Anfragen bis die
 * entsprechende(n) Primzahl(en) berechnet wurde(n).
 * 
 * Der PrimeManager muss jederzeit eine beliebige Anzahl von Anfragen gleichzeitig bearbeiten
 * können.
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 * 
 */
public class PrimeManager implements Logger {

    private List<String> primeLog = new ArrayList<String>();
    // TODO: Korrekter Typ ?
    private volatile List<Long> primeNumbers = new ArrayList<Long>();
    private Thread workerThread = new Thread(this::calcPrimes);

    private long calcDelay;
    private long currentNumber = 2;
    private long lastPrime = 2;
    private volatile boolean isWorking = false;
    private int partitionSize;

    /**
     * Konstruktor.
     * 
     * Mittels partitionSize wird die Größe der Partionen für die Primfaktorenzerlegung angegeben
     * (Anzahl der Primzahlen (Faktorkandidaten), die von einem Task maximal getestet werden).
     * 
     * @pre partitionSize ist größer gleich 1
     * 
     * @param partitionSize Größe der Partition für ForkJoin-Primfaktorenzerlegung
     */
    public PrimeManager(int partitionSize) {
        assert partitionSize >= 1 : "Es können nur Intervalle (>= 1) gebildet werden.";

        this.partitionSize = partitionSize;
    }

    /**
     * Liefert zu der übergebenen Zahl die nächstgrößere Primzahl. Ist die übergebene Zahl selbst
     * bereits prim, so wird sie als Ergebnis zurückgegeben.
     * 
     * Wenn die Aussage zum Zeitpunkt der Anfrage noch nicht getroffen werden kann, wird so lange
     * gewartet bis dies möglich ist.
     * 
     * @pre Die übergebene Zahl muss eine positive Ganzzahl (inkl. 0) sein
     * @param q Die Zahl für die, die nächstgrößere Primzahl ermittelt werden soll
     * @return die nächstgrößere Primzahl oder die Zahl selbst (falls sie selbst prim ist)
     * @throws InterruptedException
     */
    public long nextPrime(long q) throws InterruptedException {
        assert (q >= 0) : "nextPrime muss mit einer positiven Ganzzahl aufgerufen werden.";

        addEntry("requested: nextprime," + q);

        // TODO: Synchronized größe ok ?
        synchronized (this) {
            while (lastPrime < q) {
                this.wait();
            }
        }

        for (long prime : primeNumbers) {
            if (prime >= q) {
                addEntry("response: nextprime," + q + "," + prime);
                return prime;
            }
        }

        // TODO: Fehlerfall korrekt händeln
        return -10;
    }

    /**
     * Liefert eine aufsteigend sortierte Liste aller Primfakoren der übergebenen Zahl q.
     * 
     * Wenn die Berechnung zum Zeitpunkt der Anfrage noch nicht stattfinden kann, wird so lange
     * gewartet bis dies möglich ist.
     * 
     * @pre Es dürfen nur positive Ganzzahlen geprüft werden, die größer gleich 2 sind (siehe
     *      Definition Primzahlen)
     * @param q Die zu zerlegende Zahl
     * @return Liste mit denm aufsteigend sortierten Primfaktoren von q
     * @throws InterruptedException
     */
    public List<Long> primeFactors(long q) throws InterruptedException {
        assert (q >= 2) : "PrimeFactors muss mit einer positiven Ganzzahl >=2 aufgerufen werden.";

        addEntry("requested: primefactors," + q);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        synchronized (this) {
            while (lastPrime < q / 2) {
                this.wait();
            }
        }

        // TODO: Liste wirklich kopieren ?
        List<Long> listDummy = new ArrayList<Long>(primeNumbers);

        PrimeFactorWorker worker =
                new PrimeFactorWorker(partitionSize, q, 0, (int) primeNumbers.size(), listDummy);

        List<Long> resultList = forkJoinPool.invoke(worker);
        addEntry("response: primefactors," + q + "," + resultList.toString().replace(" ", ""));
        return resultList;
    }

    private static class PrimeFactorWorker extends RecursiveTask<List<Long>> {

        private final int MAXSIZE;
        private long number;
        private final int start;
        private final int end;
        private volatile List<Long> primeNumbers = new ArrayList<Long>();

        public PrimeFactorWorker(int paritionSize, long number, int start, int end,
                List<Long> primeList) {

            this.MAXSIZE = paritionSize;
            this.number = number;
            this.start = start;
            this.end = end;
            this.primeNumbers = primeList;
        }

        @Override
        protected List<Long> compute() {

            List<Long> listDummy = new ArrayList<Long>();
            // TODO: ???
            List<Long> resultList = Collections.synchronizedList(listDummy);

            if (end - start > MAXSIZE) {

                int mid = (start + (end - start) / 2);

                ForkJoinTask<List<Long>> lForkJoinTask =
                        new PrimeFactorWorker(MAXSIZE, number, start, mid, primeNumbers).fork();
                ForkJoinTask<List<Long>> rForkJoinTask =
                        new PrimeFactorWorker(MAXSIZE, number, mid + 1, end, primeNumbers).fork();

                resultList.addAll(lForkJoinTask.join());
                resultList.addAll(rForkJoinTask.join());

            } else {

                long upperBorder = number / 2;
                int i = start;

                while (i < end && primeNumbers.get(i) <= upperBorder) {
                    if (number % primeNumbers.get(i) == 0) {
                        resultList.add(primeNumbers.get(i));
                        number = number / primeNumbers.get(i);
                        i = start;
                    } else {
                        i++;
                    }
                }
            }

            return resultList;
        }

    }

    /**
     * Liefert eine Kopie aller bis zum aktuellen Zeitpunkt gefundenen Primzahlen.
     * 
     * Diese Methode ist nur zu Test- und Debugzwecken vorgesehen und darf auch nur dafür verwendet
     * werden.
     * 
     * @return Eine Kopie aller bis jetzt gefundenen Primzahlen.
     */
    public Collection<Long> knownPrimes() {

        List<Long> knownPrimes = new ArrayList<Long>(primeNumbers);

        return knownPrimes;
    }

    /**
     * Startet den PrimeWorker-Thread und somit die Berechnung der Primzahlen ab der Zahl 2. Das
     * übergebene delay wird verwendet um die Berechnungen jeweils um den übergebenen Wert in ms zu
     * verzögern. Dabei wird nach jeder geprüften / berechneten Zahl das delay durchgeführt.
     * 
     * Sollte die Berechnung unterbrochen worden sein und wieder gestartet werden, so wird sie an
     * der Stelle fortgesetzt, an der sie unterbrochen wurde.
     * 
     * @pre delay ist größer gleich 0
     * @param delay gewünschte Verzögerung zwischen zwei Berechnungen (s.o.)
     */
    public void startWorker(long delay) {
        assert delay >= 0 : "Delay muss >= 0 sein!";

        calcDelay = delay;
        isWorking = true;

        workerThread.start();
        System.out.println("Prime Worker hat angefangen zu arbeiten!");
    }

    private void calcPrimes() {

        // Add 2 as first PrimeNumber
        currentNumber = 2;
        primeNumbers.add(currentNumber);
        lastPrime = currentNumber;
        addEntry("found prime: " + currentNumber);
        currentNumber++;

        while (isWorking && currentNumber < 1000) {
            try {

                if (isPrime(currentNumber)) {
                    primeNumbers.add(currentNumber);
                    lastPrime = currentNumber;

                    synchronized (this) {
                        this.notifyAll();
                    }

                    addEntry("found prime: " + currentNumber);
                }

                currentNumber++;

                Thread.sleep(calcDelay);
            } catch (InterruptedException e) {
                System.out.println("Prime Worker hat aufgehört zu arbeiten!");
                break;
            }
        }
    }

    private boolean isPrime(long num) {

        if (num == 1) {
            return false;
        }

        long upperBorder = (long) Math.sqrt(num);
        int i = 0;

        while (primeNumbers.get(i) <= upperBorder) {
            if (num % primeNumbers.get(i) == 0) {
                return false;
            }

            i++;
        }

        return true;
    }

    /**
     * Beendet die Berechnung der Primzahlen. Die bereits berechneten Primzahlen werden dabei nicht
     * verworfen.
     */
    public void stopWorker() {
        isWorking = false;
        // TODO: Raus oder rein?
        workerThread.interrupt();
    }

    @Override
    public List<String> getLog() {
        return primeLog;
    }

    @Override
    public void addEntry(String e) {
        System.out.println("PrimeLog: " + e);
        primeLog.add(e);
    }

}
