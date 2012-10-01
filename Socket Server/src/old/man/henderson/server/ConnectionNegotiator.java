package old.man.henderson.server;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	static String driver = "org.apache.derby.jdbc.EmbeddedDriver"; // derby drivers, this will be different if we use MySQL or somthing else
	
	
	
	public static void main(String args[])
	{
		ServerSocket negotiatorSocket;
		Socket connection = null;
		try {
			init();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		long i = 0;
		while(true){
			try {
				negotiatorSocket = new ServerSocket(2008, 10);			
				
				System.out.println("negotiator> Waiting for connection");
				connection = negotiatorSocket.accept();
				System.out.println("negotiator> Connection received from " + connection.getInetAddress().getHostName());
				
				System.out.println("negotiator> Starting new provider interface");
				
				Thread temp = new Thread(new Provider(negotiatorSocket,connection,i++));
				Thread.sleep(500L);
				temp.start();
				
				System.out.println("negotiator> closing socket");
				negotiatorSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
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
	static void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException, IOException{
		conn = DriverManager.getConnection(url);
		System.out.println("Connected to the database");
		Statement st = conn.createStatement();
		Class.forName(driver).newInstance();
		
		ScriptRunner runner = new ScriptRunner(conn,true,false);
		
		runner.runScript(new FileReader(initFileUrl));
		
		st.close();
		conn.close();
	}
}
