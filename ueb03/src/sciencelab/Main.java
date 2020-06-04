package sciencelab;

import java.util.List;

/**
 * Klasse zum Starten des "Wissenschaftler-Gemeinschaftslabor-Problem".
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 */
public class Main {

    /**
     * Startet alles und wartet nicht auf das Beenden der gestarteten Threads. Alle Zeitangaben sind
     * in Millisekunden.
     * 
     * @param n Anzahl Wissenschaftler und Anzahl Multitools
     * @param maxSteps Anzahl Durchläufe pro Wissenschaftler
     * @param tinkeringTime Dauer des Bastelns
     * @param tryoutTime Dauer des Ausprobierens
     * @param saneTime Zeit bis zum Wahnsinngwerden (nur fuer Algorithmus C interessant)
     * @param type Aufgabenteil
     * @param res Liste der Wissenschaftler
     * 
     * @pre res ist null oder leer
     * @post falls res nicht null ist, enthält res alle erzeugten Wissenschaftler-Objekte
     * 
     * @return Array aller Wissenschaftler-Threads
     */
    public Thread[] startAll(int n, int maxSteps, long tinkeringTime, long tryoutTime,
            long saneTime, AlgorithmType type, List<Scientist> res) {

    }

    /**
     * Startet {@link Scientist} als Thread.
     * 
     * @param s Scientist, der in einem Thread gestartet werden soll
     * @param type Welcher Algorithmus soll laufen
     * @return der gestartete Thread
     */
    private Thread startInThread(final Scientist s, final AlgorithmType type) {

        Thread thread = null;
        switch (type) {
            case A:
                thread = new Thread(s::startAlgoA);
                break;
            case B:
                thread = new Thread(s::startAlgoB);
                break;
            case C:
                thread = new Thread(s::startAlgoC);
                break;
            default:
                assert false;
        }

        thread.start();
        return thread;
    }
}
