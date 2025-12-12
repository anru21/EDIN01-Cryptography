public class LFSR {
    private int length;
    private int[] connectionPol;
    private int[] currPol;

    public LFSR(int length, int[] connectionPol) {
        this.length = length;

        this.connectionPol = new int[connectionPol.length];

        for (int i = 0; i < connectionPol.length; i++) {
            this.connectionPol[i] = connectionPol[i];
        }

        this.currPol = new int[connectionPol.length];

    }

    public void setCurrPol(int[] initState) {
        this.currPol = initState;
    }

    public int[] getCurrPol() {
        return this.currPol;
    }

    public int getLength() {
        return length;
    }

    public int[] getConnectionPol() {
        return connectionPol;
    }

    public int step() {
        int res = currPol[length - 1];
        int temp = func();

        for (int i = length - 1; i > 0; i--) {
            currPol[i] = currPol[i - 1];
        }

        currPol[0] = temp;

        return res;
    }

    private int func() {
        int[] masked = new int[this.length];

        for (int i = 0; i < length; i++) {
            masked[i] = currPol[i] & connectionPol[i];
        }

        int c = 0;
        for (int num : masked) {
            if (num == 1) {
                c++;
            }
        }

        int res = c % 2;

        return res;
    }
}
