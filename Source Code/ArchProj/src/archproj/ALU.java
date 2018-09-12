package archproj;

public class ALU {
    private int res;

    public ALU(int a, int b, int aluControl, int shamt) {
        switch(aluControl){
            //add
            case 2:
                res = a+b;
                break;
            //sub
            case 6:
                res = a-b;
                break;
            //sll
            case 3:
                for (int i = 0; i < shamt; i++) b = b << 1;
                res = b;
                break;
            //nor
            case 12:
                res = ~(a | b);
                System.out.println("nor: "+res);
                break;
            //slt
            case 7:
                if(a<b) res = 1;
                else res = 0;
                break;
        }
    }

    public boolean getZeroFlag() {
        return res==0;
    }
    public int getRes() {
        return res;
    }   
}