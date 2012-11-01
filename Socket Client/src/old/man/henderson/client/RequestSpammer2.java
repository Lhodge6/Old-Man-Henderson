package old.man.henderson.client;
public class RequestSpammer2 implements Runnable{
	int id;
	public RequestSpammer2(int id){
		this.id = id;		
	}
	public void run()
	{
		while(true){
			try {
				System.out.println("**********>>>>>>>>>>   " + id + "   <<<<<<<<<<**********");
				//System.out.println("server> " + Requester.sendMessage("192.168.1.30", 2008, "select * from USERS;"));	
				Requester.sendMessage("192.168.1.30", 2008, "select * from USERS;");
				Thread.sleep((long) (Math.random() * 5000l));
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
			RequestSpammer2 client = new RequestSpammer2(i++);
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