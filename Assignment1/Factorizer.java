
import java.math.BigInteger;

public class Factorizer {

    public static void main(String[] args) {

        // Our number that we want to find the factorization of
        // 170527948450228765165631

        BigInteger N = new BigInteger("392742364277");
        System.out.println(N);
        int[] factorBase = PrimeGenerator.genFactorBase(60000);

        System.out.print("Factorbase B: ");
        for (int i = 0; i < factorBase.length; i++) {
            System.out.print(factorBase[i] + " ");
        }
        System.out.println();

        MatrixBundle bundle = Ygenerator.generate(factorBase, N);

        System.out.println("hej");

        GaussianElimination.gaussAlgorithm(bundle, N, factorBase);

        // Array Intialised and Assigned
        // int[][] arr = { { 1, 2 }, { 3, 4 }, { 5, 6 } };

        // Printing the Array
        // for (int i = 0; i < 3; i++) {
        // for (int j = 0; j < 2; j++)
        // System.out.print(arr[i][j] + " ");
        // System.out.println();
        // }

    }
}