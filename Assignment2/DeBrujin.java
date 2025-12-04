import java.util.HashMap;

public class DeBrujin {

    public enum NumberMapping {
        ZERO(0, 0, 0),
        ONE(0, 1, 1),
        TWO(0, 2, 2),
        THREE(0, 3, 3),
        FOUR(0, 4, 4),
        FIVE(1, 0, 5),
        SIX(1, 1, 6),
        SEVEN(1, 2, 7),
        EIGHT(1, 3, 8),
        NINE(1, 4, 9);

        private final int first;
        private final int second;
        private final int mapped;

        NumberMapping(int first, int second, int mapped) {
            this.first = first;
            this.second = second;
            this.mapped = mapped;
        }

        public int getMapped() {
            return mapped;
        }

        public static Integer map(int first, int second) {
            for (NumberMapping nm : values()) {
                if (nm.first == first && nm.second == second) {
                    return nm.mapped;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {

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

        DeBrujin2 de_brujin2 = new DeBrujin2("0000");
        DeBrujin5 de_brujin5 = new DeBrujin5("0000");
        int[] currState = new int[4];

        for (int i = 0; i < 10000; i++) {
            int a = de_brujin2.step();
            int b = de_brujin5.step();
            Integer result = NumberMapping.map(a, b);
            currState[0] = currState[1];
            currState[1] = currState[2];
            currState[2] = currState[3];
            currState[3] = result;

            int codeNumber = currState[0] * 1000 + currState[1] * 100 + currState[2] * 10
                    + currState[3];

            visitedStates.put(codeNumber, visitedStates.get(codeNumber) + 1);

        }

        for (HashMap.Entry<Integer, Integer> pair : visitedStates.entrySet()) {
            if (pair.getValue() > 1) {
                System.out.println(pair.getKey());
                System.out.println(pair.getValue());
            }
        }

    }
}