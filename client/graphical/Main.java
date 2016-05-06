import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.io.PrintWriter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main extends Frame implements ActionListener{
  // start attributes
  static Button bArr[] = new Button[9];                 //Buttons for the directions
  static Button bConnect = new Button();                //Connect button
  static Button bClose = new Button();                  //Close button
  static Button bPu = new Button();                     //increase power button
  static Button bPd = new Button();                     //decrease power button
  static Label lPower = new Label();                    //show current power label
  static Label lStatus = new Label();                   //show current status label
  static TextField tIP = new TextField();               //ip textfield
  static int power = 4;
  static Socket socket;
  static PrintWriter out; 
  static BufferedReader in; 
  static boolean connected = false;
  // end attributes
  
  public Main(String title) { 
    // Frame-Init
    super(title);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { dispose(); 
      System.out.println("closed"); 
      disconnect();
      System.exit(0);
      return;
      }
    });
    int frameWidth = 300; 
    int frameHeight = 300;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setResizable(true);
    Panel cp2 = new Panel(new BorderLayout());
    add(cp2);


    Panel cp = new Panel(new GridLayout(3,3));
    cp2.add(cp, BorderLayout.CENTER);

    for (int i = 0;i < bArr.length ; i++) {                 //create 9 buttons
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

    lPower.setText("Power: " + power);
    lStatus.setBackground(Color.LIGHT_GRAY);
    lStatus.setText("Not connected");
    tIP.setText("192.168.3.1");

    bPu.setLabel("+");
    bPu.addActionListener(this);
    bPd.setLabel("-");
    bPd.addActionListener(this);
    

    bConnect.setLabel("connect");
    bConnect.addActionListener(this);
    bClose.setLabel("close");
    bClose.addActionListener(this);
    
    Panel cp3 = new Panel(new GridLayout(2,2));
    cp2.add(cp3, BorderLayout.NORTH);

    Panel cp4 = new Panel(new GridLayout(1,2));
    cp4.add(bPd);
    cp4.add(bPu);

    cp3.add(lPower);
    cp3.add(cp4);
    cp3.add(tIP);
    cp3.add(lStatus);
    cp3.add(bConnect);
    cp3.add(bClose);

    // start components   
    // end components
    setVisible(true);
  } // end of public Main
  
  // start methods
  
  public void actionPerformed(ActionEvent evt){
    String action = evt.getActionCommand();
    System.out.println("-------------" + action);
    try{
        if (action.equals("connect")){                  //connect
            System.out.println("trying to (re)connect");
            reconnect();
        }else if(action.equals("close")){               //disconnect
            System.out.println("closing down the socket");
            disconnect();
        }else if(action.equals("-")){                   //decrease power
            if(power>1){
                power--;
            }
            lPower.setText("Power: "+power);
        }else if(action.equals("+")){                   //increase power
            power++;
            lPower.setText("Power: "+power);
        }else if(action.equals("0")){                   //stop
            if(connected){
                send("000");
            }
        }else{                                          //move
            if(connected){
                int actionInt = Integer.parseInt(action);
                System.out.println(actionInt+"0"+power);
                send(actionInt+"0"+power);
            }
        }
    }catch(Exception ex){
        ex.printStackTrace();
        lStatus.setBackground(Color.RED);
        lStatus.setText("Failed"); 
        connected = false;
    }
  }
  public static void reconnect(){
    try{
        
        System.out.println("Client: Searching for Socket");
        socket = new Socket (tIP.getText(), 13000);  //ip address and port of the server 
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connected");
        lStatus.setBackground(Color.GREEN);
        lStatus.setText("Connected");
        connected = true;      
    }catch(Exception ex){
        ex.printStackTrace();
        lStatus.setBackground(Color.RED);
        lStatus.setText("Failed");
        connected = false;
    }
  }
  public static void disconnect(){
    if(connected){
        try{
            send("0100");
            socket.close();
            lStatus.setBackground(Color.LIGHT_GRAY);
            lStatus.setText("Not connected");
            connected = false;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
  }
  public static void send(String message){
      out.println(message);
  }

  public static void main(String[] args) {
    new Main("Main"); 
  } // end of main
    
    // end methods
} // end of class Main
