import java.util.LinkedList;
import java.util.PriorityQueue;


public class GraphNode {
	private long inDegree;
	private long outDegree;
	private LinkedList<Edge> adj;
	private Key data;
	
	public GraphNode(){
		inDegree = 0;
		outDegree = 0;
		adj = new LinkedList<Edge>();
		data = new Key();
	}
	
	public GraphNode(Key k){
		inDegree = 0;
		outDegree = 0;
		adj = new LinkedList<Edge>();
		data = k;
	}
	
	public void addEdge(){
		inDegree++;
	}
	
	public void removeEdge(){
		inDegree--;
	}
	
	public void addNode(GraphNode n){
		adj.add(new Edge(n,this));
		outDegree++;		
	}
	
	public void addNode(GraphNode n, long w){
		adj.add(new Edge(n,this,w));
		outDegree++;		
	}
	
	public void addNodeUndirected(GraphNode n){
		n.addNode(this);
		adj.add(new Edge(n,this));
		outDegree++;		
	}
	
	public void addNodeUndirected(GraphNode n, long w){
		n.addNode(this,w);
		adj.add(new Edge(n,this,w));
		outDegree++;		
	}

	public long getOutDegree() {
		return outDegree;
	}

	public long getInDegree() {
		return inDegree;
	}

	public Key getData() {
		return data;
	}

	public void setData(Key data) {
		this.data = data;
	}
	
	public LinkedList<Edge> getAdj(){
		return adj;
	}
	
	public int getLength(){
		int length = 1;
		LinkedList<GraphNode> visted = new LinkedList<GraphNode>();
		visted.add(this);
		for(Edge e : adj){
			if(e.vertex != this)
			length += e.vertex.getLength(visted);
		}
		return length;
	}
	
	private int getLength(LinkedList<GraphNode> visted){
		int length = 1;
		visted.add(this);
		for(Edge e : adj){
			if(!visted.contains(e.vertex))
				length += e.vertex.getLength(visted);
		}
		return length;
	}
	
	public String toString(){
		return "" + data;
	}
	
	public String print(){
		String text = "Node: " + data + " connects to: \n";
		for(Edge e:adj){
			text = text + e + "\n";
		}
		return text;
	}
	
	public String printTree(){
		String text = print();
		LinkedList<GraphNode> visted = new LinkedList<GraphNode>();
		visted.add(this);
		for(Edge e : adj){
			if(!visted.contains(e.vertex))
				text = text + e.vertex.printTree(visted);
		}
		return text;
	}
	
	public String printTree(LinkedList<GraphNode> visted){
		String text = print();
		visted.add(this);
		for(Edge e : adj){
			if(!visted.contains(e.vertex))
				text = text + e.vertex.printTree(visted);
		}
		return text;
	}
	
	public GraphNode copy(){
		return new GraphNode(data);
	}
	
	public LinkedList<GraphNode> getNodes(){
		LinkedList<GraphNode> visted = new LinkedList<GraphNode>();
		for(Edge e : adj)
			if(!visted.contains(e.vertex))
				visted.addAll(e.vertex.getNodes(visted));
		
		return visted;
	}
	
	public LinkedList<GraphNode> getNodes(LinkedList<GraphNode> visted){
		for(Edge e : adj)
			if(!visted.contains(e.vertex))
				visted.addAll(e.vertex.getNodes(visted));
		
		return visted;
	}
	
	
	public LinkedList<Edge> getEdges(){
		LinkedList<GraphNode> visted = new LinkedList<GraphNode>();
		LinkedList<Edge> list = new LinkedList<Edge>();
		list.addAll(adj);
		for(Edge e : adj)
			if(!visted.contains(e.vertex))
				list.addAll(e.vertex.getEdges(visted));
		return list;
	}
	
	public LinkedList<Edge> getEdges(LinkedList<GraphNode> visted ){
		int count = 0;
		LinkedList<Edge> list = new LinkedList<Edge>();
		visted.add(this);
		for(Edge e: adj){
			System.out.println("Command Count: " + count++ + " edge: " + e);
			if(!list.contains(e))
				list.add(e);
		}
		for(Edge e : adj)
			if(!visted.contains(e.vertex))
				list.addAll(e.vertex.getEdges(visted));
		return list;
	}
}

