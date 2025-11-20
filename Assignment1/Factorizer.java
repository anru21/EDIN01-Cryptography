
import java.math.BigInteger;

public class Factorizer {

    public static void main(String[] args) {

        // Our number that we want to find the factorization of
        // 170527948450228765165631

        BigInteger N = new BigInteger("392742364277");
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