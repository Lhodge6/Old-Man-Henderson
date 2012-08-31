
public class BinaryTree {
	private BinaryNode<?> root;
	
	public BinaryTree(){
		root = null;
	}
	
	public void insert(Key o){
		if(root == null){
			root = new BinaryNode(o);
		}else {
			root.insert(o);
		}
	}
	
	public void balance(){
		root.balance();
	}
	
	public String toString(){
		return root.toString();
	}
}
