import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ygenerator {
        int NBR_THREADS = 4;
        ExecutorService pool = Executors.newFixedThreadPool(NBR_THREADS);

    public void generate(BigInteger[] factor_base, BigInteger N){
        int x = 0;
        int y = 0;
        while (true) {
            final int k = x;
            final int j = y;
            Callable<BigInteger> calc = () -> {
                BigInteger r = N.multiply(BigInteger.valueOf(k).sqrt()).add(BigInteger.valueOf(j)); // r = floor(sqrt(k*N)) + j
                BigInteger r_square = r.pow(2);                                           // r^2
                return BigInteger.ONE;
            }
        }
        
    }
}
