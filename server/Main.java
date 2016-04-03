//server

import com.pi4j.wiringpi.Gpio;      //importing gpio libary
import com.pi4j.wiringpi.SoftPwm; 

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {

  public static void main(String[] args){
    int pwmamount = 0;
      int pinnr = 0;
      Gpio.wiringPiSetup();             //setup the pi for pgio
      SoftPwm.softPwmCreate(pinnr, 0, 100);     //create pwm  (pinnr, min, max)
    
    try
      {
        //Warte auf Anfragen auf Port 13000:
        ServerSocket serverSocket = new ServerSocket(13000); 
        System.out.println("Server: Socket open");
        //Eine einzige Anfrage entgegennehmen:
        Socket clientSocket = serverSocket.accept();
        System.out.println("Server: Client connected");
        while(pwmamount >= 0){
          BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   //wait for an input Stream
          String serverResponse = in.readLine();        //server answer to String
          
          pwmamount = Integer.parseInt(serverResponse);       //server answer to integer
          SoftPwm.softPwmWrite(pinnr, pwmamount);       //set (pinnr, any number between min & max)
          System.out.println("PWM set to: " + pwmamount);
          Thread.sleep(25);
        }
        serverSocket.close();
        System.out.println("Server: Socket closed");
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }

  }

}

