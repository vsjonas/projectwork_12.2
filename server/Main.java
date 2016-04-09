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
    int pinnrL = 0;
    int pinnrR = 1;
    int power = 0;
    String serverResponse = "0000";

    Gpio.wiringPiSetup();             //setup the pi for pgio
    SoftPwm.softPwmCreate(pinnrL, 0, 100);     //create pwm  (pinnr, min, max)
    SoftPwm.softPwmCreate(pinnrR, 0, 100);   
 
    try
      {
        //Warte auf Anfragen auf Port 13000:
        ServerSocket serverSocket = new ServerSocket(13000); 
        System.out.println("Server: Socket open");
        //Eine einzige Anfrage entgegennehmen
        Socket clientSocket = serverSocket.accept();
        System.out.println("Server: Client connected");
        while(Integer.parseInt(serverResponse.substring(1,2)) != 1){
          BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   //wait for an input Stream
	  serverResponse = in.readLine();        //reads new Response
          System.out.println(serverResponse);

	  pwmamount = Integer.parseInt(serverResponse.substring(0,1));       //server answer to integer 
	  power = Integer.parseInt(serverResponse.substring(2,4));
	 
          //SoftPwm.softPwmWrite(pinnr, pwmamount);       //set (pinnr, any number between min & max)
          //System.out.println("PWM set to: " + pwmamount);  
	  
	  System.out.println("direction: " + pwmamount);
	  System.out.println("power: " + power);
	  
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

