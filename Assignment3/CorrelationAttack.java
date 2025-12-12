import java.io.File;
import java.io.FileNotFoundException;
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

        // D0 D1 D2 D3 D4 D5 D6 D7 D8 D9 D10 D11 D12 D13
        int[] lf13Conn = { 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1 };
        LFSR lf13 = new LFSR(13, lf13Conn);
        StringBuilder initState13 = initialStateGuess(lf13, 13, keystream);
        System.out.println("The init state of LFSR13 is " + initState13);
        int[] lf15Conn = { 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1 };
        LFSR lf15 = new LFSR(15, lf15Conn);
        StringBuilder initState15 = initialStateGuess(lf15, 15, keystream);
        System.out.println("The init state of LFSR15 is " + initState15);
        int[] lf17Conn = { 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1 };
        LFSR lf17 = new LFSR(17, lf17Conn);
        StringBuilder initState17 = initialStateGuess(lf17, 17, keystream);
        System.out.println("The init state of LFSR17 is " + initState17);

    }

    private static StringBuilder initialStateGuess(LFSR lfsr, int lfsrLength, StringBuilder keystream) {
        float p_max = 0;

        StringBuilder initStateGuess = new StringBuilder("");

        for (int i = 1; i < Math.pow(2, lfsrLength) + 1; i++) {
            int[] initState = new int[lfsrLength];
            int lengthOfBinaryRep = Integer.toBinaryString(i).length();
            String initialZeros;

            if (lengthOfBinaryRep < lfsrLength) {
                // Appends 0:s to the beginning of the binary representation if the number is
                // less than lfsrLength bits
                initialZeros = "0".repeat(lfsrLength - lengthOfBinaryRep);
            } else {
                // The binary representation is lfsrLength bits long
                initialZeros = "";
            }

            StringBuilder binaryRepresent = new StringBuilder(initialZeros + Integer.toBinaryString(i));

            for (int j = 0; j < lfsrLength; j++) {
                initState[j] = binaryRepresent.charAt((lfsrLength - 1) - j) - '0';
            }

            // Tests initStats as the initialstate of the lfsr
            lfsr.setCurrPol(initState);

            // Calculates the Hamming distance between the keystream and the LFSR output
            int hammingDistance = hammingDistance(lfsr, keystream);

            // 1 - (Hamming distance / N)
            float p = 1 - ((float) hammingDistance / keystream.length());

            if (p > p_max) {
                p_max = p;
                initStateGuess = binaryRepresent;
            }

        }

        System.out.println("The best (maximum biased) probability p for LFSR" + lfsrLength + " is " + p_max);

        return initStateGuess;
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