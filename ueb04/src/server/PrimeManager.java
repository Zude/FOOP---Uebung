package server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import helper.Logger;
import helper.MessageType;

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

    // Es können mehrere Anfragen gleichzeitig laufen, also könnte auch gleichzeitig geschrieben
    // werden
    private List<String> primeLog = Collections.synchronizedList(new ArrayList<String>());
    // TODO: Korrekter Typ ?
    private volatile ConcurrentSkipListSet<Long> primeNumbers = new ConcurrentSkipListSet<Long>();
    private Thread workerThread = new Thread(this::calcPrimes);
    private Set<Long> waitingList = new ConcurrentSkipListSet<Long>();
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
     */
    public long nextPrime(long q) {
        assert (q >= 0) : "nextPrime muss mit einer positiven Ganzzahl aufgerufen werden.";

        addEntry("requested: " + MessageType.NEXTPRIME.toString().toLowerCase() + "," + q);

        Long waitingLong = q;

        waitingList.add(waitingLong);

        synchronized (waitingLong) {
            while (lastPrime < q) {
                try {
                    waitingLong.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        waitingList.remove(waitingLong);

        for (long prime : primeNumbers) {
            if (prime >= q) {
                addEntry("response: " + MessageType.NEXTPRIME.toString().toLowerCase() + "," + q
                        + "," + prime);
                return prime;
            }
        }

        // TODO: Fehlerfall korrekt händeln
        final int err = -10; // Magic Number fix
        return err;
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
     */
    public List<Long> primeFactors(long q) {
        assert (q >= 2) : "PrimeFactors muss mit einer positiven Ganzzahl >=2 aufgerufen werden.";

        addEntry("requested: " + MessageType.PRIMEFACTORS.toString().toLowerCase() + "," + q);

        List<Long> resultList = new ArrayList<Long>();

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        Long waitingLong = q / 2;

        synchronized (waitingLong) {
            while (lastPrime < q / 2) {
                try {
                    waitingList.add(waitingLong);
                    waitingLong.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        waitingList.remove(waitingLong);

        // TODO: Liste wirklich kopieren ?
        // Eine CopyOnWriteArrayList würde dies überflüssig machen, aber dann wird das schreiben
        // deutlich langsamer

        if (isPrimeForList(q)) {
            resultList.add(q);
        } else {
            List<Long> listDummy = new ArrayList<Long>(primeNumbers);

            PrimeFactorWorker worker =
                    new PrimeFactorWorker(partitionSize, q, 0, listDummy.size(), listDummy);

            resultList = forkJoinPool.invoke(worker);
        }

        addEntry("response: " + MessageType.PRIMEFACTORS.toString().toLowerCase() + "," + q + ","
                + resultList.toString().replace(" ", ""));
        return resultList;
    }

    private class PrimeFactorWorker extends RecursiveTask<List<Long>> {

        private final int maxsize;
        private long number;
        private final int start;
        private final int end;
        private volatile List<Long> copyNumbers = new ArrayList<Long>();

        PrimeFactorWorker(int paritionSize, long number, int start, int end,
                List<Long> copyNumbers) {

            this.maxsize = paritionSize;
            this.number = number;
            this.start = start;
            this.end = end;
            this.copyNumbers = copyNumbers;
        }

        @Override
        protected List<Long> compute() {

            // TODO: ???
            // Sollte eine gute Wahl fürs ständige schreiben sein, aber das lesen ist "gefährlich",
            // sollte aber gehen weil nur nach der Berechnung gelesen wird und dann auch nur mit
            // einem Thread
            List<Long> resultList = Collections.synchronizedList(new ArrayList<Long>());

            if (end - start > maxsize) {

                int mid = (start + (end - start) / 2);

                ForkJoinTask<List<Long>> lForkJoinTask =
                        new PrimeFactorWorker(maxsize, number, start, mid, copyNumbers).fork();
                ForkJoinTask<List<Long>> rForkJoinTask =
                        new PrimeFactorWorker(maxsize, number, mid, end, copyNumbers).fork();

                resultList.addAll(lForkJoinTask.join());
                resultList.addAll(rForkJoinTask.join());

            } else {

                long upperBorder = number / 2;
                int i = start;

                while (i < end && copyNumbers.get(i) <= upperBorder) {
                    if (number % copyNumbers.get(i) == 0) {
                        resultList.add(copyNumbers.get(i));
                        number = number / copyNumbers.get(i);
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

        // TODO remove
        // final int limitPrime = 1000;
        // while (isWorking && currentNumber < limitPrime) {
        while (isWorking) {
            try {

                if (isPrimeForList(currentNumber)) {
                    primeNumbers.add(currentNumber);
                    lastPrime = currentNumber;

                    for (Long numberToCheck : waitingList) {
                        if (currentNumber >= numberToCheck) {
                            synchronized (numberToCheck) {
                                numberToCheck.notifyAll();
                            }
                        }
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

    private boolean isPrimeForList(long num) {

        if (num == 1) {
            return false;
        }

        long upperBorder = (long) Math.sqrt(num);

        for (Long primeNumber : primeNumbers) {
            if (primeNumber <= upperBorder) {
                if (num % primeNumber == 0) {
                    return false;
                }
            } else {
                break;
            }
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
