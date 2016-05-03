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
    static boolean runPing = true;
    
    	public static void main(String[] args) {
	    sc = new Scanner(System.in);
		String message = "000";
		
        Thread ping = new Thread(new Runnable(){
            public void run(){
                //System.out.println("0");
                while(runPing){
                   // System.out.println("1");
                    if(connected){
                        //System.out.println("2");
                        try{
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String pingMessage = in.readLine();
                            if(pingMessage.equals("ping")){
                            out.println("030");
                                System.out.println("ping answered");
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                try{
                out.println("010");
                socket.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println("Client: Socket closed");
            }
        });
        ping.start();


	    try{
            System.out.println("Client: Searching for Socket");
	    	socket = new Socket ("192.168.3.1", 13000);  //ip address and port of the server 
            out = new PrintWriter(socket.getOutputStream(), true);
            connected = true;
	    	while(!message.startsWith("010")){
	    		System.out.println("Enter PWM amount:");
                message.equals("010");
	    		message  = sc.next();
                if(!message.startsWith("010")){
	    		out.println(message);
                }
	    	}
            //ping.interrupt();
            runPing = false;
            connected = false;
            System.out.println("Starting to close the Socket (waiting for last ping)");
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
}
