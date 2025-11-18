import java.math.BigInteger;

public class GaussianElimination {

    public static void gaussAlgorithm(MatrixBundle matrixBundle, BigInteger N, int[] factorBase) {
        // Obj have one vector with x values, one matrix with the exponents stored, one
        // matrix with binary numbers

        int[][] binaryMatrix = matrixBundle.exp_mod_mat();
        int[][] exponentMatrix = matrixBundle.exp_mat();
        BigInteger[] xVector = matrixBundle.r_vector();

        for (int i = 0; i < binaryMatrix[0].length; i++) {
            for (int j = 0; j < binaryMatrix.length; j++) {
                if (binaryMatrix[j][i] == 1) {
                    for (int k = j; k < binaryMatrix.length; k++) {
                        if (k != j) {
                            if (binaryMatrix[k][i] == 1) {
                                xVector[k] = xVector[k].multiply(xVector[j]);
                                for (int l = 0; l < binaryMatrix[0].length; l++) {
                                    binaryMatrix[k][l] = (binaryMatrix[k][l] + binaryMatrix[j][l]) % 2;
                                    exponentMatrix[k][l] = (exponentMatrix[k][l] + exponentMatrix[j][l]);
                                }

                            }
                        }
                    }

                    // Move up the vector with a 1 on the column we are visiting
                    int[] binaryTemp = binaryMatrix[j];
                    int[] exponentTemp = exponentMatrix[j];
                    BigInteger xTemp = xVector[j];

                    binaryMatrix[j] = binaryMatrix[i];
                    exponentMatrix[j] = exponentMatrix[i];
                    xVector[j] = xVector[i];

                    binaryMatrix[i] = binaryTemp;
                    exponentMatrix[i] = exponentTemp;
                    xVector[i] = xTemp;

                    break; // All rows should have 0 for this column index --> skip remaining rows
                }
            }
        }

        for (int i = exponentMatrix.length - 5; i < exponentMatrix.length; i++) {
            BigInteger modX = xVector[i].divideAndRemainder(N)[1];
            BigInteger y = BigInteger.valueOf(1);
            for (int j = 0; j < exponentMatrix[i].length; j++) {
                y = y.multiply(BigInteger.valueOf(factorBase[j]).pow(exponentMatrix[i][j]));
            }
            BigInteger modY = y.divideAndRemainder(N)[1];

            BigInteger gcd = bigIntGCD(modX.subtract(modY).abs(), N);
            if (gcd.compareTo(BigInteger.ONE) != 0) {
                System.out.println(N.divideAndRemainder(gcd)[0]);
            }
        }
        // Last 5 rows should now have all even exponents
    }

    private static BigInteger bigIntGCD(BigInteger a, BigInteger b) {
        if (b.equals(0)) {
            return a;
        }
        return bigIntGCD(b, a.divideAndRemainder(b)[1]);
    }
}
