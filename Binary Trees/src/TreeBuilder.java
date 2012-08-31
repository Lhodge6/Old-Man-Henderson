import java.util.Random;


public class TreeBuilder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int size = 10;
		BinaryTree tree = new BinaryTree();
		
		for(int i = 0; i < size; i++){
			tree.insert(new Key((new Random().nextLong()) % 100));
		}
		
		System.out.print(tree);

	}

}
