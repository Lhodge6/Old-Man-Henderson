import java.util.LinkedList;
import java.util.concurrent.PriorityBlockingQueue;

public class Prim {
	public static GraphNode prim (GraphNode G) {
		GraphNode u = new GraphNode(G.getData());
		LinkedList<GraphNode> visted = new LinkedList<GraphNode>();
		Edge compare = new Edge();
		PriorityBlockingQueue<Edge> queued = new PriorityBlockingQueue<Edge>(11,compare);
		visted.add(G);
		
		do{
			for(GraphNode g : visted)
				for(Edge e : g.getAdj()){
					queued.add(e);
				}
			GraphNode temp = new GraphNode(queued.peek().vertex.getData());
			GraphNode tempSource = u;
			boolean shift = false;
			do{
				if(queued.peek().source.getData() == u.getData()){
					tempSource.addNode(temp,queued.peek().weight);
					visted.add(queued.poll().vertex);
					cleanUp(visted, queued);
					shift = true;
					System.out.println("Check1: " + queued.peek());
				}else{
					if(!tempSource.getAdj().isEmpty())
						tempSource = tempSource.getAdj().peek().vertex;
				}
			}while(!shift && !tempSource.getAdj().isEmpty());
			System.out.println("Check2: " + queued.peek());
		}while(queued.size() > 0);
		System.out.println("Check3: ");
		
		return u;
    }
	private static void cleanUp(LinkedList<GraphNode> visted, PriorityBlockingQueue<Edge> queued){
		for(GraphNode g : visted)
			for(Edge e : queued)
				if(e.vertex == g)
					queued.remove(e);
	}
}