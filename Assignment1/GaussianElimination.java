import java.math.BigInteger;
import java.lang.ProcessBuilder;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class GaussianElimination {

    public static void gaussAlgorithm(MatrixBundle matrixBundle, BigInteger N, int[] factorBase) {

        int[][] binaryMatrix = matrixBundle.exp_mod_mat();
        int[][] exponentMatrix = matrixBundle.exp_mat();

        BigInteger[] xVector = matrixBundle.r_vector();

        // Stringbuilder that will be used to build string that is used as the input
        // file for Gaussian elimination
        StringBuilder sb = new StringBuilder();

        /*
         * Append M & N
         * M denotes the number of (r^2 mod N) - numbers (Number of equations)
         * N denotes the factor base size
         */
        sb.append(binaryMatrix.length + " " + binaryMatrix[0].length + "\n");

        for (int i = 0; i < binaryMatrix.length; i++) {
            for (int j = 0; j < binaryMatrix[i].length; j++) {
                sb.append(binaryMatrix[i][j]);
                if (j != binaryMatrix[i].length - 1) {
                    sb.append(" ");
                }
            }
            // Apppend new line except after the last equation/row
            if (i != binaryMatrix.length - 1) {
                sb.append("\n");
            }
        }

        try (FileWriter writer = new FileWriter("input.txt")) {
            writer.write(sb.toString());

            // New process builder that will run the executable Gaussian Elimination file
            ProcessBuilder pb = new ProcessBuilder("./GaussBin.exe", "input.txt", "output.txt");
            Process p = pb.start();

            p.waitFor();

            BufferedReader inStream = new BufferedReader(new FileReader("output.txt"));

            inStream.readLine(); // Throw first line that represents how many solutions there are to the equation
                                 // system

            Boolean solutionFound = false;
            String equationSolution;

            // Represents the Left hand side of the equation x^2 = y^2 mod N that later will
            // be used

            while (!solutionFound && (equationSolution = inStream.readLine()) != null) {
                BigInteger LHS = BigInteger.ONE;

                // Split the equation solution on whitespaces. String -> String[]
                String[] bits = equationSolution.trim().split("\\s+");

                if (bits.length != exponentMatrix.length) {
                    System.out.println("ERROR: equationSolution vector length does not match exponent matrix!");
                }

                // Vector that will contain the exponents for this equationsolution
                int[] yVector = new int[exponentMatrix[0].length];

                for (int i = 0; i < bits.length; i++) {

                    // If 1 -> multiply the row with index 1 into the LHS and RHS
                    if (bits[i].equals("1")) {

                        LHS = LHS.multiply(xVector[i]).mod(N);

                        // Add each factorbase exponent for this row into the y-vector
                        for (int j = 0; j < exponentMatrix[i].length; j++) {
                            yVector[j] += exponentMatrix[i][j];
                        }
                    }
                }

                // Represent the Right hand side of the equation x^2 = y^2 mod N
                BigInteger RHS = BigInteger.ONE;

                for (int j = 0; j < yVector.length; j++) {
                    int exponent = yVector[j] / 2; // exponents must be even
                    RHS = RHS.multiply(BigInteger.valueOf(factorBase[j]).pow(exponent)).mod(N);
                }

                // Reduces the LHS & RHS modulo N
                BigInteger x = LHS.mod(N);
                BigInteger y = RHS.mod(N);

                // Compute gcd(|x - y|, N)
                BigInteger gcd = bigIntGCD(x.subtract(y).abs(), N);

                if (!gcd.equals(BigInteger.ONE) && !gcd.equals(N)) {
                    System.out.println("Factorization found!");
                    System.out.println("p = " + gcd);
                    System.out.println("q = " + N.divide(gcd));
                    solutionFound = true;
                } else {
                    System.out.println("Trivial solution, retry with another dependency.");
                }
            }

            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Help method to calculate gcd between to bigintegers
    private static BigInteger bigIntGCD(BigInteger a, BigInteger b) {
        if (b.compareTo(BigInteger.valueOf(0)) == 0) {
            return a;
        }
        return bigIntGCD(b, a.divideAndRemainder(b)[1]);
    }
}
