package archproj;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class memFrame extends JFrame{
    private Container c;
    private int memSize = 1024;
    private JLabel [] memLbl = new JLabel[memSize];
    private JTextField [] memVal = new JTextField[memSize];
    private JPanel scrollPanel = new JPanel();
    private JScrollPane scrollPane;
    private Memory mem;
    private JButton btnSave = new JButton("Save");
        
    public memFrame(Memory mem){
        this.mem = mem;
        init();
    }
    
    private void init(){
        this.setTitle("Memory");
        this.setBounds(1050, 0, 500, 855);
        //this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/memory2.png")).getImage()); 
        
        scrollPanel.setLayout(new GridLayout(64,8));

        c = this.getContentPane();
        
        //c.setLayout( new GridLayout(memSize/16,16) );        
        
        for (int i = 0; i < memSize; i +=4 ) {
            memLbl[i] = new JLabel("        "+i+"");
            memVal[i] = new JTextField("0");
            scrollPanel.add( memLbl[i] );
            scrollPanel.add( memVal[i]);
        }
        scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        c.add(scrollPane);
        c.add(btnSave, BorderLayout.SOUTH);
        
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMemory();
            }
        });
        
    } 
    
    public void memRefresh(){
        for(int i=0; i<memSize; i+=4){
            memVal[i].setText(mem.getValue(i)+"");
        }
    }
    
    public void setMemory(){
        for(int i=0; i<memSize; i+=4){
            mem.setValue(i, Integer.parseInt(memVal[i].getText())); 
            memVal[i].setEditable(false);
        }
    }
}

class resFrame extends JFrame{
    private JLabel lblRes = new JLabel();
    private Container c = getContentPane();
    
    public resFrame(){
        init();
    }
    
    private void init(){
        this.setTitle("Result");
        this.setBounds(50, 280, 650, 150);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/medical-history.png")).getImage());
        
    }   
    
    public void setRes(String s) {
        lblRes.setText(s);
        c.add(lblRes);
        
    }
}

class traceFrame extends JFrame{
    private JPanel pnlWires = new JPanel();
    private JPanel pnlControl = new JPanel();
    private JLabel [] wireLbls = new JLabel[19];
    private JLabel [] controlLbls = new JLabel[9];
    private String [] wireNames = new String [19];
    private String [] controlNames = new String [9];
    private int wireValues[] = new int [19];
    private int controlValues[] = new int [9];
    
    
    public traceFrame(){
        init();
    }
    
    private void init(){
        this.setTitle("Trace");
        this.setBounds(1190, 230, 500, 550);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/trace.png")).getImage());
        
        Container c = this.getContentPane();
        c.setLayout(new GridLayout(1,2));
        
        
        
        pnlWires.setBorder(BorderFactory.createTitledBorder("Wires"));
        pnlControl.setBorder(BorderFactory.createTitledBorder("Control Signals"));
        
        pnlWires.setLayout(new GridLayout(20,2));
        pnlControl.setLayout(new GridLayout(9,2));
        
        
        
        //-----Wires-----
        
        wireNames[0]= "PC output: ";
        wireNames[1]= "PC+4 adder output: ";
        //wireNames[2]= "Instruction(Hex): ";
        wireNames[2]= "Opcode field of instruction: ";
        wireNames[3]= "Rs field of instruction: ";
        wireNames[4]= "Rt field of instruction: ";
        wireNames[5]= "Rd field of instruction: ";
        wireNames[6]= "RegDestination Mux output: ";
        wireNames[7]= "Offset field of instruction: ";
        wireNames[8]= "Sign-extender Output: ";
        wireNames[9]= "Shift-2 output: ";
        wireNames[10]= "Target address adder: ";
        wireNames[11]= "Function code of instruction: ";
        wireNames[12]= "Read data 1: ";
        wireNames[13]= "Read data 2: ";
        wireNames[14]= "ALU second input: ";
        wireNames[15]= "ALU output: ";
        wireNames[16]= "Zero flag: ";
        wireNames[17]= "Data memory output: ";
        wireNames[18]= "MemtoReg Mux output: ";
        
        for (int i = 0; i < wireNames.length; i++) {
            wireValues[i]=0;
            wireLbls[i] = new JLabel(wireNames[i]+wireValues[i]+"");
            pnlWires.add(wireLbls[i]);
        }
        
        
        //-----Control Signals-----
        
        controlNames[0]= "RegDest: ";
        controlNames[1]= "Branch: ";
        controlNames[2]= "MemRead: ";
        controlNames[3]= "MemtoReg: ";
        controlNames[4]= "ALUOp: ";
        controlNames[5]= "MemWrite: ";
        controlNames[6]= "ALUSrc: ";
        controlNames[7]= "RegWrite: ";
        controlNames[8]= "ALU control output: ";
        
        for (int i = 0; i < controlNames.length; i++) {
            controlValues[i]=0;
            controlLbls[i] = new JLabel(controlNames[i]+controlValues[i]+"");
            pnlControl.add(controlLbls[i]);
        }

        
        //-----Allignment-----
        
        c.add(pnlWires);
        c.add(pnlControl);     
    }   
    
