
import java.math.BigInteger;


public class Factorizer {

    public static void main(String[] args) {
        
        BigInteger N = BigInteger.valueOf(307561);
        int[] factorBase = PrimeGenerator.genFactorBase(100);

        System.out.print("Factorbase B: ");
        for (int i = 0; i < factorBase.length; i++) {
            System.out.print(factorBase[i] + " ");
        }
        System.out.println();

        MatrixBundle bundle = Ygenerator.generate(factorBase, N);
        



        // Array Intialised and Assigned
        //int[][] arr = { { 1, 2 }, { 3, 4 }, { 5, 6 } };

        // Printing the Array
        //for (int i = 0; i < 3; i++) {
        //    for (int j = 0; j < 2; j++)
        //        System.out.print(arr[i][j] + " ");
        //    System.out.println();
        //}

    }
}