package sciencelab;

/**
 * Ein potentiell leicht mental instabiler Wisschenschaftler, der in einem
 * Wissenschaftler-Gemeinschaftlabor arbeitet.
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 * 
 */
public class Scientist {

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

        return false;
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

    // TODO Dürfen die Algos selbst die Exepctions werfen oder müssen wirklich über all die
    // Try-Catch Blöcke sein?

    /**
     * Implementierung Algorithmus A.
     */
    void startAlgoA() {
        startTime = System.currentTimeMillis();

        int round = 0;
        while (round < k) {

            try {
                left.takeTool(this);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            printLog("Linkes Multifunktionswerkzeug nehmen");
            try {
                right.takeTool(this);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            printLog("Rechtes Multifunktionswerkzeug nehmen");

            try {
                Thread.sleep(tinkeringTimeMS);
                printLog("Basteln");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            left.setFree(this);
            right.setFree(this);
            printLog("Beide Multifunktionswerkzeug zurücklegen");

            try {
                Thread.sleep(tryoutTimeMS);
                printLog("Ausprobieren");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            round++;

        }

        printLog("Beendet");

    }

    /**
     * Implementierung Algorithmus B.
     */
    void startAlgoB() {
        startTime = System.currentTimeMillis();

        int round = 0;
        while (round < k) {
            try {
                left.takeTool(this);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            printLog("Linkes Multifunktionswerkzeug nehmen");

            boolean check = right.isFree();
            printLog("Ist rechtes Multifunktionswerkzeug frei?");
            if (check) { // TODO isFree() und dann doppelt printLog?
                printLog("Ja:");

                try {
                    right.takeTool(this);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                printLog("Rechtes Multifunktionswerkzeug nehmen");

                try {
                    Thread.sleep(tinkeringTimeMS);
                    printLog("Basteln");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                left.setFree(this);
                right.setFree(this);
                printLog("Beide Multifunktionswerkzeug zurücklegen");

                try {
                    Thread.sleep(tryoutTimeMS);
                    printLog("Ausprobieren");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
                printLog("Nein");

                left.setFree(this);
                printLog("Linkes Multifunktionswerkzeug zurücklegen");

                synchronized (right) {
                    while (!right.isFree()) {
                        try {
                            right.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }

                printLog(
                        "Warten, bis rechtes Multifunktionswerkzeug frei wird (aber nicht direkt nehmen!)");
            }

            round++;
        }
        printLog("Beendet");
    }

    /**
     * Implementierung Algorithmus C.
     */
    void startAlgoC() {
        startTime = System.currentTimeMillis();

        // TODO Timer fürs Wahnsinnig werden fehlt noch
        int round = 0;
        while (round < k) {
            try {
                left.takeTool(this);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            printLog("Linkes Multifunktionswerkzeug nehmen");

            boolean check = right.isFree();
            printLog("Ist rechtes Multifunktionswerkzeug frei?");
            if (check) {
                printLog("Ja:");

                try {
                    right.takeTool(this);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                printLog("Rechtes Multifunktionswerkzeug nehmen");

                try {
                    Thread.sleep(tinkeringTimeMS);
                    printLog("Basteln");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                left.setFree(this);
                right.setFree(this);
                printLog("Beide Multifunktionswerkzeug zurücklegen");

                try {
                    Thread.sleep(tryoutTimeMS);
                    printLog("Ausprobieren");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
                printLog("Nein");

                left.setFree(this);
                printLog("Linkes Multifunktionswerkzeug zurücklegen");

                while (!right.isFree()) {
                    // just wait...
                }
                printLog(
                        "Warten, bis rechtes Multifunktionswerkzeug frei wird (aber nicht direkt nehmen!)");
            }

            round++;
        }
        printLog("Beendet");
    }
}
