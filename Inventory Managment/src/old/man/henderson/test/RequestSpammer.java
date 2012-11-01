package old.man.henderson.test;

import old.man.henderson.client.Requester;

public class RequestSpammer implements Runnable{
	int id;
	public RequestSpammer(int id){
		this.id = id;		
	}
	public void run()
	{
		while(true){
			try {
				System.out.println("**********>>>>>>>>>>   " + id + "   <<<<<<<<<<**********");
				System.out.println("server> " + Requester.sendMessage("10.55.18.29", 2008, "select * from USERS;", System.currentTimeMillis(), 30000));	
				//Requester.sendMessage("192.168.1.30", 2008, "select * from USERS;");
				Thread.sleep((long) (Math.random() * 10000l));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String args[])
	{
		int i = 0;
		while(i<500){
			RequestSpammer client = new RequestSpammer(i++);
			Thread temp = new Thread(client);
			temp.start();
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}