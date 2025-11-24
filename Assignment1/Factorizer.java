
import java.math.BigInteger;

public class Factorizer {

    public static void main(String[] args) {

        // 323=17 * 19,
        // 307561=457 * 673,
        // 31741649=4621 * 6869,
        // 3205837387=46819 * 68473,
        // 392742364277=534571 * 734687.

        // Our number that we want to find the factorization of
        // 170527948450228765165631

        BigInteger N = new BigInteger("170527948450228765165631");
        System.out.println(N);
        int[] factorBase = PrimeGenerator.genFactorBase(1000);

        System.out.print("Factorbase B: ");
        for (int i = 0; i < factorBase.length; i++) {
            System.out.print(factorBase[i] + " ");
        }
        System.out.println();

        MatrixBundle bundle = Ygenerator.generate(factorBase, N);

        GaussianElimination.gaussAlgorithm(bundle, N, factorBase);

    }
}