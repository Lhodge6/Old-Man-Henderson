package problem18;

public class Main {
	public static void main(String[] args) {		
		int num[] = {            75,
				               95, 64,
				             17, 47, 82,
		   	               18, 35, 87, 10,
			             20, 04, 82, 47, 65,
		               19, 01, 23, 75, 03, 34,
		             88, 02, 77, 73, 07, 63, 67,
	               99, 65, 04, 28, 06, 16, 70, 92,
	             41, 41, 26, 56, 83, 40, 80, 70, 33,
               41, 48, 72, 33, 47, 32, 37, 16, 94, 29,
             53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14,
           70, 11, 33, 28, 77, 73, 17, 78, 39, 68, 17, 57,
		 91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48,
	   63, 66, 04, 68, 89, 53, 67, 30, 73, 16, 69, 87, 40, 31,
	 04, 62, 98, 27, 23, 9, 70, 98, 73, 93, 38, 53, 60, 04, 23};
		BinaryTree tree[] = new BinaryTree[num.length];
		
		for(int i = 0; i<num.length; i++){
			tree[i] = new BinaryTree(i,num[i]);
			if(i > 0)
				if(tree[i].row == tree[i-1].row){
					tree[i].leftSibling = tree[i-1];
					tree[i-1].rightSibling = tree[i];
				}
		}
		
		for(int i = 0; i < tree.length; i++){
			if(tree[i].calcLeft(i) < tree.length){
				tree[i].leftChild = tree[tree[i].calcLeft(i)];
				tree[i].leftChild.rightParent = tree[i];
			}
			if(tree[i].calcRight(i) < tree.length){
				tree[i].rightChild = tree[tree[i].calcRight(i)];
				tree[i].rightChild.leftParent = tree[i];
			}			
		}
		tree[0].calcTreeValue();
		//System.out.println(tree[0]);
		for(BinaryTree bt: tree)
			System.out.println("index: " + bt.index + " Value: " + bt.data + " left child: " + (bt.leftChild != null ? bt.leftChild.index : "null") + ", " + (bt.leftChild != null ? bt.leftChild.data : "null") + " right child: " + (bt.rightChild != null ? bt.rightChild.index : "null") + ", " + (bt.rightChild != null ? bt.rightChild.data : "null") + " tree Value: " + bt.treeValue);
		System.out.println("*************************************************");
		BinaryTree path[] = tree[0].findBestPath();
		int sum = 0;
		for(BinaryTree bt: path){
			System.out.println("index: " + bt.index + " Value: " + bt.data + " left child: " + (bt.leftChild != null ? bt.leftChild.index : "null") + ", " + (bt.leftChild != null ? bt.leftChild.data : "null") + " right child: " + (bt.rightChild != null ? bt.rightChild.index : "null") + ", " + (bt.rightChild != null ? bt.rightChild.data : "null") + " tree Value: " + bt.treeValue);
			sum += bt.data;
		}
		System.out.println("Sum: " + sum);
	
	}
}
