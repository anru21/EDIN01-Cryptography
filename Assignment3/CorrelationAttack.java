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
        int[] lf13Conn = { 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1 };
        LFSR lf13 = new LFSR(13, lf13Conn);

        float p_max = 0;

        int initialStateGuess = 0;

        for (int i = 1; i < Math.pow(2, 13); i++) {
            int[] initState = new int[13];
            int lengthOfBinaryRep = Integer.toBinaryString(i).length();
            String initialZeros;

            if (lengthOfBinaryRep < 13) {
                // Appends 0:s to the beginning of the binary representation if the number is
                // less than 13 bits
                initialZeros = "0".repeat(13 - lengthOfBinaryRep);
            } else {
                // The binary representation is 13 bits
                initialZeros = "";
            }

            StringBuilder binaryRepresent = new StringBuilder(initialZeros + Integer.toBinaryString(i));

            for (int j = 0; j < 13; j++) {
                initState[j] = binaryRepresent.charAt(12 - j) - '0';
            }

            // Tests initStats as the initialstate of the lfsr
            lf13.setCurrPol(initState);

            // Calculates the Hamming distance between the keystream and the LFSR output
            int hammingDistance = 0;
            for (int keyIndex = 0; keyIndex < keystream.length(); keyIndex++) {
                int keyBit = (int) keystream.charAt(keyIndex) - '0';
                int lfsrOutput = lf13.step();
                hammingDistance += Math.abs(lfsrOutput - keyBit);
            }

            // 1 - (Hamming distance / N)
            float p = 1 - ((float) hammingDistance / keystream.length());

            if (p > p_max) {
                p_max = p;
                initialStateGuess = i;

            }

        }
        System.out.println(initialStateGuess);

    }

}