    public void traceRefresh(Instruction i, InstMem im,Control ic, ALUControl aluC, ALU alu, RegisterFile rf, int memVal){
        setWireValues(i,im, ic,rf,alu,memVal);
        setControlValues(ic , aluC);
    }
    
    public void setWireValues(Instruction i, InstMem im, Control ic, RegisterFile rf, ALU alu, int memVal){
        
        wireValues[0]= im.getPc();                                              //"PC output: ";
        wireValues[1]= im.getPc()+4;                                            //"PC+4 adder output: ";
        //wireValues[2]= Integer.parseInt(im.instFetch(),2);     //"Instruction(Hex): ";
        wireValues[2]= i.getOpCode();                                           //"Opcode field of instruction: ";
        wireValues[3]= i.getRs();                                               //"Rs field of instruction: ";
        wireValues[4]= i.getRt();                                               //"Rt field of instruction: ";
        wireValues[5]= i.getRd();                                               //"Rd field of instruction: ";
        wireValues[6]= ic.getDstReg();                                          //"RegDestination Mux output: ";
        wireValues[7]= i.getConstant();                                         //"Offset field of instruction: ";
        wireValues[8]= i.getConstant();                                         //"Sign-extender Output: ";
        wireValues[9]= wireValues[9]*4;                                         //"Shift-2 output: ";
        wireValues[10]= wireValues[10]+im.getPc();                              //"Target address adder: ";
        wireValues[11]= i.getFunct();                                           //"Function code of instruction: ";
        wireValues[12]= ic.getAluSrc1();                                        //"Read data 1: ";
        wireValues[13]= rf.getRegisterValue(i.getRt());                         //"Read data 2: ";
        wireValues[14]= ic.getAluSrc2();                                        //"ALU second input: ";
        wireValues[15]= alu.getRes();                                           //"ALU output: ";
        wireValues[16]= (alu.getZeroFlag())? 1 : 0;                             //"Zero flag: ";
        wireValues[17]= memVal;                                                 //"Data memory output: ";
        wireValues[18]= GUI.getMemToRegMuxOutput();                               //"MemtoReg Mux output: ";
     
        for (int j = 0; j < wireNames.length; j++) {
            wireLbls[j].setText(wireNames[j]+wireValues[j]+"");
            pnlWires.add(wireLbls[j]);
        }
        //wireLbls[2].setText(wireNames[2] + Integer.toHexString(wireValues[2]));
    }
    
    public void setControlValues(Control ic, ALUControl aluC){
        
        int branch = ic.getBranch() ? 1 : 0;
        int memRead = ic.getMemRead()? 1: 0;
        int memWrite = ic.getMemWrite() ? 1: 0;
        int ALUSrc = ic.getAluSrcMux()? 1: 0;
        int regWrite = ic.getRegWrite()? 1: 0;
        
        controlValues[0]= ic.getRegDstMux();                      //"RegDest: ";
        controlValues[1]= branch;                                 //"Branch: ";
        controlValues[2]= memRead;                                //"MemRead: ";
        controlValues[3]= ic.getMemToReg();                       //"MemtoReg: ";
        controlValues[4]= ic.getAluOp();                          //"ALUOp: ";
        controlValues[5]= memWrite;                               //"MemWrite: ";
        controlValues[6]= ALUSrc;                                 //"ALUSrc: ";
        controlValues[7]= regWrite;                               //"RegWrite: ";
        controlValues[8]= aluC.getAluControl();                   //"ALU control output: ";
        
        for (int i = 0; i < controlNames.length; i++) {
            controlLbls[i].setText(controlNames[i]+controlValues[i]+"");
            pnlControl.add(controlLbls[i]);
        }
    }
}

class helpFrame extends JFrame{
    private JTextArea help = new JTextArea();
        
    public helpFrame(){
        init();
    }
    
