Public Class DeBrujin2 {

    private String initState;
    private int[4] currState;

    public void DuBrujin2(String initState){
        this.initState = initState;
        for(int i = 0; i < 4; i++){
            currState[i] = ((int) initState[i]) % 2;
        }
    } 

    public String getCurrState(){
        String currStr = ""; 
        for(int i = 0; i < 4, i++){
            currStr = currStr.concat(currState[i].toString());
        }
        return currStr;
    }

    public int step(){
        int out = currState[3];
        int next = currState[3] + currState[0] + this.func();


        for(int i = 0; i < 4; i++){
            if(i == 0) {
                currState[i] = next;
            } else {
                currState[i] = currState[i];
            }  
        }

        return out;
    }

    private int func(){
        if((currState[0] + currState[1] + currState[2]) == 0){
            return 1;
        }
        return 0;
    }

}