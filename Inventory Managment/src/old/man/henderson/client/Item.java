package old.man.henderson.client;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class Item {
	private int id;
	private String name;
	private String owner;
	private int locationId;
	private String description;
	private int quantity;
	private boolean searchable;
	private boolean tradeable;
	private Date perishDate;
	private Time period;
	
	public Item(int id, String name, String owner, int locationId, String description, int quantity, 
				boolean searchable, boolean tradeable, Date perishDate, Time period){
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.locationId = locationId;
		this.description = description;
		this.quantity = quantity;
		this.searchable = searchable;
		this.tradeable = tradeable;
		this.perishDate = perishDate;
		this.period = period;
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
	public int getLocationId(){
		return locationId;
	}
	public String getDescription(){
		return description;
	}
	public int getQuantity(){
		return quantity;
	}
	public boolean isSearchable(){
		return searchable;
	}
	public boolean isTradeable(){
		return tradeable;
	}
	public Date perishDate(){
		return perishDate;
	}
	public Time period(){
		return period;
	}
	public boolean hasPerished(){
		return Calendar.getInstance().after(perishDate);
	}
}
