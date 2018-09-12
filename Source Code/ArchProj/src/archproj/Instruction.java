package archproj;

public class Instruction {
    private final int opCode;
    private final int rs;
    private final int rt;
    private final int rd;
    private final int shamt;
    private final int funct;
    private final int jumpAdd;
    private final String constant;
    

    public Instruction(String inst) {
        opCode = Integer.parseInt(inst.substring(0, 6),2);
        rs = Integer.parseInt(inst.substring(6, 11),2);
        rt = Integer.parseInt(inst.substring(11, 16),2);
        rd = Integer.parseInt(inst.substring(16, 21),2);
        shamt = Integer.parseInt(inst.substring(21, 26),2);
        funct = Integer.parseInt(inst.substring(26, 32),2);
        jumpAdd = Integer.parseInt(inst.substring(6, 32),2);
        constant = inst.substring(16, 32);
    }
    
    public int getConstant() {
        int signedConstant = Integer.parseInt(constant,2);
        if(constant.charAt(0) == '0')
            return signedConstant;
        else {
            signedConstant |= 0xffff0000;
            return signedConstant;
        }
    }
    public int getFunct() {
        return funct;
    }
    public int getJumpAdd() {
        return jumpAdd;
    }
    public int getOpCode() {
        return opCode;
    }
    public int getRd() {
        return rd;
    }
    public int getRs() {
        return rs;
    }
    public int getRt() {
        return rt;
    }
    public int getShamt() {
        return shamt;
    }
}
