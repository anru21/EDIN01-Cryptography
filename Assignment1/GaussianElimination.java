import java.math.BigInteger;

public class GaussianElimination {

    public static void gaussAlgorithm(MatrixBundle matrixBundle, BigInteger N, int[] factorBase) {

        int[][] binaryMatrix = matrixBundle.exp_mod_mat();
        int[][] exponentMatrix = matrixBundle.exp_mat();
        BigInteger[] xVector = matrixBundle.r_vector();

        for (int i = 0; i < binaryMatrix[0].length; i++) { // Iterate through all factorbases (columns)
            for (int j = 0; j < binaryMatrix.length; j++) { // Iterate through each equation (rows)
                if (binaryMatrix[j][i] == 1) {
                    // Iterate from row j onward because all previous rows have even exponents for
                    // this factor base
                    for (int k = j; k < binaryMatrix.length; k++) {
                        if (k != j) {
                            if (binaryMatrix[k][i] == 1) { // Row have odd exponent for this factorbase
                                // xVector[k] = xVector[k].multiply(xVector[j]);
                                // for (int l = 0; l < binaryMatrix[0].length; l++) {
                                // binaryMatrix[k][l] = (binaryMatrix[k][l] + binaryMatrix[j][l]) % 2;
                                // exponentMatrix[k][l] = (exponentMatrix[k][l] + exponentMatrix[j][l]);
                                // }
                                accumulateRow(j, k, xVector, binaryMatrix, exponentMatrix); // Add row j to row k
                            }
                        }
                    }

                    // Move up the vector with a 1 on the column we are visiting
                    // int[] binaryTemp = binaryMatrix[j];
                    // int[] exponentTemp = exponentMatrix[j];
                    // BigInteger xTemp = xVector[j];

                    // binaryMatrix[j] = binaryMatrix[i];
                    // exponentMatrix[j] = exponentMatrix[i];
                    // xVector[j] = xVector[i];

                    // binaryMatrix[i] = binaryTemp;
                    // exponentMatrix[i] = exponentTemp;
                    // xVector[i] = xTemp;
                    moveUpPivotEquation(i, j, xVector, binaryMatrix, exponentMatrix); // Move up pivot equation from
                                                                                      // index j -> i

                    break; // All rows should have 0 for this column index --> skip remaining rows
                }
            }
        }

        // Last 5 rows should now have all even exponents
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
    }

    private static void accumulateRow(int j, int k, BigInteger[] xVector, int[][] binaryMatrix,
            int[][] exponentMatrix) {

        xVector[k] = xVector[k].multiply(xVector[j]);
        for (int l = 0; l < binaryMatrix[0].length; l++) {
            binaryMatrix[k][l] = (binaryMatrix[k][l] + binaryMatrix[j][l]) % 2;
            exponentMatrix[k][l] = (exponentMatrix[k][l] + exponentMatrix[j][l]);
        }
    }

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
        if (b.equals(0)) {
            return a;
        }
        return bigIntGCD(b, a.divideAndRemainder(b)[1]);
    }
}