    private void init(){
        this.setTitle("Help");
        this.setBounds(450, 250, 450, 85);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/help.png")).getImage()); 
        
        help.setText("supported instructions: \n" +
                    "Add" +
                    "   Nor"+
                    "   Sll"+
                    "   Slt"+
                    "   Jr"+
                    "   J"+
                    "   Jal" +
                    "   Beq" +
                    "   Addi" +
                    "   Slti" +
                    "   Lb" +
                    "   Lbu" +
                    "   Lw" +
                    "   Sb" +
                    "   Sw");
        help.setBackground(new Color(235,235,235));
        help.setEditable(false);
        this.add(help);
    }   
}

class aboutFrame extends JFrame{
    private JTextArea about = new JTextArea();
        
    public aboutFrame(){
        init();
    }
    
    private void init(){
        this.setTitle("About");
        this.setBounds(450, 250, 350, 100);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/about.png")).getImage()); 
        
        about.setText("Trux Simulator v1.0 copyright 2018 \n" +
                     "Trux is the Mips Assembler and Runtime Simulator. \n");
        about.setBackground(new Color(235,235,235));
        about.setEditable(false);
        this.add(about);
    }   
}

class contactFrame extends JFrame{     
    private JPanel pnlInfo = new JPanel();
    private JPanel pnlNames = new JPanel();
    private JPanel pnlIds = new JPanel();    
    private JPanel pnlEmails = new JPanel();       
    private JLabel txt = new JLabel("This silmulator is made by :");
    private JLabel name1 = new JLabel("Abdelrahman ELGhamry");
    private JLabel name2 = new JLabel("Hossam ELDin Khaled");
    private JLabel name3 = new JLabel("Abdelrahman Amr Issawi");
    private JLabel id1 = new JLabel("16P3043");
    private JLabel id2 = new JLabel("16P3025");
    private JLabel id3 = new JLabel("16P6001");
    private JLabel email1 = new JLabel("Ghamry98@hotmail.com");
    private JLabel email2 = new JLabel("Hossampen97@gmail.com");
    private JLabel email3 = new JLabel("aid-issawi@hotmail.com");
   
    public contactFrame(){
        init();
    }
    
    private void init(){
        this.setTitle("Contact Us");
        this.setBounds(450, 250, 500, 250);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/contact.png")).getImage()); 
        
        this.setLayout( new BorderLayout() );
        //this.add(txt , BorderLayout.NORTH);
        this.add(pnlInfo , BorderLayout.CENTER);
        
        pnlInfo.setLayout( new GridLayout(1,3) );
        pnlInfo.add(pnlNames);
        pnlInfo.add(pnlIds);
        pnlInfo.add(pnlEmails);
        
        pnlNames.setLayout( new GridLayout(3,1) );
        pnlNames.add(name1);
        pnlNames.add(name2);
        pnlNames.add(name3);
        
        pnlIds.setLayout( new GridLayout(3,1) );
        pnlIds.add(id1);
        pnlIds.add(id2);
        pnlIds.add(id3);
        
        pnlEmails.setLayout( new GridLayout(3,1) );
        pnlEmails.add(email1);
        pnlEmails.add(email2);
        pnlEmails.add(email3);
    }
}


public class GUI extends JFrame {
//-----Members-----
    //let memSize be static to easily use it in registerFile class 
    private static int memSize = 1023;
    private InstMem im = new InstMem();
    private RegisterFile reg = new RegisterFile();
    private Memory mem = new Memory(memSize);
    
    private int clock = 0;
    private int startingAddress=0;
    private JMenuBar bar = new JMenuBar();
    private JMenu mnuFile = new JMenu("File");
    private JMenu mnuShow = new JMenu("Show");
    private JMenu mnuRun = new JMenu("Run");
    private JMenu mnuHelp = new JMenu("Help");
    
    private JMenuItem mnuiNew = new JMenuItem("New");
    private JMenuItem mnuiExit = new JMenuItem("Exit");
    
    private JMenuItem mnuiResult = new JMenuItem("Result");
    private JMenuItem mnuiMemory = new JMenuItem("Memory");
    private JMenuItem mnuiTrace = new JMenuItem("Trace");
    
    private JMenuItem mnuiRun = new JMenuItem("Run all");
    private JMenuItem mnuiStep = new JMenuItem("Step");
    
    private JMenuItem mnuiHelp = new JMenuItem("Help");
    private JMenuItem mnuiAbout = new JMenuItem("About...");
    private JMenuItem mnuiContact = new JMenuItem("Contact Us");
    
