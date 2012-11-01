package old.man.henderson.server;
import java.io.*;
import java.net.*;
import java.sql.*;
public class Provider2 implements Runnable{
	ServerSocket providerSocket;
	ServerSocket negotiatorSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	long socketId;
	public Provider2(ServerSocket providerSocket, Socket connection, long id){
		this.providerSocket = providerSocket;
		this.connection = connection;
		this.socketId = id;
	}
	
	Connection conn = null;
	String url = "jdbc:derby:C:\\Apache\\DataBase;create=True"; //connection url, will be different on a different machine
	String initFileUrl = "C:\\Apache\\init.sql";
	String driver = "org.apache.derby.jdbc.EmbeddedDriver"; // derby drivers, this will be different if we use MySQL or somthing else
	
	public void run()
	{
		try{				
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());	
			
			//4. The two parts communicate via the input and output streams
			do{
				try{
					message = (String)in.readObject();
					System.out.println("client " + socketId + "> " + message);
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
					Thread.yield();
					//basic conditional close, will just reboot the server 
					if(message.equals("bye"))
						sendMessage("bye");
					
					Thread.yield();
				}
				catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}
				catch(Exception e){
					System.err.println(e);
					sendMessage(e.toString());
				}
			}while(!message.equals("bye"));
			conn.close();
		}
		catch(IOException ioException){
			System.err.println(ioException);
			ioException.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
			e.printStackTrace();
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
			System.out.println("server " + socketId+ " on port " + getPort() + ">" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public int getPort(){
		return providerSocket.getLocalPort();
	}
}