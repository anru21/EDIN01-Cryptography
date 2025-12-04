import java.util.HashMap;

public class DeBrujin5 {
    private String initialState;
    private int[] currState = new int[4];
    private HashMap<Integer, Integer> visitedStates;

    public DeBrujin5(String initialState) {
        this.initialState = initialState;
        this.visitedStates = new HashMap<>();

        for (int i = 0; i < 4; i++) {
            currState[i] = ((int) initialState.charAt(i) - '0');
            // System.out.println(currState[i]);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        int number = i * 1000 + j * 100 + k * 10 + l;
                        this.visitedStates.put(number, 0);
                    }
                }
            }
        }
    }

    public int step() {

        int d4Term = currState[0];

        int out = d4Term;

        int d2Term = currState[2];
        int dTerm = currState[3];

        int newValue = ((2 * (d4Term + d2Term + 2 * dTerm)) + this.func()) % 5;

        for (int j = 0; j < currState.length - 1; j++) {
            currState[j] = currState[j + 1];
        }

        currState[3] = newValue;

        return out;

    }

    private int func() {
        if (currState[0] == 1 && currState[1] == 0 && currState[2] == 0 && currState[3] == 0) {
            return 3;
        }

        if (currState[0] == 0 && currState[1] == 0 && currState[2] == 0 && currState[3] == 0) {
            return 2;

        }

        return 0;

    }

    public void DeBrujinAlgo() {

        for (int i = 0; i < 625; i++) {
            int d4Term = currState[0];

            int out = d4Term;

            int d2Term = currState[2];
            int dTerm = currState[3];

            int newValue = ((2 * (d4Term + d2Term + 2 * dTerm)) + this.func()) % 5;

            for (int j = 0; j < currState.length - 1; j++) {
                currState[j] = currState[j + 1];
            }

            currState[3] = newValue;

            int value = currState[0] * 1000 + currState[1] * 100 + currState[2] * 10 + currState[3];

            this.visitedStates.put(value, this.visitedStates.get(value) + 1);

        }

        for (HashMap.Entry<Integer, Integer> pair : this.visitedStates.entrySet()) {
            if (pair.getValue() == 1) {
                System.out.println(pair.getKey());
            }

        }

    }

    public String getInitialState() {
        return initialState;
    }

    public int[] getCurrentState() {
        return currState;
    }
}