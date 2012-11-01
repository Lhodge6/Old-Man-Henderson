package old.man.henderson.server;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import old.man.henderson.server.Provider;


public class ConnectionNegotiator{
	ObjectOutputStream out;
	ObjectInputStream in;
	
	static Connection conn = null;
	static String url = "jdbc:derby:C:\\Apache\\DataBase;create=True"; //connection url, will be different on a different machine
	static String initFileUrl = "C:\\Apache\\init.sql";
	static String driver = "org.apache.derby.jdbc.EmbeddedDriver"; // derby drivers, this will be different if we use MySQL or something else
	static int startPort = 5000;
	static int endPort = 6000;
	
	
	
	public static void main(String args[])
	{
		ServerSocket negotiatorSocket;
		Socket connection = null;
		
		init();
				
		long i = 0;
		while(true){
			try {
				negotiatorSocket = new ServerSocket(2008, 10);
				
				System.out.println("negotiator> Waiting for connection at: " + negotiatorSocket.getLocalPort());
				connection = negotiatorSocket.accept();
				System.out.println("negotiator> Connection received from " + connection.getInetAddress().getHostName());
				
				System.out.println("negotiator> Starting new provider interface");
				
				Thread temp = new Thread(new Provider2(negotiatorSocket,connection,i++));
				temp.start();
				Thread.yield();				
				
				if(!negotiatorSocket.isClosed()){
					System.out.println("negotiator> closing socket");
					negotiatorSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	static void init() {
		try {
			conn = DriverManager.getConnection(url);
		
		System.out.println("Connected to the database");
		Statement st = conn.createStatement();
		Class.forName(driver).newInstance();
		
		ScriptRunner runner = new ScriptRunner(conn,true,false);
		
		runner.runScript(new FileReader(initFileUrl));
		for(int i = startPort; i < endPort; i++)
			runner.runScript(new StringReader("INSERT into PORTLIST (PORT) VALUES (" + i +");"));
		
		st.close();
		conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
