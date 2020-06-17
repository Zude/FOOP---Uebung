package sciencelab;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Ein potentiell leicht mental instabiler Wisschenschaftler, der in einem
 * Wissenschaftler-Gemeinschaftlabor arbeitet.
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 * 
 */
public class Scientist {

    private boolean inTryoutSleep = false;

    /**
     * Die Multitools.
     */
    private final MultiTool left, right;

    /**
     * So lange bastet er.
     */
    private final long tinkeringTimeMS;

    /**
     * So lange probiert er aus.
     */
    private final long tryoutTimeMS;

    /**
     * Name: Nur fuer debugging benötigt.
     */
    private final String id;

    /**
     * Maximale Anzahl an Durchläufen.
     */
    private final int k;

    /**
     * Zeit, nach der er ohne Ausprobieren wahninnig wird.
     */
    private final long saneTimeMS;

    /**
     * Startzeit (nur für Logging).
     */
    private long startTime;

    private volatile boolean isInsane = false;

    /**
     * Konstruktor.
     * 
     * @param l linkes Multitool
     * @param r rechtes Multitool
     * @param tryoutTime Dauer des Ausprobierens in ms
     * @param tinkeringTime Dauer des Bastelns in ms
     * @param saneTime Zeit bis zum Wahnsinnigwerden (nur für Aufgabenteil C interessant)
     * @param idStr ID (nur für Debugging)
     * @param numSteps Anzahl der Basteln-Ausprobieren-Durchläufe
     */
    public Scientist(MultiTool l, MultiTool r, long tryoutTime, long tinkeringTime, long saneTime,
            String idStr, int numSteps) {
        this.left = l;
        this.right = r;
        this.tryoutTimeMS = tryoutTime;
        this.tinkeringTimeMS = tinkeringTime;
        this.saneTimeMS = saneTime;
        this.id = idStr;
        this.k = numSteps;
    }

    /**
     * 
     * @return Ist der Wissenschaftler verrückt geworden?
     */
    public boolean isInsane() {

        return isInsane;
    }

    public boolean isAsleep() {
        return inTryoutSleep;
    }

    /**
     * Logging. Schreibt id + Nachricht + vergangene Zeit auf stdout und flusht den Ausgabestream.
     * 
     * Für besser lesbare Zeitangaben sollte this.startTime zu Beginn der aufrufenden Methode
     * (startAlgoA|B|C) mit System.currentTimeMillis() initialisiert werden.
     * 
     * @param s Nachricht, die ausgegeben werden soll
     */
    private void printLog(String s) {
        System.out.println(id + ": " + s + " " + (System.currentTimeMillis() - this.startTime));
        System.out.flush();
    }

    /**
     * Implementierung Algorithmus A.
     */
    void startAlgoA() {
        startTime = System.currentTimeMillis();

        int round = 0;
        try {
            while (round < k) {

                left.takeTool(this);

                printLog("Linkes Multifunktionswerkzeug nehmen");

                right.takeTool(this);

                printLog("Rechtes Multifunktionswerkzeug nehmen");

                Thread.sleep(tinkeringTimeMS);
                printLog("Basteln");

                left.setFree(this);
                right.setFree(this);
                printLog("Beide Multifunktionswerkzeug zurücklegen");

                Thread.sleep(tryoutTimeMS);
                printLog("Ausprobieren");

                round++;

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printLog("Beendet");

    }

    /**
     * Implementierung Algorithmus B.
     */
    void startAlgoB() {
        startTime = System.currentTimeMillis();

        int round = 0;
        try {
            while (round < k) {

                left.takeTool(this);

                printLog("Linkes Multifunktionswerkzeug nehmen");

                boolean check = right.isFree();

                printLog("Ist rechtes Multifunktionswerkzeug frei?");
                if (check) {
                    printLog("Ja:");

                    right.takeTool(this);

                    printLog("Rechtes Multifunktionswerkzeug nehmen");

                    Thread.sleep(tinkeringTimeMS);
                    printLog("Basteln");

                    left.setFree(this);
                    right.setFree(this);
                    printLog("Beide Multifunktionswerkzeug zurücklegen");

                    Thread.sleep(tryoutTimeMS);
                    printLog("Ausprobieren");

                    round++;
                } else {
                    printLog("Nein");

                    left.setFree(this);
                    printLog("Linkes Multifunktionswerkzeug zurücklegen");

                    synchronized (right) {
                        while (!right.isFree()) {
                            right.wait();
                        }
                    }
                    printLog("Warten, bis rechtes Multifunktionswerkzeug frei wird");
                }

            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        printLog("Beendet");
    }

    /**
     * Implementierung Algorithmus C.
     */
    void startAlgoC() {
        startTime = System.currentTimeMillis();

        class MyTimerTask extends TimerTask {

            private double starvingSince = 0;
            private final double timeToGetCrazy = saneTimeMS;
            private Thread scientistThread;
            private Scientist scientist;

            MyTimerTask(Thread thread, Scientist scientist) {
                this.scientistThread = thread;
                this.scientist = scientist;
            }

            @Override
            public void run() {

                if (!scientist.inTryoutSleep) {
                    starvingSince++;
                }

                if (starvingSince >= timeToGetCrazy) {
                    try {

                        if (!scientist.inTryoutSleep) {
                            scientist.isInsane = true;
                            scientistThread.interrupt();
                            printLog("Muahahahahahahaaaaaaa!");
                            starvingSince = 0;
                        }
                    } catch (SecurityException ex) {
                        printLog("Interrupted");
                    }
                }
            }

            public void reset() {
                this.starvingSince = 0;
            }

        }

        Timer timer = new Timer(false);

        MyTimerTask myTask = new MyTimerTask(Thread.currentThread(), this);

        timer.schedule(myTask, 0, 1);

        int round = 0;
        while (round < k && !isInsane) {

            try {

                left.takeTool(this);
                printLog("Linkes Multifunktionswerkzeug nehmen");

                boolean check = right.isFree();

                printLog("Ist rechtes Multifunktionswerkzeug frei?");

                if (check) {
                    printLog("Ja:");

                    right.takeTool(this);
                    printLog("Rechtes Multifunktionswerkzeug nehmen");

                    Thread.sleep(tinkeringTimeMS);
                    printLog("Basteln");

                    myTask.reset();

                    left.setFree(this);
                    right.setFree(this);
                    printLog("Beide Multifunktionswerkzeug zurücklegen");

                    inTryoutSleep = true;
                    Thread.sleep(tryoutTimeMS);
                    inTryoutSleep = false;
                    printLog("Ausprobieren");

                    round++;
                } else {
                    printLog("Nein");

                    left.setFree(this);
                    printLog("Linkes Multifunktionswerkzeug zurücklegen");

                    synchronized (right) {
                        while (!right.isFree()) {
                            right.wait();
                        }

                    }

                    printLog("Warten, bis rechtes Multifunktionswerkzeug frei wird");
                }

            } catch (InterruptedException e) {
                left.setFree(this);
                right.setFree(this);
                break;
            } catch (SecurityException ex) {
                left.setFree(this);
                right.setFree(this);
                break;
            }
        }
        myTask.cancel();
        timer.cancel();
        timer.purge();
        printLog("Beendet");
    }
}
