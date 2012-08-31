import java.util.LinkedList;
import java.util.concurrent.PriorityBlockingQueue;


public class Kruskal {
	public static LinkedList<GraphNode> kruskal (GraphNode G) {
		Edge compare = new Edge();
		PriorityBlockingQueue<Edge> edges = new PriorityBlockingQueue<Edge>(11, compare);
		edges.addAll(G.getEdges());
		LinkedList<GraphNode> nodes = new LinkedList<GraphNode>();
		GraphNode solution;
		int totalNodes = G.getLength();
		
		Edge temp;
		while(nodes.size() < totalNodes){
			temp = edges.poll();
			if(contains(temp.source,nodes)){
				GraphNode t = get(temp.source,nodes);
				t.addNode(new GraphNode(temp.vertex.getData()));
				nodes.add(t);
			}else if(contains(temp.vertex,nodes)){
				GraphNode t = new GraphNode(temp.source.getData());
				t.addNode(get(temp.vertex,nodes));
				nodes.add(t);
			}else{
				nodes.add(new GraphNode(temp.source.getData()));
				nodes.peekLast().addNode(temp.vertex, temp.weight);
			}
		}
		
		
		return nodes;
	}
	
	public static boolean contains(GraphNode g, LinkedList<GraphNode> nodes){
		boolean contains = false;
		for(GraphNode n: nodes)
			if(n.getData() == g.getData())
				contains = true;
		return contains;
	}
	
	public static GraphNode get(GraphNode g, LinkedList<GraphNode> nodes){
		GraphNode temp = new GraphNode();
		for(GraphNode n: nodes)
			if(n.getData() == g.getData())
				temp = nodes.get(nodes.indexOf(n));
		return temp;
	}

}
