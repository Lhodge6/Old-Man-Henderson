package old.man.henderson.client;

public class BasicUser extends User {
	private boolean verified;
	private int verificationNumber;
	public BasicUser(String userName, String password, String email) {
		super(userName, password, email);
		// TODO Auto-generated constructor stub
	}
	
	public BasicUser(String userName, String password, String email, boolean verified, int verificationNumber) {
		super(userName, password, email);
		this.verified = verified;
		this.verificationNumber = verificationNumber;
		// TODO Auto-generated constructor stub
	}
	
	public boolean isVerified(){
		return verified;
	}
	
	public int verificationNumber(){
		return verificationNumber;
	}

}
