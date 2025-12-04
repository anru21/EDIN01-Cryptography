import java.util.HashMap;

public class DeBrujin {

    public static void main(String[] args) {

        HashMap<Integer, Integer> DeBrujin = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        int number = i * 1000 + j * 100 + k * 10 + l;
                        DeBrujin.put(number, 0);
                    }
                }
            }
        }

        deBrujinTest(DeBrujin);

    }

    public static void deBrujinTest(HashMap<Integer, Integer> deBrujin) {
        String initialState = "0001";
        StringBuilder state = new StringBuilder(initialState);
        for (int i = 0; i < 626; i++) {
            int d4Term = state.charAt(0) - '0';
            int d2Term = state.charAt(2) - '0';
            int dTerm = state.charAt(3) - '0';

            int newValue = (2 * (d4Term + d2Term + 2 * dTerm)) % 5;

            for (int j = 0; j < state.length() - 1; j++) {
                state.setCharAt(j, state.charAt(j + 1));
            }

            state.setCharAt(3, (char) (newValue + '0'));

            int intRepresentation = Integer.parseInt(state.toString());

            deBrujin.put(intRepresentation, deBrujin.get(intRepresentation) + 1);

        }

        for (HashMap.Entry<Integer, Integer> pair : deBrujin.entrySet()) {
            if (pair.getValue() == 0) {
                System.out.println(pair.getKey());
            }

        }
    }

}