//server

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.PrintWriter;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

public class Main{
    public static int pwmModL = 13;
    public static int pwmModR = 13;
    public static int pinnrL = 0;
    public static int pinnrR = 1;
    public static int option = 0;
    public static PrintWriter out;
    static ServerSocket serverSocket;

    public static void main(String[] args){
        Gpio.wiringPiSetup();             //setup the pi for pgio
        SoftPwm.softPwmCreate(pinnrL, 0, 100);     //create pwm  (pinnr, min, max)
        SoftPwm.softPwmCreate(pinnrR, 0, 100);
        
        while(option != 2){
            option = 0;
            int pwmamount = 0;    
            int power = 0;
            String serverResponse = "000";
            try
              {
                //Warte auf Anfragen auf Port 13000:
                serverSocket = new ServerSocket(13000); 
                System.out.println("Server: Socket open");
                //Eine einzige Anfrage entgegennehmen
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server: Client connected");
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                while(option != 1 && option != 2){
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   //wait for an input Stream
                    serverResponse = in.readLine();        //reads new Response
                    System.out.println(serverResponse);
                    
                    pwmamount = Integer.parseInt(serverResponse.substring(0,1));       //server answer to integer 
                    option = Integer.parseInt(serverResponse.substring(1,2));
                    power = Integer.parseInt(serverResponse.substring(2,3));

                    System.out.println("direction: " + pwmamount);
                    System.out.println("option: " + option);
                    System.out.println("power: " + power);
                    switch(pwmamount){
                        case(0):
                            setMotor(0,0,0);
                        break;
                        case(1):
                            setMotor(1,1,power);
                        break;
                        case(2):
                            setMotor(1,0,power);
                        break;
                        case(3):
                            setMotor(1,-1,power);
                        break;
                        case(4):
                            setMotor(-1,0,power);
                        break;
                        case(5):
                            setMotor(-1,-1,power);
                        break;
                        case(6):
                            setMotor(0,-1,power);
                        break;
                        case(7):
                            setMotor(-1,1,power);
                        break;
                        case(8):
                            setMotor(0,1,power);
                        break;
                    }
                
                    Thread.sleep(25);
                }
                serverSocket.close();
                System.out.println("Server: Socket closed\n----------------------");
              }
              catch (Exception e){
                e.printStackTrace();
                try{
                    serverSocket.close();
                }catch(Exception f){
                    f.printStackTrace();
                }
              }
         }
    }
  public static void setMotor(int l, int r, int speed){
      if (l != 0){
          if (l>0){
              l = l + speed;
          } else {
              l = l - speed;
          }
          l = l + pwmModL;
      }
      if (r != 0){
          if (r>0){
              r = r + speed;
          } else {
              r = r - speed;
          }          
          r = r + pwmModR;
      }
      SoftPwm.softPwmWrite(pinnrL,l);
      SoftPwm.softPwmWrite(pinnrR,r);
  }

}
