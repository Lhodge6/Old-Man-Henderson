package old.man.henderson.client;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Requester{
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
	Requester(){}
	Scanner sc = new Scanner(System.in);
	void run()
	{
		try{
			//1. creating a socket to connect to the server
			requestSocket = new Socket("192.168.1.30", 2005);
			System.out.println("Connected to localhost in port 2004");
			
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			//3: Communicating with the server
			do{
				try{
					message = (String)in.readObject();
					System.out.println("server>" + message);
					
					//this takes sql queries, will crash if not absolutely perfect, should reboot...
					sendMessage(sc.nextLine());
				}
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}
			}while(!message.equals("bye"));
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}	
	public static void main(String args[])
	{
		Requester client = new Requester();
		while(true){
			client.run();
		}
	}
}