    private JPanel pnlCirc = new JPanel();
    private JPanel pnlReg = new JPanel();
    private JPanel pnlBottom = new JPanel();
            
    private JPanel pnlCode = new JPanel();
    private JPanel pnlBottomRight = new JPanel();
    
    private JPanel pnlExtras = new JPanel();
    private JPanel pnlExec = new JPanel();
    private JPanel pnlBtns = new JPanel();
    
    private JTextArea areaCode = new JTextArea("Enter Your Code Here...",5,30);
    final private JTextField lblStartingAddress = new JTextField("0",21);
    private JScrollPane scrollAreaCode;
    
    private JPanel pnlClock = new JPanel();
    private JPanel pnlStartingAddress = new JPanel();
    private JLabel lblClock;
    private JLabel lblExec = new JLabel();
    private JButton btnStart = new JButton("Start");
    private JButton btnStep = new JButton("Step");
    private JButton btnMem = new JButton("Show Memory");
    private JButton btnRes = new JButton("Show Result");
    private JButton btnTrace = new JButton("Trace");
    private JButton btnSaveCode = new JButton("Save");
    
    private JButton btnSet = new JButton("Set");
    
    private JLabel lblImg;
    
    private JLabel[] regNames = new JLabel[32];
    private JTextField [] regValues = new JTextField[32];
    
    private resFrame res = new resFrame();
    private traceFrame trace = new traceFrame();
    private memFrame memoryFrame = new memFrame(mem);

    private static int memToRegMuxOutput; 
    
    
    //-----Constructor-----
    public GUI(){
        init() ;
    }
    
