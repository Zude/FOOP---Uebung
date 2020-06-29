import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import server.PrimeManager;

public class DummyPrimeManager extends PrimeManager {

    public DummyPrimeManager(int partitionSize) {
        super(partitionSize);
    }

    @Override
    public long nextPrime(long q) {
        assert (q >= 0) : "nextPrime muss mit einer positiven Ganzzahl aufgerufen werden.";

        if (q == 1)
            return 2;
        if (q == 4)
            return 5;

        return q;
    }

    @Override
    public List<Long> primeFactors(long q) {
        assert (q >= 2) : "PrimeFactors muss mit einer positiven Ganzzahl >=2 aufgerufen werden.";

        List<Long> res = new ArrayList<Long>();

        if (q == 4) {
            res.add(new Long(2));
            res.add(new Long(2));
        }

        return res;

    }

    @Override
    public void startWorker(long delay) {

    }

    @Override
    public Collection<Long> knownPrimes() {
        return null;
    }

}
