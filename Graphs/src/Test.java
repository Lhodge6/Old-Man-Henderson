//==============================================================
//    MST Program – Kruskal’s / Prim’s
//==============================================================
// Leon Hodges-Austin
// cosc 336
// 5/14/2012
//-----------------------------------------------------------------------------------------------------

import java.util.LinkedList;
import java.util.Random;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GraphNode a = new GraphNode(new Key(0,'a'));
		GraphNode b = new GraphNode(new Key(1,'b'));
		GraphNode c = new GraphNode(new Key(2,'c'));
		GraphNode d = new GraphNode(new Key(3,'d'));
		GraphNode e = new GraphNode(new Key(4,'e'));
		GraphNode f = new GraphNode(new Key(5,'f'));
		GraphNode g = new GraphNode(new Key(6,'g'));
		
		a.addNodeUndirected(b, 1);
		a.addNodeUndirected(c, 4);
		b.addNodeUndirected(c, 2);
		b.addNodeUndirected(d, 3);
		b.addNodeUndirected(e, 10);
		c.addNodeUndirected(d, 6);
		c.addNodeUndirected(g, 3);
		d.addNodeUndirected(e, 5);
		d.addNodeUndirected(g, 1);
		e.addNodeUndirected(f, 7);
		e.addNodeUndirected(g, 2);
		g.addNodeUndirected(f, 5);
		
		System.out.println("*************************");
		System.out.println(a.printTree());
		System.out.println("*************************");
		
		//GraphNode p = Prim.prim(a);
		
		//System.out.println(p.printTree());
		
		LinkedList<GraphNode> list = Kruskal.kruskal(a);
		System.out.println(list.peek().printTree());
		

	}

}
