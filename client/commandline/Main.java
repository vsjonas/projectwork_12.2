//client

import java.net.Socket;
import java.io.PrintWriter;
import java.util.*;		

public class Main {

	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
		String message = "0";
		
	    try
	    {
	      //Verbindung zu Port 13000 auf 192.168.3.1 aufbauen:
	    	System.out.println("Client: Searching for Socket");
	    	Socket socket = new Socket ("192.168.3.1", 13000);  //ip address and port of the server 
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    	while(message.equals("010")){
	    		System.out.println("Enter PWM amount:");
	    		message  = sc.next();
	    		//Senden eines Newline sorgt dafür, dass der PrintWriter die Ausgabe "abschickt". Alternative müsste "flush" aufgerufen werden.
                out.println(message);
	    	}	    	
	      //Socket dichtmachen:
	      socket.close();
	      System.out.println("Client: Socket closed");
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
}