    private void init(){
        //-----Frame-----
        this.setTitle("Trux Simulator");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setBounds(50, 30, 1000, 700);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/computer.png")).getImage()); 
        
        Container c = this.getContentPane();
        c.setLayout( new BorderLayout());
        
        //-----Menu-----
        this.setJMenuBar(bar);
        bar.add(mnuFile);
        bar.add(mnuShow);
        bar.add(mnuRun);
        bar.add(mnuHelp);
        
        mnuFile.add(mnuiNew);
        mnuFile.add(mnuiExit);
        
        mnuShow.add(mnuiMemory);
        mnuShow.add(mnuiResult);
        mnuHelp.addSeparator();
        mnuShow.add(mnuiTrace);
        
        mnuRun.add(mnuiRun);
        mnuRun.add(mnuiStep);
        
        mnuHelp.add(mnuiHelp);
        mnuHelp.add(mnuiAbout);
        mnuHelp.addSeparator();
        mnuHelp.add(mnuiContact);
        
        
        //-----RegFile-----
        pnlReg.setLayout(new GridLayout(32,2));
        pnlReg.setPreferredSize(new Dimension(150,0));
        for (int i = 0; i < 32; i++) {
            regNames[i]= new JLabel(reg.getRegisterName(i));
            pnlReg.add(regNames[i]);
            
            regValues[i] = new JTextField(reg.getRegisterValue(i)+"",6);
            regValues[i].setHorizontalAlignment(SwingConstants.RIGHT);
            regValues[i].setBorder(BorderFactory.createLineBorder(Color.black, 1));
            pnlReg.add(regValues[i]);
        }
        regValues[0].setEditable(false);
        regValues[1].setEditable(false);
        regValues[26].setEditable(false);
        regValues[27].setEditable(false);
        regValues[30].setEditable(false);
        
        
        
        //-----Labels-----
        lblClock = new JLabel(clock+"");
        
        
        //-----Body Allignment-----
        pnlCirc.setBorder(BorderFactory.createTitledBorder("Circuit:"));
        pnlReg.setBorder(BorderFactory.createTitledBorder("Registers:"));
        
        pnlCode.setLayout(new BorderLayout(5,5));
        pnlCode.setBorder(BorderFactory.createTitledBorder("Code:"));
        scrollAreaCode = new JScrollPane(areaCode ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnlCode.add(scrollAreaCode);
        pnlCode.add(btnSaveCode,BorderLayout.SOUTH);
        
        pnlClock.setBorder(BorderFactory.createTitledBorder("Clock Counter:"));
        pnlClock.add(lblClock);
        
        pnlStartingAddress.setBorder(BorderFactory.createTitledBorder("Starting Address:"));
        pnlStartingAddress.setLayout(new GridLayout(1,2));
        pnlStartingAddress.add(lblStartingAddress);
        pnlStartingAddress.add(btnSet);
        lblStartingAddress.setHorizontalAlignment(SwingConstants.CENTER);
        
        pnlExec.setBorder(BorderFactory.createTitledBorder("Executing Line number '" + im.getPc() + "' :"));
        pnlExec.add(lblExec);
        
        pnlExtras.setLayout( new GridLayout(1,2) );
        pnlExtras.add(pnlStartingAddress);
        pnlExtras.add(pnlClock);
        
        pnlBtns.add(btnStart);
        pnlBtns.add(btnStep);
        pnlBtns.add(btnMem);
        pnlBtns.add(btnRes);
        pnlBtns.add(btnTrace);
        
        pnlBottomRight.setLayout( new BorderLayout() );
        pnlBottomRight.add(pnlExtras , BorderLayout.NORTH);
        pnlBottomRight.add(pnlExec , BorderLayout.CENTER);
        pnlBottomRight.add(pnlBtns , BorderLayout.SOUTH);
                
        pnlBottom.setLayout( new GridLayout(1,2) );
        pnlBottom.setPreferredSize(new Dimension(0,165));
        pnlBottom.add(pnlCode);
        pnlBottom.add(pnlBottomRight);
        
        c.add(pnlCirc , BorderLayout.CENTER);
        c.add(pnlReg , BorderLayout.EAST);
        c.add(pnlBottom , BorderLayout.SOUTH);
        
        
        //The images must be imported in the package folder 
        ImageIcon icCircuit = new ImageIcon(getClass().getResource("/Images/1fullnetbeans.png"));
         
        lblImg = new JLabel(icCircuit , JLabel.CENTER);
        pnlCirc.setPreferredSize(new Dimension(806,510));
        pnlCirc.add(lblImg);
        //repaint();
        
        
        pack();
        
        
        //-----Action Listeners-----
        btnSaveCode.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblStartingAddress.setEditable(false);
                im.setPc(Integer.parseInt(lblStartingAddress.getText()));
                String[] dvdCode = areaCode.getText().split("\n");
                for(String s:dvdCode)
                    im.enterCode(s);
                areaCode.setEditable(false);
                for(int i=0; i<32; i++){
                    regValues[i].setEditable(false);
                    reg.setRegisterValue(i, Integer.parseInt(regValues[i].getText()), true);
                }
                memoryFrame.setMemory();
            }
        });
        
        btnStart.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                while(im.instFetch() != null) {
                    runCode();
                }
                if(im.instFetch() == null){
                    res.setRes("Build Successfully!");
                    JOptionPane.showMessageDialog(pnlCirc, "Program ended!");
                }
            }
        });
        
        btnStep.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lblStartingAddress.setEditable(false);
                if(im.instFetch() != null)
                    runCode();
                else{
                    res.setRes("Build Successfully!");
                    res.setVisible(true);
                    JOptionPane.showMessageDialog(pnlCirc, "Program ended!");
                }
            }
        });
        
        btnMem.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                memoryFrame.setVisible(true);
            }
        });   
        
        btnRes.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                res.setVisible(true);
            }
        });
        
        btnTrace.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                trace.setVisible(true);
            }
        });
        
        mnuiNew.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                trace.setVisible(false);
                memoryFrame.setVisible(false);
                res.setVisible(false);
                ArchProj.reset();
            }
        });
        
        mnuiExit.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
                
        mnuiResult.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                res.setVisible(true);
            }
        });
        
        mnuiMemory.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                memoryFrame.setVisible(true);
            }
        }); 
        
        mnuiTrace.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                traceFrame trace = new traceFrame();
                trace.setVisible(true);
            }
        });
        
        mnuiRun.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                while(im.instFetch() != null) {
                    runCode();
                }
                if(im.instFetch() == null){
                    res.setRes("Build Successfully!");
                    JOptionPane.showMessageDialog(pnlCirc, "Program ended!");
                }
            }
        });
        
        mnuiStep.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lblStartingAddress.setEditable(false);
                if(im.instFetch() != null)
                    runCode();
                else{
                    res.setRes("Build Successfully!");
                    JOptionPane.showMessageDialog(pnlCirc, "Program ended!");
                }
            }
        });
        
        mnuiHelp.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                helpFrame help = new helpFrame();
                help.setVisible(true);
            }
        });
        
        mnuiAbout.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                aboutFrame about = new aboutFrame();
                about.setVisible(true);
            }
        });
        
        mnuiContact.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                contactFrame contact = new contactFrame();
                contact.setVisible(true);
            }
        });
        
        lblStartingAddress.addKeyListener( new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                    im.setPc(Integer.parseInt(lblStartingAddress.getText()));
                    lblStartingAddress.setEditable(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
        
        areaCode.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if( areaCode.getText().equals("Enter Your Code Here...") )
                    areaCode.setText("");
            }
        });
        
        btnSet.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                im.setPc(Integer.parseInt(lblStartingAddress.getText()));
                lblStartingAddress.setEditable(false);
            }
        });
    }
     
    //-----Methods-----
      
    public void runCode(){
            //1.instruction fetch
            Instruction i = new Instruction(im.instFetch());
            lblExec.setText(im.instFetch());

            //2.Decoding and Reg Read
            Control ic = new Control(i,reg);
            
            //Increment clock cycle
            lblClock.setText(++clock +"");

            //3.Execution
            ALUControl aluC = new ALUControl(ic.getAluOp(), i.getFunct());
            ALU alu = new ALU( ic.getAluSrc1() ,ic.getAluSrc2() ,aluC.getAluControl() , i.getShamt() );
            
            //--flow--
            
            //--branch
            int pc=0;
            if(ic.getBranch() && alu.getZeroFlag()) {
                pc = im.getPc()+i.getConstant()*4 + 4;    //update pc, pc = pc + offset*4 
            }

            //--jump
            else if(ic.getJump()) {                  //unconditional jump no need for further checking
                pc = im.getPc() & 0xf0000000;
                //im.pc |= 0xf0000000;            //update pc with its most 4 significant bits
                pc = pc+i.getJumpAdd()*4;
                //im.pc += i.getJumpAdd()*4;      //pc = 4 most significant bits + 26 bits from instruction shifted 2 times
            }
            
            //--jal
            else if(ic.getJumpAndLink()) {
                reg.setRegisterValue(31, im.getPc()+4, true);
                regRefresh(31,im.getPc()+4);
                
                pc = im.getPc() & 0xf0000000;
                pc = pc+i.getJumpAdd()*4;
            }
            
            //--jr
            else if(ic.getJumpReg()){
                pc = reg.getRegisterValue(i.getRs()); 
            }
            
            //4.Memory operands
            int memVal = 0;
            if(ic.getMemRead())
                memVal = mem.load(alu.getRes(), ic.getLoadByte(), ic.getMemUnsigned());
            if(ic.getMemWrite()) {
                mem.store(alu.getRes(), reg.getRegisterValue(i.getRt()), ic.getStoreByte());
                memoryFrame.memRefresh();
            }
                
            //5.WriteBack
            if( ic.getRegWrite() ) {
                if( ic.getMemToReg() == 1 )
                    memToRegMuxOutput = memVal;
                else if( ic.getMemToReg() == 0 )
                    memToRegMuxOutput = alu.getRes();
                else 
                    memToRegMuxOutput = (im.getPc()+4);
                
                reg.setRegisterValue(ic.getDstReg(), memToRegMuxOutput, ic.getRegWrite());
                
                //Register Refresh
                if(ic.getDstReg() != 0)
                    regRefresh(ic.getDstReg(),memToRegMuxOutput);
            }
            
            //Trace Refresh
            trace.traceRefresh(i, im, ic, aluC, alu, reg, memVal);
            
            //Image Update
            imageRefresh(ic);
            
            //Update program counter
            
            //--jump || jal || jr
            if(ic.getJump() || ic.getJumpAndLink() || ic.getJumpReg() || (ic.getBranch() && alu.getZeroFlag())) {                  //unconditional jump no need for further checking
                im.setPc(pc);
            }
            else{
                im.setPc( im.getPc() + 4 );
            }
    }
    
    public void regRefresh( int i , int value ){
        regValues[i].setText(value+"");
       /*
        for (int j = 0; j < 32; j++) {
            pnlReg.add(regNames[j]);
            pnlReg.add(regValues[j]);
        }
        */
    }
    
    public void imageRefresh( Control ic ){
        String instName = ic.getInstName();
        pnlExec.setBorder(BorderFactory.createTitledBorder("Executing Line number '" + im.getPc() + "' : "+instName));
        pnlCirc.remove(lblImg);
        ImageIcon newIcon = new ImageIcon(getClass().getResource("/Images/"+instName+".png"));
        lblImg = new JLabel(newIcon, JLabel.CENTER);
        pnlCirc.add(lblImg);
        repaint();
    }

    public static int getMemSize() {
        return memSize;
    }
    
    public static int getMemToRegMuxOutput(){
        return memToRegMuxOutput;
    }
}