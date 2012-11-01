package old.man.henderson.client;

import java.sql.Time;
import java.util.Date;

public class RequesterSQLBuilder {
	private static String IP = "10.55.18.29";
	private static int PORT = 2008;
	private static long TIMEOUT = 30000;
	
	public static int login(String userName, String password){
		if(Integer.parseInt(     select("select count(USERNAME) as ct from USERS " +
		 						   		"where USERNAME = '" + userName + "' and PASSWORD = '" + password + "';").split("\n")[2].trim()) == 1)
			return 1;
		else if(Integer.parseInt(select("select count(USERNAME) as ct from ADMINS " +
				   						"where USERNAME = '" + userName + "' and PASSWORD = '" + password + "';").split("\n")[2].trim()) == 1)
			return 2;
		else
			return 0;
	}
	public static Admin getAdmin(String userName, String password){
		String data[] = selectFirstRow("select * from ADMINS " +
				 					"where USERNAME = '" + userName + "' and PASSWORD = '" + password + "';");
		
		return new Admin(data[0],data[1],data[2]);
	}
	public static BasicUser getUser(String userName, String password){
		String data[] = selectFirstRow("select * from USERS " +
				 					"where USERNAME = '" + userName + "' and PASSWORD = '" + password + "';");
		
		return new BasicUser(data[0],data[1],data[2],Boolean.parseBoolean(data[3]),Integer.parseInt(data[4]));
	}
	public static BusinessUser getBusinessUser(String userName, String password){
		String data[] = selectFirstRow("select * from USERS " +
				 					"where USERNAME = '" + userName + "' and PASSWORD = '" + password + "';");
		
		return new BusinessUser(data[0],data[1],data[2],Boolean.parseBoolean(data[3]),Integer.parseInt(data[4]), data[5]);
	}
	public static Inventory[] getUserInventorys(String userName){
		String data[] = select("select * from INVENTORIES " +
				   					"where OWNERNAME = '" + userName + "';").split("\n");
		
		Inventory[] inventoryList = new Inventory[data.length-2];
		
		for(int i = 2; i < data.length; i++){
			String[] temp = data[i].split(" ");
			inventoryList[i-2] = new Inventory(Integer.parseInt(temp[0]),temp[1],temp[2]);
		}
			
		
		return inventoryList;
	}
	@SuppressWarnings("deprecation")
	public static Item[] getInventoryItems(int inventoryId){
		String data[] = select("select * from ITEMS " +
								  "where LOCID = '" + inventoryId + "';").split("\n");
		
		Item[] itemList = new Item[data.length-1];

		for(int i = 2; i < data.length; i++){
			String[] temp = data[i].split(" ");
			itemList[i-2] = new Item(Integer.parseInt(temp[0]),temp[1],temp[2],Integer.parseInt(temp[3]),temp[4],Integer.parseInt(temp[5]),
					Boolean.parseBoolean(temp[6]),Boolean.parseBoolean(temp[7]),(java.sql.Date) new Date(Long.parseLong(temp[8])), 
					new Time(Time.parse(temp[9])));
		}

		return itemList;
	}
	public static ItemDetails[] getItemDetails(int itemId){
		String data[] = select("select * from CUSTOMFIELDS " +
				   					"where PARENTITEMID = '" + itemId + "';").split("\n");
		
		ItemDetails[] itemDetailList = new ItemDetails[data.length-1];
		
		for(int i = 2; i < data.length; i++){
			String[] temp = data[i].split(" ");
			itemDetailList[i-2] = new ItemDetails(Integer.parseInt(temp[0]),temp[1],Integer.parseInt(temp[2]),temp[3]);
		}
			
		
		return itemDetailList;
	}
	
	public static String select(String msg){
		return Requester.sendMessage(IP, PORT, msg, System.currentTimeMillis(), TIMEOUT);
	}
	public static String[] selectFirstRow(String msg){
		String response = select(msg);
		return response.split("\n")[2].trim().split(" ");		
	}
}
