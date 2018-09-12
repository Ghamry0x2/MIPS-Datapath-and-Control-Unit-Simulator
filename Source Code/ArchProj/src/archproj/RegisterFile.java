package archproj;

class RegisterFile {
    private int registersValues[] = new int [32];
    private String [] registersNames = new String [32];
    
    public RegisterFile() {
        registersNames[0]= "$0";
        registersNames[1]= "$at";
        registersNames[2]= "$v0";
        registersNames[3]= "$v1";
        registersNames[4]= "$a0";
        registersNames[5]= "$a1";
        registersNames[6]= "$a2";
        registersNames[7]= "$a3";
        registersNames[8]= "$t0";
        registersNames[9]= "$t1";
        registersNames[10]= "$t2";
        registersNames[11]= "$t3";
        registersNames[12]= "$t4";
        registersNames[13]= "$t5";
        registersNames[14]= "$t6";
        registersNames[15]= "$t7";
        registersNames[16]= "$s0";
        registersNames[17]= "$s1";
        registersNames[18]= "$s2";
        registersNames[19]= "$s3";
        registersNames[20]= "$s4";
        registersNames[21]= "$s5";
        registersNames[22]= "$s6";
        registersNames[23]= "$s7";
        registersNames[24]= "$t8";
        registersNames[25]= "$t9";
        registersNames[26]= "$k0";
        registersNames[27]= "$k1";
        registersNames[28]= "$gp";
        registersNames[29]= "$sp";
        registersNames[30]= "$fp";
        registersNames[31]= "$ra";
        
        for (int i = 0; i < 32; i++) {
            registersValues[i]=0;
        }
        //initialize sp with last element in memory
        registersValues[29] = GUI.getMemSize()+1;
        registersValues[30] = GUI.getMemSize()+1;
    }
    
    public void setRegisterValue(int address, int val, boolean regWrite) {
        if(regWrite && address!=0)
            registersValues[address]=val;
    }
    public int getRegisterValue(int address) { 
        return registersValues[address] ;
    }
    public String getRegisterName(int address) {
        return registersNames[address];
    }
}