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
			System.out.println("Connected to " + requestSocket.getInetAddress().getHostName() + " in port " + requestSocket.getPort());
			
			//2. get Input stream			
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			int newPort = Integer.parseInt(in.readObject().toString());
			System.out.println("New Port number : " + newPort);
			System.out.println("Connecting...");
			requestSocket.close();
			
			//3. get server port from negotiator
			requestSocket = new Socket("192.168.1.30", newPort);
			System.out.println("Connected to " + requestSocket.getInetAddress().getHostName() + " in port " + requestSocket.getPort());
			
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
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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