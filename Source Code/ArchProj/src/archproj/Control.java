package archproj;
/*
--add: R Type--
aluOp = 2
Source1 = Rs
Source2 = Rt
Dst = Rd
regWrite = true

--addi: I Type--
Source1 = Rs
Source2 = Constant
Dst = Rt
regWrite = true

--lw: I Type--
Source1 = Rs
Source2 = Constant
Dst = Rt
regWrite = true
memRead = true
memToReg = true

--lb: I Type--
Source1 = Rs
Source2 = Constant
Dst = Rt
regWrite = true
memRead = true
loadByte = true
memToReg = true

--sw: I Type--
Source1 = Rs
Source2 = Constant
Dst = Rt
memWrite = true

--sb: I Type--
Source1 = Rs
Source2 = Constant
Dst = Rt
memRead = true
storeByte = true

--lbu: I Type--
Source1 = Rs
Source2 = Constant
Dst = Rt
regWrite = true
loadByte = true
memRead = true
memUnsigned = true;
memToReg = true;

--sll: R Type--
opCode = 2
Source1 = Rs
Source2 = Rt
Dst = Rd
regWrite = true

--nor: R Type--
opCode = 2
Source1 = Rs
Source2 = Rt
Dst = Rd
regWrite = true

--beq: I Type--
opCode = 1
Source1 = Rs
Source2 = Rt
branch = true

--j: j Type--
jump = true

--jal: j Type--
jumpAndLink = true

--jr: R Type--
jumpReg = true
regster address is in rs

--slti I Type--
regWrite = true
aluOp = 3
aluSrc1 = rs
aluSrc2 = const
dstReg = rt

*/

public class Control {
    private int aluOp;
    private int aluSrc1;
    private int aluSrc2;
    private int dstReg;
    
    private boolean regWrite;
    private boolean memWrite;
    private boolean memRead;
    private boolean loadByte;
    private boolean storeByte;
    private boolean memUnsigned;
    private boolean branch;
    private boolean jump;
    private int memToReg;
    private boolean jumpAndLink;
    private boolean jumpReg;
    private boolean aluSrcMux = true;
    private int regDstMux;
    
    private String instName;
    
    public Control(Instruction i, RegisterFile rf) {
        int opCode = i.getOpCode();
        switch(opCode) {
            //jal
            case 3:
                regDstMux = 2;
                memToReg = 2;
                instName = "jal";
                dstReg = 31;
                jumpAndLink = true;
                regWrite = true;
                break;
            //jump
            case 2:
                instName = "j";
                jump = true;
                break;
            //beq
            case 4:
                instName = "beq";
                aluOp = 1;
                aluSrc1 = rf.getRegisterValue(i.getRs());
                aluSrc2 = rf.getRegisterValue(i.getRt());
                branch = true;
                break;
            //R Type or Jr
            case 0:
                aluSrcMux = false;
                regDstMux = 1;
                //jr
                if(i.getFunct() == 8) {
                    instName = "jr";
                    jumpReg = true;
                    break;
                }
                //Normal R-Type procedure
                setInstructionName(i.getFunct());
                aluOp = 2;
                aluSrc1 = rf.getRegisterValue(i.getRs());
                aluSrc2 = rf.getRegisterValue(i.getRt());
                dstReg = i.getRd();
                regWrite = true;
                break;
            //addi
            case 8:
                instName = "addi";
                aluSrc1 = rf.getRegisterValue(i.getRs());
                aluSrc2 = i.getConstant();
                dstReg = i.getRt();
                regWrite = true;
                break;
            //slti    
            case 10:
                instName = "slti";
                regWrite = true;
                aluOp = 3;
                aluSrc1 = rf.getRegisterValue(i.getRs());
                aluSrc2 = i.getConstant();
                dstReg = i.getRt();
                break;
            //lw
            case 35:
                instName = "lw";
                aluSrc1 = rf.getRegisterValue(i.getRs());
                aluSrc2 = i.getConstant();
                dstReg = i.getRt();
                regWrite = true;
                memRead = true;
                memToReg = 1;
                break;
            //lb
            case 32:
                instName = "lb";
                aluSrc1 = rf.getRegisterValue(i.getRs());
                aluSrc2 = i.getConstant();
                dstReg = i.getRt();
                regWrite = true;
                memRead = true;
                loadByte = true;
                memToReg = 1;
                break;
            //lbu
            case 36:
                instName = "lbu";
                aluSrc1 = rf.getRegisterValue(i.getRs());
                aluSrc2 = i.getConstant();
                dstReg = i.getRt();
                regWrite = true;
                memRead = true;
                loadByte = true;
                memUnsigned = true;
                memToReg = 1;
                break;
            //sw
            case 43:
                instName = "sw";
                aluSrc1 = rf.getRegisterValue(i.getRs());
                aluSrc2 = i.getConstant();
                memWrite = true;
                break;
            //sb
            case 40:
                instName = "sb";
                aluSrc1 = rf.getRegisterValue(i.getRs());
                aluSrc2 = i.getConstant();
                memWrite = true;
                storeByte = true;
                break;
        }
    }
    
    private void setInstructionName(int funct){
        switch(funct){
            //add
            case 32:
                instName = "add";
                break;
            //nor    
            case 37:
                instName = "nor";
                break;
            //slt    
            case 42:
                instName = "slt";
                break;
            //sll
            case 0:
                instName = "sll";
                break;
        }
    }

    public int getAluOp() {
        return aluOp;
    }
    public int getAluSrc1() {
        return aluSrc1;
    }
    public int getAluSrc2() {
        return aluSrc2;
    }
    public int getDstReg() {
        return dstReg;
    }
    public boolean getRegWrite() {
        return regWrite;
    }
    public boolean getMemRead() {
        return memRead;
    }
    public boolean getMemWrite() {
        return memWrite;
    }
    public boolean getLoadByte() {
        return loadByte;
    }
    public boolean getStoreByte() {
        return storeByte;
    }
    public boolean getMemUnsigned() {
        return memUnsigned;
    }
    public boolean getBranch(){
        return branch;
    }
    public boolean getJump(){
        return jump;
    }
    public int getMemToReg(){
        return memToReg;
    }
    public boolean getJumpAndLink(){
        return jumpAndLink;
    }
    public boolean getJumpReg(){
        return jumpReg;
    }
    public boolean getAluSrcMux() {
        return aluSrcMux;
    }
    public int getRegDstMux() {
        return regDstMux;
    }
    public String getInstName() {
        return instName;
    }
    
}
