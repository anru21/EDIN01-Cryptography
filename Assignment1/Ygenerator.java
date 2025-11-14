import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ygenerator {
        static int NBR_THREADS = 4;    // can be changed
        static int MARGIN = 5;         // can be changed
        static ExecutorService pool = Executors.newFixedThreadPool(NBR_THREADS);

    public static MatrixBundle generate(int[] factor_base, BigInteger N) throws InterruptedException {
        // some variables/datastructures needed for calculations
        int x = 0;
        int y = 0;
        int count = 0;
        int[][] exponent_matrix = new int[factor_base.length + MARGIN][factor_base.length];
        int[][] exponent_mod_matrix = new int[factor_base.length + MARGIN][factor_base.length];
        BigInteger[] r_vector = new BigInteger[factor_base.length];
        //Semaphore doneCheck = new Semaphore(NBR_THREADS);

        // continue submitting potential r:s until result matrices are filled
        while (count < factor_base.length + MARGIN) {
            final int k = x;
            final int j = y;
            //pool.submit(() -> {
            //    doneCheck.acquire();
            BigInteger r = N.multiply(BigInteger.valueOf(k).sqrt()).add(BigInteger.valueOf(j)); // r = floor(sqrt(k*N)) + j
            BigInteger r_square = r.pow(2);     // r^2

            BigInteger[] q_and_rest = {r_square, BigInteger.ZERO}; // initialize
            int[] temp_exponent_vector = new int[factor_base.length];

            for(int i = 0; i < factor_base.length; i++){        // might be cooked
                q_and_rest = q_and_rest[0].divideAndRemainder(BigInteger.valueOf(factor_base[i]));
                if(q_and_rest[1].equals(BigInteger.valueOf(0))){
                    temp_exponent_vector[i]++;
                    i--;
                }
            }

            //    doneCheck.release();
            //    return BigInteger.ONE;
            //});
            // change x/y and index
        }
        

        //doneCheck.acquire(NBR_THREADS);
        //ret mod exponent matrix, exponent matrix and r:s
        return new MatrixBundle(exponent_matrix, exponent_mod_matrix, r_vector);
    }
}
