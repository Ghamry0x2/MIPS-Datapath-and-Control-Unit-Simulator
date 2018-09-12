package archproj;

//Memory is called from outside by (bytes not words) addressing
//We are dividing by 4 to load the complete 32bits word, so its working on line "word" addressing , not byte addressing,  so internally 1,2,3... are representing the line no.
//Each memory place in the array, represents a word, line
//We need to store values in memory in decimal format
public class Memory {
    private int[] mem;
    private int size;
    private int word;

    public Memory(int size) {
        this.size = size;
        mem = new int[size];
        
        for(int i = 0 ; i < size ; i++ ){
            mem[i]=0;
        }
    }
    
    //We need to handle memory misuse
    public int getValue(int address){
        return mem[address/4];
    }
    
    public void setValue (int address, int value){
        mem[address/4]=value;
        word = value;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
    
    
    //Hossam's Methods
    //Combo Methods AHAHAHAHHAHAHA
    //Suports lb, lbu, lw, sb, sw
    public void store(int address, int value, boolean storeByte) {
        if(storeByte){
            value &= 0x000000ff;
            int t = 0;
            int subAddress = address%4;
            switch(subAddress){
                case 0:
                    mem[address/4] &= 0xffffff00;
                    t = mem[address/4];
                    t |= value;
                    break;
                case 1:
                    value = value << 8;
                    mem[address/4] &= 0xffff00ff;
                    t = mem[address/4];
                    t |= value;
                    break;
                case 2:
                    value = value << 16;
                    mem[address/4] &= 0xff00ffff;
                    t = mem[address/4];
                    t |= value;
                    break;
                case 3:
                    value = value << 24;
                    mem[address/4] &= 0x00ffffff;
                    t = mem[address/4];
                    t |= value;
                    break;
            }
            mem[address/4] |= t;
        }
        else {
            //insert the whole 32-bit
            //address is multiple of 4
            mem[address/4] = value;
        }
        
    }
    public int load(int address, boolean loadByte, boolean memUnsigned) {
        int value = 0;
        if(loadByte){
            int subAddress = address%4;
            switch(subAddress){
                case 0:
                    value = mem[address/4] & 0x000000ff;
                    break;
                case 1:
                    value = mem[address/4] &  0x0000ff00;
                    value = value >>> 8;
                    break;
                case 2:
                    value = mem[address/4] &  0x00ff0000;
                    value = value >>> 16;
                    break;
                case 3:
                    value = mem[address/4] &  0xff000000;
                    value = value >>> 24;
                    break; 
            }
            if(memUnsigned){
                //load unsigned byte
                return value;
            } 
            else {
                //load signed //implements sign extend
                int temp = value << 24;
                if(temp < 0)
                    value |= 0xffffff00;
            }
        }
        else {
            //load the whole 32bit
            value = mem[address/4];
        }
        return value;
    }
    
    

    @Override //returns  location: value
    public String toString(){
        
        String s="";
        
        for(int i = 0; i <= size ; i=i+4){
            s+= i + ":"+ mem[i];
        }
        return s;
    }
}