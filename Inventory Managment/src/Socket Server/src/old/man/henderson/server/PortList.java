package old.man.henderson.server;

public class PortList {
	private int[] ports;
	private int size;
	
	public PortList(){
		setPorts(new int[0]);
		setSize(0);
	}
	
	public PortList(int first, int range){
		setPorts(init(first,range));
		setSize(range);
	}
	
	private int[] init(int first, int range){
		int[] temp = new int[range];
		
		for(int i = 0; i < range; i++)
			temp[i] = i + range;			
			
		return temp;
	}

	public int[] getPorts() {
		return ports;
	}

	public void setPorts(int[] ports) {
		this.ports = ports;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public int pop(){
		int temp = ports[size-1];
		size--;		
		return temp;
	}
	
	public void add(int num){
		if(ports.length <= size)
			doubleSize();
		ports[size] = num;
		size++;			
	}
	
	private void doubleSize(){
		int[] temp = new int[ports.length*2];
		for(int i = 0; i < ports.length; i++)
			temp[i] = ports[i];
		ports = temp;
	}
	

}
