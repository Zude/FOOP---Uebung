package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import helper.Logger;
import helper.MessageType;

/**
 * Ein Server der per TCP-Verbindung Anfragen an einen PrimeManager ermöglicht. Der Server kann
 * hierbei mit beliebige vielen Clients gleichzeitig kommunzieren. Die Kommunikation zwischen Client
 * und Server verläuft stets synchron. Clients dürfen sich allerdings nicht gegenseitig blockieren.
 * 
 * @author kar, mhe, Lars Sander, Alexander L�ffler
 * 
 */
public class PrimeServer implements Logger {

    protected ServerSocket serverSocket;
    protected volatile boolean openForNewConnections = true;

    private List<String> serverLog = new ArrayList<String>();
    private final int msgLength = 3; // Normale "länge" der Socket-Nachrichten

    // TODO Check ob Thread sichere Collection wichtig sein könnte.
    private List<Thread> openConnections = new ArrayList<Thread>();

    private PrimeManager primeManager;

    /**
     * Eine Hilfsklasse für jeden der Threads die jeder Client zur kommunikation verwendet. Extends
     * Thread damit in stopServer() einfach mit join() gewartet werden kann.
     * 
     * @author Lars Sander, Alexander Löffler
     *
     */
    private class ClientThread extends Thread {

        private Socket clientSocket;

        private PrintWriter out;
        private BufferedReader in;

        private int id;

        ClientThread(int ID, Socket client) {
            this.id = ID;
            this.clientSocket = client;
        }

