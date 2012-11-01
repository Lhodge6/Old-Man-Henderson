package old.man.henderson.client;

public class ItemDetails {
	private int id;
	private String name;
	private String details; 
	private int owner;
	public ItemDetails(int id, String name, int owner, String details){
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.details = details;
	}
	public int getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public int getOwnerName(){
		return owner;
	}
	public String getDetails(){
		return details;
	}

}
