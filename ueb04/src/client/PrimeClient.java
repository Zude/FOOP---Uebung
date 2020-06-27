package client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import helper.Logger;

/**
 * Ein Client, der Anfragen an einen {@link server.PrimeServer} stellen kann. Die Anfragen
 * blockieren hierbei so lange bis eine Antwort vom Server eingegangen ist.
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 */
public class PrimeClient implements Logger {

    private List<String> MyLog = new ArrayList<String>();

    private final String host;
    private final int port;

    /**
     * Konstruktor.
     * 
     * @param host Host-Adresse des Hosts mit dem verbunden werden soll
     * @param port Port Port auf dem verbunden werden soll
     * @throws IOException Verbindungsfehler
     */
    public PrimeClient(String host, int port) throws IOException {
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

        Socket cs = new Socket();
        cs.connect(host, port);
    }

    /**
     * Beendet die Verbindung zum Server. Kehrt sofort zurück.
     * 
     * @throws IOException falls beim Schließen ein Netzwerkfehler auftritt (unwahrscheinlich).
     */
    public void disconnect() throws IOException {
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

    }

    @Override
    public List<String> getLog() {
        return MyLog;
    }

    @Override
    public void addEntry(String e) {
        MyLog.add(e);
    }

}
