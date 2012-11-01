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

public class Sever{
	static ObjectOutputStream out;
	static ObjectInputStream in;
	static String message;
	
	static Connection conn = null;
	static String url = "jdbc:derby:C:\\Apache\\DataBase;create=True"; //connection url, will be different on a different machine
	static String initFileUrl = "C:\\Apache\\init.sql";
	static String driver = "org.apache.derby.jdbc.EmbeddedDriver"; // derby drivers, this will be different if we use MySQL or something else
	
	
	
	public static void main(String args[])
	{
		ServerSocket serverSocket = null;
		Socket connection = null;
		
		init();
				
		while(true){
			try {
				serverSocket = new ServerSocket(2008, 10);
				
				//System.out.println("server > Waiting for connection at: " + serverSocket.getLocalPort());
				connection = serverSocket.accept();
				//System.out.println("server > Connection received from " + connection.getInetAddress().getHostName());
				out = new ObjectOutputStream(connection.getOutputStream());
				out.flush();
				in = new ObjectInputStream(connection.getInputStream());
				message = (String)in.readObject();
				//System.out.println("client > " + message);
				Class.forName(driver).newInstance();				
				if(message.contains("select")){
					//connecting to the database
					conn = DriverManager.getConnection(url);
					//System.out.println("Connected to the database");
					Statement st = conn.createStatement();
					
					sendMessage(ScriptRunner.runScriptWithResult(conn,new StringReader(message)));//Executing the message against the database, formating the response and returning it all in one line :P

					st.close();
					conn.close();
					serverSocket.close();
				}else{
					conn = DriverManager.getConnection(url);
					//System.out.println("Connected to the database");
					Statement st = conn.createStatement();
					
					sendMessage("" + st.executeUpdate(message));
					
					
					st.close();
					conn.close();
					serverSocket.close();
				}		
				if(!serverSocket.isClosed())
					serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					if(!serverSocket.isClosed())
						serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	static void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			//System.out.println("server >" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
