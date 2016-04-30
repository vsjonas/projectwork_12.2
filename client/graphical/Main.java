import java.awt.*;
import java.awt.event.*;

/**
  *
  * Description
  *
  * @version 1.0 from 29/04/2016
  * @author 
  */



public class Main extends Frame implements ActionListener{
  // start attributes
  Button bArr[] = new Button[9];
  // end attributes
  
  public Main(String title) { 
    // Frame-Init
    super(title);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { dispose(); }
    });
    int frameWidth = 300; 
    int frameHeight = 300;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setResizable(false);
    Panel cp2 = new Panel(new BorderLayout());
    add(cp2);

    Button connect = new Button();
    connect.setLabel("connect");
    connect.addActionListener(this);

    Panel cp = new Panel(new GridLayout(3,3));
    cp2.add(cp, BorderLayout.CENTER);
    for (int i = 0;i < bArr.length ; i++) {
        bArr[i] = new Button();
    //    bArr[i].setLabel(""+i);
        bArr[i].addActionListener(this);
        cp.add(bArr[i]);
    }

    bArr[0].setLabel("8");
    bArr[1].setLabel("1");
    bArr[2].setLabel("2");
    bArr[3].setLabel("7");
    bArr[4].setLabel("0");
    bArr[5].setLabel("3");
    bArr[6].setLabel("6");
    bArr[7].setLabel("5");
    bArr[8].setLabel("4");
   
    // start components   
    // end components
    
    setVisible(true);
  } // end of public Main
  
  // start methods
  
  public void actionPerformed(ActionEvent evt){
    String action = evt.getActionCommand();
    System.out.println(action);
    if (action.equals("connect")){
        
    }
    
  }
  public static void main(String[] args) {
    new Main("Main");
  } // end of main
    
    // end methods
} // end of class Main
