import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;
import java.util.*;


public class servo {
  
  
  
  public static void main(String[] args) throws InterruptedException {
    Scanner sc = new Scanner(System.in);
    
    String eingabe = "";
    int e = 0;
    
    int pinnr = 0;
    
    Gpio.wiringPiSetup();
    SoftPwm.softPwmCreate(pinnr, 0, 100);
    
    while (e >= 0) { 
      System.out.println("PWM amount: ");
      eingabe = sc.next();
      e = Integer.parseInt(eingabe);    
      
      SoftPwm.softPwmWrite(pinnr, e);
      Thread.sleep(25);
    } // end of while
    SoftPwm.softPwmWrite(pinnr, 15);
    Thread.sleep(500);
    System.out.println("Ende");
    
    
  } // end of main
  
} // end of class servo
