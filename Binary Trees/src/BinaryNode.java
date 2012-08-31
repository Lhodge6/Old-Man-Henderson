
public class BinaryNode<T> {
	public BinaryNode<?> left, right, parent;
	public Key data;
	
	public BinaryNode(){
		left = null;
		right = null;
		data = null;
		parent = null;
	}
	
	public BinaryNode(BinaryNode<?> n){
		left = null;
		right = null;
		data = null;
		parent = n;
	}
	
	public BinaryNode(Key o){
		left = null;
		right = null;
		parent = null;
		data = o;
	}
	
	public BinaryNode(Key o, BinaryNode<?> n){
		left = null;
		right = null;
		parent = n;
		data = o;
	}
	
	@SuppressWarnings("unchecked")
	public void insert(Key o){
		if((data).compareTo(o) <= 0)
			if(left != null)
				left.insert(o);
			else
				left = new BinaryNode<Object>(o, this);
		else
			if(right != null)
				right.insert(o);
			else
				right = new BinaryNode<Object>(o, this);
	}
	
	public int getSize(){
		int size = 1;
		if(left != null)
			size += left.getSize();
		if(right != null)
			size+=right.getSize();
		return size;
	}
	
	public int getDepth(){
		int depth = 1, leftD = 0, rightD = 0;
		if(left !=null)
			leftD = left.getDepth();
		if(right != null)
			rightD = right.getDepth();
		if(leftD >= rightD)
			depth += leftD;
		else
			depth += rightD;
		return depth;
	}
	
	public void balance(){
		if(left!=null)
			while(!left.isBalanced()){
				if(left.left.getDepth()>left.right.getDepth())
					left.rotateRight();
				else
					left.rotateLeft();
			}		
		if(right!=null)
			while(!right.isBalanced()){
				if(right.left.getDepth()>right.right.getDepth())
					left.rotateRight();
				else
					left.rotateLeft();
			}		
	}
	
	public boolean isBalanced(){
		int leftD = 0, rightD = 0;
		if(left!=null){
			left.balance();
			leftD = left.getDepth();
		}
		if(right!=null){
			right.balance();
			rightD = right.getDepth();
		}
		return Math.abs(leftD-rightD) >= 2;
	}
	
	public void rotateRight(){
		right.parent = this.parent;
		this.parent = right;
		this.right = right.left;
		parent.left = this;
		if(parent.parent.left == this)
			parent.parent.left = this.parent;
		else
			parent.parent.right = this.parent;
	}
	
	public void rotateLeft(){
		left.parent = this.parent;
		this.parent = left;
		this.left = left.right;
		parent.right = this;
		if(parent.parent.left == this)
			parent.parent.left = this.parent;
		else
			parent.parent.right = this.parent;
	}
	
	public String toString(){
		return "Key: " + data.key + " R: " + right + " L: " + left;
	}
}
