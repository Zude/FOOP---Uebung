package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import helper.Logger;
import helper.MessageType;

/**
 * Ein Client, der Anfragen an einen {@link server.PrimeServer} stellen kann. Die Anfragen
 * blockieren hierbei so lange bis eine Antwort vom Server eingegangen ist.
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 */
public class PrimeClient implements Logger {

    private List<String> clientLog = new ArrayList<String>();

    private Socket clientSocket;

    // TODO die Reader/Writer aus der Aufgabenstellung verwenden oder herausfinden warum das hier
    // besser sein könnte
    private PrintWriter out;
    private BufferedReader in;

    private final String host;
    private final int port;

    private int id;

    /**
     * Konstruktor.
     * 
     * @param host Host-Adresse des Hosts mit dem verbunden werden soll
     * @param port Port Port auf dem verbunden werden soll
     * @throws IOException Verbindungsfehler
     */
    public PrimeClient(String host, int port) throws IOException {
        System.out.println("Client erstellt");

        this.host = host;
        this.port = port;

    }

    /**
     * Diese Methode muss vor allen anderen die mit dem Server kommunizieren einmalig aufgerufen
     * werden. Sie initialisiert die Kommunikation mit dem Server.
     * 
     * Es wird eine HALLO-Nachricht gesendet und es wird dann auf eine Antwort mit der zugeteilten
     * ID gewartet.
     * 
     * @throws IOException falls es ein Problem mit der Verbindung gibt
     */
    public void connect() throws IOException {

        try {
            clientSocket = new Socket(host, port);

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            System.err.println("Client err");
        }

        // TODO Log vorm Absenden oder danach? Spielt für den Test keine Rolle, aber kann es beim
        // senden fehler geben?
        out.println(MessageType.HALLO);
        addEntry("connecting");

        String ans = in.readLine();

        try {
            id = Integer.valueOf(ans);
        } catch (NumberFormatException e) {
            System.err.println("Client kann ID nicht lesen");
        }

        addEntry("connected," + id);

    }

    /**
     * Beendet die Verbindung zum Server. Kehrt sofort zurück.
     * 
     * @throws IOException falls beim Schließen ein Netzwerkfehler auftritt (unwahrscheinlich).
     */
    public void disconnect() throws IOException {

        addEntry("disconnecting");
        clientSocket.close();
    }

    /**
     * Fordert die nächste Primzahl zur übergebenen Zahl an.
     * 
     * @pre Es soll nur für positive Ganzzahlen die nächste Primzahl angefordert werden
     * @param q die Zahl, die geprüft werden soll
     * @return verwendet das die nächstgrößere Primzahl, oder die zahl selbst, falls sie prim ist
     * @throws IOException Netzwerkfehler
     */
    public long nextPrime(long q) throws IOException {
        assert q >= 0 : "Es dürfen nur positive Zahlen (>= 0) angefragt werden.";

        out.println(id + "," + MessageType.NEXTPRIME + "," + q);
        addEntry("requesting: " + MessageType.NEXTPRIME.toString().toLowerCase() + "," + q);

        String ans;
        long res = q;

        ans = in.readLine();
        res = Long.valueOf(ans);

        addEntry("response: " + MessageType.NEXTPRIME.toString().toLowerCase() + "," + res);

        return res;

    }

    /**
     * Fordert die Primfaktorzerlegung der übergebenen Zahl an.
     * 
     * @pre Es dürfen nur positive Ganzzahlen geprüft werden, die größer als eins sind (siehe
     *      Definition Primzahlen)
     * @param q die Zahl, die geprüft werden soll
     * @return die Liste der Primfaktoren von q, aufsteigend sortiert
     * @throws IOException Netzwerkfehler
     */
    public List<Long> primeFactors(long q) throws IOException {
        assert q > 1 : "Es dürfen nur positive Zahlen (> 1) angefragt werden.";

        out.println(id + "," + MessageType.PRIMEFACTORS + "," + q);
        addEntry("requesting: " + MessageType.PRIMEFACTORS.toString().toLowerCase() + "," + q);
        return null;

    }

    @Override
    public List<String> getLog() {
        return clientLog;
    }

    @Override
    public void addEntry(String e) {
        System.out.println("ClientLog: " + e);

        clientLog.add(e);
    }

}
