import java.io.InputStream;
import java.util.Scanner;

public class CorrelationAttack {

    public static void main(String[] args) {

        InputStream in = CorrelationAttack.class.getResourceAsStream("keystream.txt");

        StringBuilder keystream = new StringBuilder();

        try (Scanner myReader = new Scanner(in)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                keystream.append(data);
            }
        }

        /***********************************************
         * Finding initial state for LFSR13
         *************************************************/
        int[] lf13Conn = { 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1 };
        LFSR lf13 = new LFSR(13, lf13Conn);
        StringBuilder initState13 = findMostLikelyInitialState(lf13, 13, keystream);
        System.out.println("The init state of LFSR13 is " + initState13);

        /***********************************************
         * Finding initial state for LFSR15
         *************************************************/
        int[] lf15Conn = { 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1 };
        LFSR lf15 = new LFSR(15, lf15Conn);
        StringBuilder initState15 = findMostLikelyInitialState(lf15, 15, keystream);
        System.out.println("The init state of LFSR15 is " + initState15);

        /***********************************************
         * Finding initial state for LFSR17
         *************************************************/
        int[] lf17Conn = { 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1 };
        LFSR lf17 = new LFSR(17, lf17Conn);
        StringBuilder initState17 = findMostLikelyInitialState(lf17, 17, keystream);
        System.out.println("The init state of LFSR17 is " + initState17);

    }

    private static StringBuilder findMostLikelyInitialState(LFSR lfsr, int lfsrLength, StringBuilder keystream) {
        float p_max = 0;

        StringBuilder bestState = new StringBuilder("");

        for (int stateIndex = 1; stateIndex < Math.pow(2, lfsrLength) + 1; stateIndex++) {
            int[] candiateState = new int[lfsrLength];
            int binaryLength = Integer.toBinaryString(stateIndex).length();
            String leadingZeros;

            if (binaryLength < lfsrLength) {
                // Appends 0:s to the beginning of the binary representation if the number is
                // less than lfsrLength bits
                leadingZeros = "0".repeat(lfsrLength - binaryLength);
            } else {
                // The binary representation is lfsrLength bits long
                leadingZeros = "";
            }

            StringBuilder binaryStateString = new StringBuilder(leadingZeros + Integer.toBinaryString(stateIndex));

            for (int j = 0; j < lfsrLength; j++) {
                candiateState[j] = binaryStateString.charAt((lfsrLength - 1) - j) - '0';
            }

            // Tests candiateState as the initialstate of the lfsr
            lfsr.setCurrPol(candiateState);

            // Calculates the Hamming distance between the keystream and the LFSR output
            int hammingDistance = hammingDistance(lfsr, keystream);

            // 1 - (Hamming distance / N)
            float p = 1 - ((float) hammingDistance / keystream.length());

            if (p > p_max) {
                p_max = p;
                bestState = binaryStateString;
            }

        }

        System.out.println("The best (maximum biased) probability p for LFSR" + lfsrLength + " is " + p_max);

        return bestState;
    }

    private static int hammingDistance(LFSR lfsr, StringBuilder keystream) {
        int hammingDistance = 0;
        for (int keyIndex = 0; keyIndex < keystream.length(); keyIndex++) {
            int keyBit = (int) keystream.charAt(keyIndex) - '0';
            int lfsrOutput = lfsr.step();
            hammingDistance += Math.abs(lfsrOutput - keyBit);
        }

        return hammingDistance;
    }
}