//client

import java.net.Socket;
import java.io.PrintWriter;
import java.util.*;	

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static Socket socket;
    static PrintWriter out;
    static BufferedReader in;
    static boolean connected = false;
    static Scanner sc;
    
    	public static void main(String[] args) {
	    sc = new Scanner(System.in);
		String message = "000";
		
	    try{
            System.out.println("Client: Searching for Socket");
	    	socket = new Socket ("192.168.3.1", 13000);  //ip address and port of the server 
            out = new PrintWriter(socket.getOutputStream(), true);
	    	while(!message.startsWith("010")){
	    		System.out.println("Enter PWM amount:");
                message.equals("010");
	    		message  = sc.next();
                //if(!message.startsWith("010")){
	    		out.println(message);
                //}
	    	}
            socket.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
}