        // TODO überlegen ob es Sinn macht das beabeiten der Antworten in einzelne Methoden
        // auszulagern
        @Override
        public void run() {

            System.out.println("ClientThread gestartet ID:" + id);

            try {

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String msg;

                // Wenn es in in keine Zeilen mehr gibt sollte der Client sich beendet haben.
                while ((msg = in.readLine()) != null) {

                    // Nachricht kann 1 oder 3 mit , geteilte Strings enthalten
                    // (1): Kommt nur bei HALLO vor
                    // (3): ID, MSG, ZAHL
                    String[] arrMsg = msg.split(",");

                    // TODO Was für Fehler können bei .valueOf auftreten und sollten wir dort was
                    // ausgeben?

                    if (arrMsg.length == 1) {
                        if (MessageType.valueOf(arrMsg[0]) == MessageType.HALLO) {

                            out.println(id);

                            addEntry("client connected," + id);
                        }

                    } else if (arrMsg.length == msgLength) {

                        StringJoiner logStr = new StringJoiner(",");
                        logStr.add("requested: " + String.valueOf(id));

                        // Sicherstellen das es die gleiche ID ist
                        if (Integer.valueOf(arrMsg[0]) == id) {

                            switch (MessageType.valueOf(arrMsg[1])) {
                                case PRIMEFACTORS:

                                    List<Long> primList =
                                            primeManager.primeFactors(Long.valueOf(arrMsg[2]));

                                    out.println(primList);

                                    logStr.add(MessageType.PRIMEFACTORS.toString().toLowerCase());
                                    logStr.add(arrMsg[2]);
                                    logStr.add(primList.toString().replaceAll(" ", ""));

                                    addEntry(logStr.toString());
                                    break;
                                case NEXTPRIME:

                                    Long prim = primeManager.nextPrime(Long.valueOf(arrMsg[2]));

                                    out.println(prim);

                                    logStr.add(MessageType.NEXTPRIME.toString().toLowerCase());
                                    logStr.add(arrMsg[2]);
                                    logStr.add(prim.toString());

                                    addEntry(logStr.toString());
                                    break;

                                default:
                                    System.err.println("Ungültiger MSG Type :" + arrMsg[1]);
                                    break;
                            }
                        } else {
                            System.err.println("Falsche ID");
                        }

                    } else {
                        System.err.println("Ungültige Nachricht: " + msg);
                    }

                }

                addEntry("client disconnected," + id);

                // TODO Unnötigen Speicher freigeben
                /*
                 * if (!openConnections.remove(this)) {
                 * System.err.println("Thread war nicht in openConnections enthalten"); }
                 */
                System.out.println("Thread beendet ID:" + id + " CC: " + openConnections.size());

                // TODO Sollten die Reader/Writer hier beendet werden. Wo ist der Unterschied ob
                // hier oder am Client der Socket geschlossen wird?

            } catch (IOException | NumberFormatException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    /**
     * Konstruktor.
     * 
     * @pre partitionSize größer 0
     * 
     * @param port Der zu nutzene TCP-Port
     * @param partitionSize Paritionsgröße für den PrimeGenerator
     * 
     * @throws IOException Netzwerkfehler
     */
    public PrimeServer(int port, int partitionSize) throws IOException {
        assert partitionSize >= 1 : "PartitionSize muss >= 1 sein.";

        serverSocket = new ServerSocket(port);

        primeManager = new PrimeManager(partitionSize);

    }

    /**
     * Konstruktur für verwendung mit Dummy PrimeManager
     * 
     * @pre partitionSize größer 0
     * 
     * @param port Der zu nutzene TCP-Port
     * @param dummy Primemanager für Tests
     * 
     * @throws IOException Netzwerkfehler
     */
    public PrimeServer(int port, PrimeManager dummy) throws IOException {
        serverSocket = new ServerSocket(port);

        primeManager = dummy;
    }

    /**
     * Startet den Server und den Worker des PrimeGenerators, welcher die Berechnungen übernimmt,
     * mit dem entsprechenden delay. Diese Methode muss sofort zurückkehren.
     * 
     * Für jede Clientverbindung wird ein eigener Thread gestartet.
     * 
     * @pre delay ist größer gleich 0
     * @param delay Das delay in ms für den PrimeGenerator
     * @throws IOException Netzwerkfehler
     */
    public void startServer(long delay) throws IOException {
        assert delay >= 0 : "Delay muss > 0 sein!";

        System.out.println("Server gestartet");

        primeManager.startWorker(delay);

        // Das annehmen neuer Verbindungen geschieht hier in einem eigenen Thread, weil ansonsten
        // die JUnit Tests nicht weiterlaufen können und es beim .accept() zu einer Blockade kommt.
        // Beim seperaten Testen sollte eine while(true) Schleife genügen. Aber dann würde auch
        // stopServer() nicht mehr funktionieren und es müsste in der Schleife nach den offenen
        // Verbindungen geschaut werden.
        Thread listener = new Thread(() -> {

            int nextID = 1;
            Socket clientSocket;

            // TODO Die Schleife wird nach dem aufruf von stopServer() immer noch 1 Mal betreten.
            // Bzw. wartet der Socket auf eine neue Verbindung, aber dann wir der Socket geschlossen
            // und es wird ein Fehler geworfen.
            while (openForNewConnections && !serverSocket.isClosed()) {

                try {
                    clientSocket = serverSocket.accept();
                    ClientThread ct = new ClientThread(nextID, clientSocket);
                    openConnections.add(ct);
                    ct.start();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                nextID++;

            }
        });

        listener.start();

    }

    /**
     * Stoppt den Server. Es werden keine neuen Verbindungen mehr angenommen. Bereits bestehende
     * Verbindungen laufen jedoch normal weiter und Anfragen von bereits verbundenen Clients werden
     * noch abgearbeitet. Die Methode kehrt erst zurück, wenn alle Clients die Verbindung beendet
     * haben.
     * 
     * Erst, wenn alle Clients ihre Verbindung beendet haben, wird auch der PrimeGenerator gestoppt
     * um zu verhindern, dass ein Client bis "in alle Ewigkeit" auf eine Antwort wartet.
     * 
     * @throws IOException Netzwerkfehler
     */
    public void stopServer() throws IOException {

        openForNewConnections = false;

        // TODO Was ist der beste Weg sich alle offene Threads zu merken, darüber zu iterieren und
        // währenddessen die Liste zu verändern? Bei jedem .join() entfernt sich der Thread aktuell
        // selbst aus der Liste. Alternativ könnte die Liste der bekannten Threads immer wachsen und
        // wir joinen auch auf längst beendete Connections. Aber das wäre doch das gleiche wie ein
        // Speicherleck wenn der Server einfach lange genug läuft...
        for (Thread thread : openConnections) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        serverSocket.close();

    }

    @Override
    public void addEntry(String e) {
        System.out.println("ServerLog: " + e);
        serverLog.add(e);
    }

    @Override
    public List<String> getLog() {
        return serverLog;
    }
}
