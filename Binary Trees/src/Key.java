import java.util.Random;


public class Key implements Comparable<Object> {
	Object data;
	long key;
	
	public Key(){
		data = null;
		key = new Random().nextLong();
	}
	
	public Key(long i){
		data = i;
		key = i;
	}
	
	public Key(long i, Object o){
		data = o;
		key = i;
	}
	
	public void setData(Object o){
		data = o;
	}
	
	public void setKey(long i){
		key = i;
	}
	
	public long getKey(){
		return key;
	}

	public int compareTo(Key o) {
		
		return (int) (key-o.key);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString(){
		return "" + data;
	}

}
