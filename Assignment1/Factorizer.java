import java.util.List;

public class Factorizer {

    public static void main(String[] args) {
        int[] factorBase = PrimeGenerator.genFactorBase(1000);

        // Array Intialised and Assigned
        int[][] arr = { { 1, 2 }, { 3, 4 }, { 5, 6 } };

        // Printing the Array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++)
                System.out.print(arr[i][j] + " ");
            System.out.println();
        }

    }
}