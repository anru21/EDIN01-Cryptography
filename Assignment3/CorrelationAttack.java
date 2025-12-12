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

        int[] lfConn = lf13.getConnectionPol();

        for (int j = 0; j < lfConn.length; j++) {
            System.out.println(lfConn[j]);
        }

    }

}