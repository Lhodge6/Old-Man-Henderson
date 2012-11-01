package old.man.henderson.test;

import old.man.henderson.client.RequesterSQLBuilder;

public class SQLbuilderTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Login sucess check: " + (RequesterSQLBuilder.login("mjholtzem", "password123") == 1));
		System.out.println("Login fail check: " + (RequesterSQLBuilder.login("mjholtzem", "password1234") == 0));
		System.out.println("Get Admin information check: " + (RequesterSQLBuilder.login("mjholtzem", "password1234") == 0));

	}

}
