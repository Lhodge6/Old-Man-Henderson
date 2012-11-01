package old.man.henderson.client;
import java.io.*;
import java.net.*;
public class Requester{
	public static String sendMessage(String ip, int port, String msg)
	{
		Socket requestSocket;
		ObjectOutputStream out;
	 	ObjectInputStream in;
	 	String message = "";
		try{
			requestSocket = new Socket(ip, port);
			//requestSocket = new Socket("192.168.1.30", 2008);
			//requestSocket = new Socket("174.147.183.199", 2008);			
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			//System.out.println("Connected to " + requestSocket.getInetAddress().getHostName() + " in port " + requestSocket.getPort());
			out.writeObject(msg);
			out.flush();
			//System.out.println("client> " + msg);
			message = (String)in.readObject();
			//System.out.println("server> " + message);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
}