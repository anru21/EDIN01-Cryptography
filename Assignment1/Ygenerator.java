import java.math.BigInteger;
import java.util.Arrays;

public class Ygenerator {
        //static int NBR_THREADS = 4;    // can be changed
        static int MARGIN = 5;         // can be changed
        //static ExecutorService pool = Executors.newFixedThreadPool(NBR_THREADS);

    public static MatrixBundle generate(int[] factor_base, BigInteger N){
        // some variables/datastructures needed for calculations
        int x = 1;
        int y = 1;
        int count = 0;
        int[][] exponent_matrix = new int[factor_base.length + MARGIN][factor_base.length];
        int[][] exponent_mod_matrix = new int[factor_base.length + MARGIN][factor_base.length];
        BigInteger[] r_vector = new BigInteger[factor_base.length];

        // continue submitting potential r:s until result matrices are filled
        while (count < factor_base.length + MARGIN) {
            System.out.println("Iter " + count + ", x: " + x + ", y:" + y);
            
            BigInteger r = N.multiply(BigInteger.valueOf(x).sqrt()).add(BigInteger.valueOf(y));     // r = floor(sqrt(k*N)) + j
            System.out.println("r:" + r.toString());
            BigInteger r_square = r.modPow(BigInteger.TWO, N);                                           // r^2 mod N
            System.out.println("r^2:" + r_square.toString());

            BigInteger q = r_square;                                      // initialize
            BigInteger[] q_and_rest = {BigInteger.ZERO, BigInteger.ZERO};
            int[] temp_exponent_vector = new int[factor_base.length];       // for storage before putting in result matrix

            for(int i = 0; i < factor_base.length; i++){
                //System.out.println("index: " + i);
                //System.out.println(q + "/" + factor_base[i]);       
                q_and_rest = q.divideAndRemainder(BigInteger.valueOf(factor_base[i]));
                //System.out.println("= " + q_and_rest[0] + " " + q_and_rest[1]);

                if(q_and_rest[1].equals(BigInteger.ZERO)){
                    q = q_and_rest[0];
                    temp_exponent_vector[i]++;
                    i--;
                    // if both q is one and r is zero we have found that r^2 can be expressed as a product of prime powers in the factor_base
                    if (q_and_rest[0].equals(BigInteger.ONE)) {
                            // If exponent vector does not yet exist in result matrix add to result matrix and add to count
                            // but we instead use exponent-mod vector to avoid "duplicates"
                            int[] temp_exponent_mod_vector = new int[factor_base.length];

                            System.out.print("Exponent vector: ");
                            for (int j = 0; j < factor_base.length; j++) {
                                temp_exponent_mod_vector[j] = temp_exponent_vector[j] % 2;
                                System.out.print(temp_exponent_vector[j] + " ");
                            }
                            System.out.println();

                            System.out.print("Exponent mod vector: ");
                            for (int j = 0; j < factor_base.length; j++) {
                                System.out.print(temp_exponent_mod_vector[j] + " ");
                            }
                            System.out.println();

                            // check if temp_exponent_mod_vector already exists among the filled rows
                            boolean exists = false;
                            for (int row = 0; row < count; row++) {
                                if (Arrays.equals(exponent_mod_matrix[row], temp_exponent_mod_vector)) {
                                    exists = true;
                                    break;
                                }
                            }

                            if (!exists) {
                                exponent_mod_matrix[count] = temp_exponent_mod_vector.clone();
                                exponent_matrix[count] = temp_exponent_vector.clone();
                                r_vector[count] = r;
                                count++;
                            }

                            break;
                    }
                }
            }
            
            

            // algorithm that changes x and y
            y++;
            if(y == 23){
                y = 1;
                x++;
            }
        }
        

        //ret mod exponent matrix, exponent matrix and r:s
        return new MatrixBundle(exponent_matrix, exponent_mod_matrix, r_vector);
    }
}
