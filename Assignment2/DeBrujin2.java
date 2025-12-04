public class DeBrujin2 {

    private String initState;
    private int[] currState = new int[4];

    public DeBrujin2(String initState) {
        this.initState = initState;
        for (int i = 0; i < 4; i++) {
            currState[i] = ((int) initState.charAt(i)) % 2;
        }
    }

    public String getCurrState() {
        return String.valueOf(currState[3]) + String.valueOf(currState[1]) + String.valueOf(currState[2])
                + String.valueOf(currState[0]);
    }

    public int step() {
        int out = currState[3];
        int next = (currState[3] + currState[0] + this.func()) % 2;

        currState[3] = currState[2];
        currState[2] = currState[1];
        currState[1] = currState[0];
        currState[0] = next;

        return out;
    }

    private int func() {
        if ((currState[0] + currState[1] + currState[2]) == 0) {
            return 1;
        }
        return 0;
    }

}