import java.io.InputStream;
import java.util.Scanner;

public class Evaluate {
    public static void main(String[] args) {

        InputStream in = CorrelationAttack.class.getResourceAsStream("keystream.txt");

        StringBuilder keystream = new StringBuilder();

        try (Scanner myReader = new Scanner(in)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                keystream.append(data);
            }
        }

        int[] lf13Conn = { 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1 };
        int[] lf13init = { 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0 };
        LFSR lf13 = new LFSR(13, lf13Conn);
        lf13.setCurrPol(lf13init);

        int[] lf15Conn = { 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1 };
        int[] lf15init = { 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1 };
        LFSR lf15 = new LFSR(15, lf15Conn);
        lf15.setCurrPol(lf15init);

        int[] lf17Conn = { 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1 };
        int[] lf17init = { 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0 };
        LFSR lf17 = new LFSR(17, lf17Conn);
        lf17.setCurrPol(lf17init);

        StringBuilder mySeq = new StringBuilder();
        for (int i = 0; i < keystream.length(); i++){
            int a = lf13.step();
            int b = lf15.step();
            int c = lf17.step();
            //System.out.println("13: " + a + " 15: " + b + " 17: " + c);
            int sum = a + b + c;
            if(sum < 2){
                mySeq.append(0);
            }else{
                mySeq.append(1);
            }
        }
        
        System.out.println(mySeq);
        System.out.println(keystream);

        if(mySeq.compareTo(keystream) == 0){
            System.out.println("OK!");
        }

    }
}
