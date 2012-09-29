package old.man.henderson.server;
import java.io.*;
import java.net.*;
import java.sql.*;
public class Provider implements Runnable{
	ServerSocket providerSocket;
	ServerSocket negotiatorSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	public Provider(ServerSocket negotiatorSocket, Socket connection){
		this.negotiatorSocket = negotiatorSocket;
		this.connection = connection;
	}
	
	Connection conn = null;
	String url = "jdbc:derby:C:\\Apache\\DataBase;create=True"; //connection url, will be different on a different machine
	String initFileUrl = "C:\\Apache\\init.sql";
	String driver = "org.apache.derby.jdbc.EmbeddedDriver"; // derby drivers, this will be different if we use MySQL or somthing else
	
	public void run()
	{
		try{
			init();
			
			//1. creating a server socket
			providerSocket = new ServerSocket(0, 10);
			
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			//in = new ObjectInputStream(connection.getInputStream());
			
			
			sendMessage("" + getPort());			
			System.out.println("negotiator> New port: " +  getPort());
			negotiatorSocket.close();
			out.close();
			//2. Wait for connection
			System.out.println("Server> Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Server> Connection received from " + connection.getInetAddress().getHostName());
			
			//3. get Input and Output streams
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			
			
			
			
			//4. The two parts communicate via the input and output streams
			do{
				try{
					message = (String)in.readObject();
					System.out.println("client> " + message);
					Class.forName(driver).newInstance();
										
					if(message.contains("select")){
						//connecting to the database
						
						conn = DriverManager.getConnection(url);
						System.out.println("Connected to the database");
						Statement st = conn.createStatement();
																		
						sendMessage(ScriptRunner.runScriptWithResult(conn,new StringReader(message)));//Executing the message against the database, formating the response and returning it all in one line :P
						
						st.close();
						conn.close();
					}else{
						conn = DriverManager.getConnection(url);
						System.out.println("Connected to the database");
						Statement st = conn.createStatement();
						
						sendMessage("" + st.executeUpdate(message));
						
						st.close();
						conn.close();
					}	
					//closing the statement to free up the database if some one else needs to do something				
					
					//basic conditional close, will just reboot the server 
					if(message.equals("bye"))
						sendMessage("bye");
					
					Thread.yield();
				}
				catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}
				catch(Exception e){
					sendMessage(e.toString());
				}
			}while(!message.equals("bye"));
			conn.close();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				providerSocket.close();
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
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	void sendObject(Object o)
	{
		try{
			out.writeObject(o);
			out.flush();
			System.out.println("server>" + o.toString());
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException, IOException{
		conn = DriverManager.getConnection(url);
		System.out.println("Connected to the database");
		Statement st = conn.createStatement();
		Class.forName(driver).newInstance();
		
		ScriptRunner runner = new ScriptRunner(conn,true,false);
		
		runner.runScript(new FileReader(initFileUrl));
		
		st.close();
		conn.close();
	}
	
	public int getPort(){
		return providerSocket.getLocalPort();
	}
}