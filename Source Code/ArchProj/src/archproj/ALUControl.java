package archproj;

public class ALUControl {
    private int funct;
    private int aluControl;

    public ALUControl(int aluOp, int f) {
        funct = f;
        switch(aluOp){
            //slti
            case 3:
                aluControl = 7;
                
                break;
            //R type
            case 2:
                checkFunct();
                break;
            //add   
            case 0:
                aluControl = 2;
                break;
            //sub
            case 1:
                aluControl = 6;
                break;
        }
    }
    
    private void checkFunct(){
        switch(funct){
            //add
            case 32:
                aluControl = 2;
                break;
            //nor    
            case 37:
                aluControl = 12;
                System.out.println("nor cc: " + aluControl);
                break;
            //slt    
            case 42:
                aluControl = 7;
                break;
            //sll
            case 0:
                aluControl = 3;
                break;
        }
    }

    public int getAluControl() {
        return aluControl;
    }
}
