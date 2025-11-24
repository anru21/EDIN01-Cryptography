
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

        long startTime = System.nanoTime();
        BigInteger N = new BigInteger("170527948450228765165631");
        System.out.println(N);
        int[] factorBase = PrimeGenerator.genFactorBase(1000);

        MatrixBundle bundle = Ygenerator.generate(factorBase, N);

        if (bundle == null) {
            for (Integer factor : factorBase) {
                if (N.mod(BigInteger.valueOf(factor)).equals(BigInteger.ZERO)) {
                    System.out.println(factor);
                    System.out.println(N.divide(BigInteger.valueOf(factor)));
                    break;
                }
            }
        } else {
            GaussianElimination.gaussAlgorithm(bundle, N, factorBase);

        }

        long elapsedTime = System.nanoTime() - startTime;

        float elapsedSeconds = ((float) elapsedTime / 1000000000);

        System.out.println("The program took " + String.format("%.3f", elapsedSeconds) + " s");

    }
}