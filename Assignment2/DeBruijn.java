import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DeBruijn {
    public static void main(String[] args) throws IOException {

        DeBruijn2 de_brujin2 = new DeBruijn2("0001");
        DeBruijn5 de_brujin5 = new DeBruijn5("0001");

        // Tests that checks if de two LFSR:s are de_bruijn sequences. Both seems to
        // pass
        // de_brujin2.isValidDeBruijnSequence();
        // de_brujin5.isValidDeBruijnSequence();

        // Method that checks if a valid DeBruijnSequence is generated
        // isValidDeBruijnSequence(de_brujin2, de_brujin5);

        // Method that creates a file with the DeBruijn sequence
        generateFile(de_brujin2, de_brujin5);

    }

    /*
     * Method that writes the debrujin sequence to a file.
     * The generated file can be used as input to the verifying tool/file
     * that we are provided. The executable file can be downloaded from the
     * canvas page
     * 
     */
    private static void generateFile(DeBruijn2 de_brujin2, DeBruijn5 de_brujin5) throws IOException {

        FileWriter myWriter = new FileWriter("deBruijnSequence.txt", true);

        int[] currState = new int[4];

        for (int j = 0; j < 10007; j++) {
            int a = de_brujin2.step();
            int b = de_brujin5.step();

            int next = numberMapping(a, b);

            if (j == 0) {
                currState[j] = next;
            } else if (j == 1) {
                currState[j] = next;
            } else if (j == 2) {
                currState[j] = next;
            } else if (j == 3) {
                currState[j] = next;
            } else {

                myWriter.append((char) ('0' + currState[0]));

                currState[0] = currState[1];
                currState[1] = currState[2];
                currState[2] = currState[3];
                currState[3] = next;
            }
        }
        myWriter.close();
    }

    /*
     * Method that tests if all the numbers between 0-9999 are visited exactly once.
     * If all numbers are visited exactly once then the algorithm generates a
     * DeBrujin sequence
     * and the method returns true.
     * If not all numbers are visited exactly once then it returns false
     * 
     */
    private static boolean isValidDeBruijnSequence(DeBruijn2 de_brujin2, DeBruijn5 de_brujin5) {
        HashMap<Integer, Integer> visitedStates = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        int number = i * 1000 + j * 100 + k * 10 + l;
                        visitedStates.put(number, 0);
                    }
                }
            }
        }

        int[] currState = new int[4];

        for (int j = 0; j < 10003; j++) {
            int a = de_brujin2.step();
            int b = de_brujin5.step();

            int next = numberMapping(a, b);

            if (j == 0) {
                currState[j] = next;
            } else if (j == 1) {
                currState[j] = next;
            } else if (j == 2) {
                currState[j] = next;
            } else if (j == 3) {
                currState[j] = next;
                int codeNumber = currState[0] * 1000 + currState[1] * 100 + currState[2] * 10
                        + currState[3];

                int state_nbr_visited = visitedStates.get(codeNumber);

                visitedStates.put(codeNumber, state_nbr_visited + 1);
            } else {

                currState[0] = currState[1];
                currState[1] = currState[2];
                currState[2] = currState[3];
                currState[3] = next;

                int codeNumber = currState[0] * 1000 + currState[1] * 100 + currState[2] * 10
                        + currState[3];

                int state_nbr_visited = visitedStates.get(codeNumber);

                visitedStates.put(codeNumber, state_nbr_visited + 1);

            }

        }

        for (

        HashMap.Entry<Integer, Integer> pair : visitedStates.entrySet()) {
            if (pair.getValue() != 1) {
                System.out.println(pair.getKey());
                return false;
            }
        }
        return true;

    }

    /*
     * Method that mapps two numbers to a new number.
     * The argument a will be a number beetwen 0-1
     * The argument b will be a number between 0-4
     * The function will return a number between 0-9
     */
    private static int numberMapping(int a, int b) {
        if (a == 0) {
            if (b == 0) {
                return 0;
            } else if (b == 1) {
                return 1;
            } else if (b == 2) {
                return 2;
            } else if (b == 3) {
                return 3;
            } else if (b == 4) {
                return 4;
            } else {
                return -1;
            }
        } else if (a == 1) {
            if (b == 0) {
                return 5;
            } else if (b == 1) {
                return 6;
            } else if (b == 2) {
                return 7;
            } else if (b == 3) {
                return 8;
            } else if (b == 4) {
                return 9;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}