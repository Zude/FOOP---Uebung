import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import client.PrimeClient;
import server.PrimeServer;

public class ClientServerWithDummy {

    private static final int SLEEP_TIME = 20;

    private static final int PORT = 6024;

    private static final int PARTITION_SIZE = 1000;

    private static final int DELAY = 10;

    private static final DummyPrimeManager dpm = new DummyPrimeManager(PARTITION_SIZE);

    @Test
    public void NextPrime_Send_And_Receive_Msg_Single_Client()
            throws IOException, InterruptedException {

        final PrimeServer server = new PrimeServer(PORT, dpm);
        server.startServer(DELAY);

        final PrimeClient client = new PrimeClient("localhost", PORT);
        client.connect();

        // warten, sodass der Server die Nachricht sicher schon erhalten hat
        Thread.sleep(SLEEP_TIME);

        // ----

        long res = client.nextPrime(1);

        Assert.assertEquals(1, res);

        // ----

        client.disconnect();
        server.stopServer();

    }

    @Test
    public void NextPrime_Send_And_Receive_Msg_Two_Clients()
            throws IOException, InterruptedException {

        Thread.sleep(SLEEP_TIME);
        // 2 Clients parallel, je Client eine Anfrage

        final PrimeServer server = new PrimeServer(PORT, dpm);
        server.startServer(DELAY);

        final PrimeClient client1 = new PrimeClient("localhost", PORT);
        client1.connect();

        final PrimeClient client2 = new PrimeClient("localhost", PORT);
        client2.connect();

        final Thread c1 = new Thread(new Runnable() {

            public void run() {
                try {
                    client1.nextPrime(1); // Was in einem Thread passiert, bleibt in einem Thread
                                          // (Assertions und Exceptions kommen da nicht raus)
                } catch (IOException e) {
                    System.err.println("Unerwartete Exception in c1: " + e);
                }
            }
        });
        c1.start();

        final Thread c2 = new Thread(new Runnable() {

            public void run() {
                try {
                    client2.nextPrime(1); // Was in einem Thread passiert, bleibt in einem Thread
                                          // (Assertions und Exceptions kommen da nicht raus)
                } catch (IOException e) {
                    System.err.println("Unerwartete Exception in c2: " + e);
                }
            }
        });
        c2.start();

        // Warten bis beide zurckgekehrt sind
        c1.join();
        c2.join();

        // Abmelden (sicherstellen, dass wirklich nacheinander)
        Thread.sleep(SLEEP_TIME);
        client1.disconnect();
        Thread.sleep(SLEEP_TIME);
        client2.disconnect();

        // Server stoppen
        server.stopServer();

        // Logs besorgen
        List<String> slog = server.getLog();
        List<String> clog1 = client1.getLog();
        List<String> clog2 = client2.getLog();

        // Erwartete Logeinträge generieren
        Assert.assertEquals(mkList("client connected,1", "client connected,2"), slog.subList(0, 2));
        Assert.assertTrue(slog.contains("requested: 1,nextprime,1,2"));
        Assert.assertTrue(slog.contains("requested: 2,nextprime,1,2"));
        Assert.assertEquals(mkList("client disconnected,1", "client disconnected,2"),
                slog.subList(slog.size() - 2, slog.size()));

        Assert.assertEquals(mkList("connecting", "connected,1", "requesting: nextprime,1",
                "response: nextprime,2", "disconnecting"), clog1);

        Assert.assertEquals(mkList("connecting", "connected,2", "requesting: nextprime,1",
                "response: nextprime,2", "disconnecting"), clog2);
    }

    /**
     * Generieren einer Liste aus beliebig vielen Strings.
     *
     * @param x Array von String
     * @return eine Liste mit allen Elementen aus x in der gleichen Reihenfolge wie angegeben
     */
    public static List<String> mkList(String... x) {
        final List<String> res = new LinkedList<>();
        for (int i = 0; i < x.length; i++) {
            res.add(x[i]);
        }
        return res;
    }

}
