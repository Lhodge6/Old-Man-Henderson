package old.man.henderson.client;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Client implements Runnable{
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
	Client(){}
	Scanner sc = new Scanner(System.in);
	public void run()
	{	
		connectToServer();		
		
		//3: Communicating with the server
		try{
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			message = (String)in.readObject();
			System.out.println("server>" + message);
			sendMessage("select * from USERS;");
			Thread.sleep((long) (Math.random() * 5000l));
		}
		catch(ClassNotFoundException classNot){
			System.err.println("data received in unknown format");
		} 
		catch (InterruptedException e) {			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
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
	void connectToServer(){
		try {
			requestSocket = new Socket("192.168.1.30", 2008);
			//requestSocket = new Socket("174.147.183.199", 2008);
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
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Connected to " + requestSocket.getInetAddress().getHostName() + " in port " + requestSocket.getPort());
	}
	public static void main(String args[])
	{
		while(true){
			Client client = new Client();
			Thread temp = new Thread(client);
			temp.start();
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}