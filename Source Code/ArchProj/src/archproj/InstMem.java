package archproj;

public class InstMem {
    private int pc;
    private String [] instMem = new String[1000];
    private int i;

    public InstMem() {
        this.pc=0;
    }
    
    public void enterCode(String s) {
        instMem[i++] = s;
    }

    public String instFetch() {
        return instMem[pc];
    }
    public int getPc() {
        return pc*4;
    }
    public void setPc(int pc) {
        this.pc = pc/4;
        i = this.pc;
        System.out.println(i);
    }
}
