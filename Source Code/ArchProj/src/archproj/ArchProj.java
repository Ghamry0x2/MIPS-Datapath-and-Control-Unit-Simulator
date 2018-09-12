package archproj;

public class ArchProj {
    static GUI gui;
    
    public static void main(String[] args){
        gui = new GUI();
        gui.setVisible(true);
    }
    
    public static void reset(){
        gui.setVisible(false);
        gui = new GUI();
        gui.setVisible(true);
    }
}
