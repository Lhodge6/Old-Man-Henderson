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
	long socketId;
	public Provider(ServerSocket negotiatorSocket, Socket connection, long id){
		this.negotiatorSocket = negotiatorSocket;
		this.connection = connection;
		this.socketId = id;
	}
	
	Connection conn = null;
	String url = "jdbc:derby:C:\\Apache\\DataBase;create=True"; //connection url, will be different on a different machine
	String initFileUrl = "C:\\Apache\\init.sql";
	String driver = "org.apache.derby.jdbc.EmbeddedDriver"; // derby drivers, this will be different if we use MySQL or somthing else
	
	public void run()
	{
		System.out.println("ENTERING PROVDIER");
		
		try{						
			System.out.println("check 1");
			//1. creating a server socket
			providerSocket = new ServerSocket(getNewPort(), 10);
			System.out.println("check 2");
			out = new ObjectOutputStream(connection.getOutputStream());
			System.out.println("check 3");
			out.flush();
			System.out.println("check 4");
			//in = new ObjectInputStream(connection.getInputStream());
						
			sendMessage("" + getPort());			
			System.out.println("check 5");
			System.out.println("negotiator> New port: " +  getPort());
			//negotiatorSocket.close();
			out.close();
			//2. Wait for connection
			System.out.println("Server " + socketId + "> Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Server " + socketId + "> Connection received from " + connection.getInetAddress().getHostName());
			
			//3. get Input and Output streams
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			
			
			
			
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
				returnPort(getPort());
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
	void sendObject(Object o)
	{
		try{
			out.writeObject(o);
			out.flush();
			System.out.println("server " + socketId+ " on port " + getPort() + ">" + o.toString());
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}	
	
	public int getPort(){
		return providerSocket.getLocalPort();
	}
	
	public int getNewPort() throws SQLException, IOException{
		String results = null;	
		conn = DriverManager.getConnection(url);	
		Statement st = null;		
		st = conn.createStatement();		
		
		results = ScriptRunner.runScriptWithResult(conn,new StringReader("select * from PORTLIST;"));		
		
		String temp =  results.split("\n")[2];
		System.out.println("subcheck 5: " + temp.trim());
		int newPort = Integer.parseInt(temp.trim());
		System.out.println("subcheck 6 new port = " + newPort);
		
		System.out.println("subcheck 7");
		results = ScriptRunner.runScriptWithOutResult(conn, new StringReader("delete from PORTLIST where PORT = " + newPort + ";"));		
		System.out.println("subcheck 8: " + results);
		
		
				
		st.close();
		
		
		conn.close();
		
		return newPort;
	}
	
	public void returnPort(int port){
		try {
			ScriptRunner.runScriptWithResult(conn,new StringReader("INSERT into PORTLIST (PORT) VALUES (" + port +");"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}