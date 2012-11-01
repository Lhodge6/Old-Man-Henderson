package old.man.henderson.client;

public class BusinessUser extends BasicUser {
	private String affiliatedWith;
	public BusinessUser(String userName, String password, String email,
			boolean verified, int verificationNumber) {
		super(userName, password, email, verified, verificationNumber);
		// TODO Auto-generated constructor stub
	}
	public BusinessUser(String userName, String password, String email,
			boolean verified, int verificationNumber, String affiliatedWith) {
		super(userName, password, email, verified, verificationNumber);
		this.affiliatedWith = affiliatedWith;
		// TODO Auto-generated constructor stub
	}
	public String getEmployer(){
		return affiliatedWith;
	}
	
}
