import java.math.BigInteger;

public class GaussianElimination {

    public static void gaussAlgorithm(MatrixBundle matrixBundle, BigInteger N, int[] factorBase) {

        int[][] binaryMatrix = matrixBundle.exp_mod_mat();
        int[][] exponentMatrix = matrixBundle.exp_mat();

        BigInteger[] xVector = matrixBundle.r_vector();

        for (int i = 0; i < binaryMatrix[0].length; i++) { // Iterate through all factorbases (columns)
            for (int j = i; j < binaryMatrix.length; j++) { // Iterate through each equation (rows)
                if (binaryMatrix[j][i] == 1) { // FIrst row with odd exponent for this factorbase

                    // Iterate from row j onward because all previous rows have even exponents for
                    // this factor base
                    for (int k = j; k < binaryMatrix.length; k++) {
                        if (k != j) {
                            if (binaryMatrix[k][i] == 1) { // Row have odd exponent for this factorbase
                                accumulateRow(j, k, xVector, binaryMatrix, exponentMatrix, N, factorBase); // Add row j
                                                                                                           // to row k
                            }
                        }
                    }

                    moveUpPivotEquation(i, j, xVector, binaryMatrix, exponentMatrix); // Move up

                    // System.out.println(binaryMatrix[k][i] + " " + exponentMatrix[k][i]);
                    // pivot equation from
                    // index j -> i

                    // break; // All rows should have 0 for this column index --> skip remaining
                    // rows
                }
            }
        }

        // Last 5 rows should now have all even exponents
        for (int i = exponentMatrix.length - 5; i < exponentMatrix.length; i++) {
            BigInteger modX = xVector[i].divideAndRemainder(N)[1];
            BigInteger y = BigInteger.valueOf(1);
            Boolean contains1 = false;
            for (int j = 0; j < exponentMatrix[i].length; j++) {
                System.out.print(binaryMatrix[i][j] + " ");
                if (binaryMatrix[i][j] == 1) {
                    contains1 = true;
                }
                // System.out.println(exponentMatrix[i][j]);
                y = y.multiply(BigInteger.valueOf(factorBase[j]).pow(exponentMatrix[i][j] /
                        2));
            }
            // System.out.println(contains1);
            BigInteger modY = y.divideAndRemainder(N)[1];

            BigInteger gcd = bigIntGCD(modX.subtract(modY).abs(), N);
            if (gcd.compareTo(BigInteger.ONE) != 0) {
                System.out.println(N.divideAndRemainder(gcd)[0]);
                System.out.println(gcd);
                System.out.println(contains1);
            }
        }
    }

    /*
     * Adds the equation for row j to equation for row k
     */
    private static void accumulateRow(int j, int k, BigInteger[] xVector, int[][] binaryMatrix,
            int[][] exponentMatrix, BigInteger N, int[] factorBase) {

        xVector[k] = xVector[k].multiply(xVector[j]).mod(N);
        // System.out.println("Bit length of xVector[" + k + "]: " +
        // xVector[k].bitLength());
        for (int l = 0; l < binaryMatrix[0].length; l++) {
            binaryMatrix[k][l] = (binaryMatrix[k][l] + binaryMatrix[j][l]) % 2;
            exponentMatrix[k][l] = (exponentMatrix[k][l] + exponentMatrix[j][l]);
            if (exponentMatrix[j][l] != 0) {
                // System.out.println(factorBase[l] + " " + exponentMatrix[k][l]);
            }
        }
    }

    /*
     * Swaps place between equation on row i and row j
     */
    private static void moveUpPivotEquation(int i, int j, BigInteger[] xVector, int[][] binaryMatrix,
            int[][] exponentMatrix) {

        int[] binaryTemp = binaryMatrix[j];
        int[] exponentTemp = exponentMatrix[j];
        BigInteger xTemp = xVector[j];

        binaryMatrix[j] = binaryMatrix[i];
        exponentMatrix[j] = exponentMatrix[i];
        xVector[j] = xVector[i];

        binaryMatrix[i] = binaryTemp;
        exponentMatrix[i] = exponentTemp;
        xVector[i] = xTemp;
    }

    // Help method to calculate gcd between to bigintegers
    private static BigInteger bigIntGCD(BigInteger a, BigInteger b) {
        if (b.compareTo(BigInteger.valueOf(0)) == 0) {
            return a;
        }
        return bigIntGCD(b, a.divideAndRemainder(b)[1]);
    }
}
