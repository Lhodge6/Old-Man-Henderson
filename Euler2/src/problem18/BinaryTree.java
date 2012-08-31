package problem18;

public class BinaryTree {
	BinaryTree leftChild, rightChild, leftParent, rightParent, leftSibling, rightSibling;
	int data, index, row, treeValue;
	
	public BinaryTree(){
		leftChild = null;
		rightChild = null;
		data = -1;
		index = -1;
		row = -1;		
	}
	
	public BinaryTree(int pos, int num){
		leftChild = null;
		rightChild = null;
		data = num;
		index = pos;
		calcRow();
	}
	
	public int calcLeft(int i){
		return row + i;
	}
	
	public int calcRight(int i){
		return row + i + 1;
	}
	
	private void calcRow() {
		int rowTemp = 0;
		while(ArethmaticSum.sum(rowTemp + 1) <= index)
			rowTemp++;		
		row = rowTemp;
	}
	
	public int calcTreeValue(){
		int leftValue = 0, rightValue = 0;
		if(leftChild != null)
			leftValue = leftChild.calcTreeValue();
		if(rightChild != null)
			rightValue = rightChild.calcTreeValue();
		treeValue = leftValue + rightValue + data;
		return treeValue;
	}
	
	public BinaryTree[] findBestPath(){
		BinaryTree path[];
		BinaryTree temp[] = null;
		if(leftChild != null){
			if(rightChild != null){
				if(rightChild.treeValue > leftChild.treeValue) 
					temp = rightChild.findBestPath();
				else
					temp = leftChild.findBestPath();
			}
		}else{
			path = new BinaryTree[1];
			path[0] = this;
			return path;
		}
			
		path = new BinaryTree[temp.length+1];
		path[0] = this;
		for(int i = 0; i < temp.length; i++)
			path[i+1] = temp[i];
		return path;					
	}
	
	public String toString(){
		return data + "\n" + leftChild + ", " + rightChild;		
	}
}
