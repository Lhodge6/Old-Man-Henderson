import java.util.Comparator;


public class Edge implements Comparator<Edge> {
	long weight;
	GraphNode vertex, source;
	
	public Edge(){
		weight = 1;
		vertex = null;
		source = null;
	}
	
	public Edge(GraphNode n){
		weight = 1;
		vertex = n;
		vertex.addEdge();
		source = null;
	}
	
	public Edge(GraphNode n, long w){
		weight = w;
		vertex = n;
		vertex.addEdge();
		source = null;
	}
	
	public Edge(GraphNode n1, GraphNode n2){
		weight = 1;
		vertex = n1;
		vertex.addEdge();
		source = n2;
	}
	
	public Edge(GraphNode n1, GraphNode n2, long w){
		weight = w;
		vertex = n1;
		vertex.addEdge();
		source = n2;
	}
	
	public long getWeight(){
		return weight;
	}
	
	public String toString(){
		return "" + vertex + " with Weight: " + weight;
	}
	
	public int compare(Edge e1, Edge e2) {
		return (int) (e1.weight-e2.weight);
	}
	
	public int compare(Edge e) {
		return (int) (weight-e.weight);
	}

	public int compare(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
}