import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimeGenerator {

    public static int[] genFactorBase(int factorBaseSize) {
        // How many
        int generatedPrimes = 0;
        int[] factorBase = new int[factorBaseSize];

        // The number that we are testing if it is a prime
        int currentNumber = 2;

        while (generatedPrimes < factorBaseSize) {
            if (isPrime(currentNumber)) {
                factorBase[generatedPrimes] = currentNumber;
                generatedPrimes++;
            }
            currentNumber++;
        }
        return factorBase;
    }

    public static List<Integer> factorBase(int smoothnessBound) {
        List<Integer> factorBase = new ArrayList<>();

        // The number that we are testing if it is a prime
        int currentNumber = 2;

        while (currentNumber < smoothnessBound) {
            if (isPrime(currentNumber)) {
                factorBase.add(currentNumber);

            }
            currentNumber++;
        }
        return factorBase;
    }

    private static boolean isPrime(int x) {
        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
