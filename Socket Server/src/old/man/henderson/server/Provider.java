package old.man.henderson.server;
import java.io.*;
import java.net.*;
import java.sql.*;
public class Provider{
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	Provider(){}
	
	Connection conn = null;
	String url = "jdbc:derby:C:\\User\\Account"; //connection url, will be different on a different machine
	String driver = "org.apache.derby.jdbc.EmbeddedDriver"; // derby drivers, this will be different if we use MySQL or somthing else
	
	void run()
	{
		try{
			//connecting to the database
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url);
			System.out.println("Connected to the database");
			
			//1. creating a server socket
			providerSocket = new ServerSocket(2004, 10);
			//new Socket(new InetAddress )
			//2. Wait for connection
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			
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
					
					Statement st = conn.createStatement();					
					if(message.contains("select"))
						sendMessage(constructResponse(st.executeQuery(message)));//Executing the message against the database, formating the response and returning it all in one line :P
					else
						sendMessage("" + st.executeUpdate(message));						
						
					//closing the statement to free up the database if some one else needs to do something				
					st.close();
					//basic conditional close, will just reboot the server 
					if(message.equals("bye"))
						sendMessage("bye");
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
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	String constructResponse(ResultSet rs){
		String response = "\n";
		
		try {
			int colCount = rs.getMetaData().getColumnCount(); // gets the number of columns so that we know how may to iterate over before we go the the next row
			
			//iterates over the coloumn names and adds them to the response
			for(int i = 1; i <= colCount; i++)
				response = response + rs.getMetaData().getColumnName(i) + " \t ";			
			response = response + "\n";
			
			//iterates over each column and adds the field data to the response, at the end o the row it iterates the row
			while(rs.next()){
				for(int i = 1; i <= colCount; i++)
					response = response + rs.getString(i) + " \t ";
				response = response + "\n";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return response;
	}
	public static void main(String args[])
	{
		Provider server = new Provider();
		while(true){
			server.run();
		}
	}
}