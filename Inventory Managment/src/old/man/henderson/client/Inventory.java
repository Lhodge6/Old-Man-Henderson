package old.man.henderson.client;

public class Inventory {
	private int id;
	private String name;
	private String owner;
	public Inventory(int id, String name, String owner){
		this.id = id;
		this.name = name;
		this.owner = owner;
	}
	public int getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public String getOwnerName(){
		return owner;
	}
}
