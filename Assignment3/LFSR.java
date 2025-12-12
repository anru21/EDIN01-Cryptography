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

    public int getLength() {
        return length;
    }

    public int[] getConnectionPol() {
        return connectionPol;
    }
}
