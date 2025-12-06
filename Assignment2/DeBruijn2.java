import java.util.HashMap;

public class DeBruijn2 {

    private String initState;
    private int[] currState = new int[4];

    public DeBruijn2(String initState) {
        this.initState = initState;
        for (int i = 0; i < 4; i++) {
            currState[i] = ((int) initState.charAt(i)) % 2;
        }
    }

    public int step() {
        int out = currState[0];
        int next = (currState[0] + currState[3] + this.func()) % 2;

        currState[0] = currState[1];
        currState[1] = currState[2];
        currState[2] = currState[3];
        currState[3] = next;

        return out;
    }

    private int func() {
        if ((currState[1] + currState[2] + currState[3]) == 0) {
            return 1;
        }
        return 0;
    }

    public int[] getCurrState() {
        return currState;
    }

    public String getInitState() {
        return initState;
    }

    public boolean isValidDeBruijnSequence() {

        HashMap<Integer, Integer> visitedStates = new HashMap<>();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        int number = i * 1000 + j * 100 + k * 10 + l;
                        visitedStates.put(number, 0);
                    }
                }
            }
        }

        for (int i = 0; i < 16; i++) {

            int next = (currState[0] + currState[3] + this.func()) % 2;

            currState[0] = currState[1];
            currState[1] = currState[2];
            currState[2] = currState[3];
            currState[3] = next;

            int value = currState[3] * 1000 + currState[2] * 100 + currState[1] * 10 + currState[0];

            visitedStates.put(value, visitedStates.get(value) + 1);

        }

        for (HashMap.Entry<Integer, Integer> pair : visitedStates.entrySet()) {
            if (pair.getValue() != 1) {
                System.out.println(pair.getKey());
                return false;
            }
        }
        return true;
    }
}