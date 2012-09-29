package old.man.henderson.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import old.man.henderson.server.Provider;


public class ConnectionNegotiator{
	ObjectOutputStream out;
	ObjectInputStream in;	
	
	
	
	public static void main(String args[])
	{
		ServerSocket negotiatorSocket;
		Socket connection = null;
		LinkedList<Thread> connectionList = new LinkedList<Thread>();
		while(true){
			try {
				negotiatorSocket = new ServerSocket(2005, 10);			
				
				System.out.println("negotiator> Waiting for connection");
				connection = negotiatorSocket.accept();
				System.out.println("negotiator> Connection received from " + connection.getInetAddress().getHostName());
				
				System.out.println("negotiator> Starting new provider interface");
				
				Provider temp = new Provider(negotiatorSocket,connection);
				//temp.run();			
				
				connectionList.add(new Thread(temp));
				connectionList.get(connectionList.size()-1).start();
				System.out.println("negotiator> closing socket");
				negotiatorSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
