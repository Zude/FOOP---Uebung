import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import client.PrimeClient;
import server.PrimeServer;

public class LongTests {

    private static final int SLEEP_TIME = 50;

    private static final int PORT = 6034;

    private static final int PARTITION_SIZE = 1;

    private static final int DELAY = 10;

    private Map<Long, Long> nextPrimes = new HashMap<Long, Long>();
    private Map<Long, List<Long>> nextFactors = new HashMap<Long, List<Long>>();
    private List<Long> listForFactors = new ArrayList<Long>();

    @Before
    public void fill_NextPrimes() {
        nextPrimes.put(1L, 2L);
        nextPrimes.put(2L, 2L);
        nextPrimes.put(3L, 3L);
        nextPrimes.put(4L, 5L);
        nextPrimes.put(5L, 5L);
        nextPrimes.put(6L, 7L);
        nextPrimes.put(7L, 7L);
        nextPrimes.put(8L, 11L);
        nextPrimes.put(9L, 11L);
        nextPrimes.put(10L, 11L);
        nextPrimes.put(11L, 11L);
        nextPrimes.put(12L, 13L);
        nextPrimes.put(13L, 13L);
    }

    @Before
    public void fill_NextFactors() {

        List<Long> l1 = new ArrayList<Long>();
        l1.add(2L);
        l1.add(2L);
        l1.add(5L);
        nextFactors.put(20L, l1);
        listForFactors.add(20L);

        List<Long> l2 = new ArrayList<Long>();
        l2.add(3L);
        l2.add(5L);
        nextFactors.put(15L, l2);
        listForFactors.add(15L);

        List<Long> l3 = new ArrayList<Long>();
        l3.add(2L);
        l3.add(2L);
        l3.add(2L);
        l3.add(3L);
        l3.add(19L);
        nextFactors.put(456L, l3);
        listForFactors.add(456L);

        List<Long> l4 = new ArrayList<Long>();
        l4.add(17L);
        nextFactors.put(17L, l4);
        listForFactors.add(20L);

        List<Long> l5 = new ArrayList<Long>();
        l5.add(29L);
        l5.add(43L);
        nextFactors.put(1247L, l5);
        listForFactors.add(1247L);
    }

    @Test
    public void Many_Clients_Calling_NextPrime() throws IOException, InterruptedException {

        final PrimeServer server = new PrimeServer(PORT, PARTITION_SIZE);
        server.startServer(DELAY);

        final int clientCount = 10;

        ExecutorService exec =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            for (int i = 1; i < clientCount; i++) {
                exec.execute(new RunClientToCallNextPrime(10, new Long(i)));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Thread.sleep(SLEEP_TIME);
        server.stopServer();

        exec.shutdown();

    }

    @Test
    public void Many_Clients_Calling_PrimeFactors() throws IOException, InterruptedException {

        final PrimeServer server = new PrimeServer(PORT, PARTITION_SIZE);
        server.startServer(DELAY);

        final int clientCount = listForFactors.size();

        ExecutorService exec =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            for (int i = 0; i < clientCount; i++) {
                exec.execute(new RunClientToCallPrimeFactors(10, i));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Thread.sleep(SLEEP_TIME);
        server.stopServer();

        exec.shutdown();

    }

    private class RunClientToCallNextPrime implements Runnable {

        private PrimeClient myClient;
        private int wait;
        private Long next;

        public RunClientToCallNextPrime(int wait, Long next) {
            this.next = next;
            this.wait = wait;
            try {
                myClient = new PrimeClient("localhost", PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                myClient.connect();

                Thread.sleep(wait);

                Assert.assertEquals((long) nextPrimes.get(next), myClient.nextPrime(next));

                Thread.sleep(wait);

                myClient.disconnect();

            } catch (IOException | InterruptedException e) {

                e.printStackTrace();
            }

        }

    }

    private class RunClientToCallPrimeFactors implements Runnable {

        private PrimeClient myClient;
        private int wait;
        private int next;

        public RunClientToCallPrimeFactors(int wait, int next) {
            this.next = next;
            this.wait = wait;
            try {
                myClient = new PrimeClient("localhost", PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                myClient.connect();

                Thread.sleep(wait);

                Assert.assertEquals(nextFactors.get(listForFactors.get(next)),
                        myClient.primeFactors(listForFactors.get(next)));

                Thread.sleep(wait);

                myClient.disconnect();

            } catch (IOException | InterruptedException e) {

                e.printStackTrace();
            }

        }

    }

}
