/**Class Graph
 * This class represents a graph using an adjacency list of nodes and edges.
 * 
 * @author Sabrina Lee 
 */

import java.util.*;
public class Graph implements GraphADT {
	// INSTANCE VARIABLES
	private ArrayList<GraphNode> nodeList = new ArrayList<GraphNode>();
	private ArrayList<ArrayList<GraphEdge>> edgeList = new ArrayList<ArrayList<GraphEdge>>();
	
	
	
	
	/**	CONSTRUCTOR
	 *  This constructor initializes a list of n nodes named from 0 to n-1 and initializes an empty list
	 *  of edges for each node 
	 *  @param n
	 */
	public Graph(int n) {
		for (int i = 0; i < n; i++) {
			nodeList.add(i, new GraphNode(i));
			edgeList.add(i, null);
		}
	}
	
	
	/**	METHOD insertEdge
	 * 	This method creates an edge between two valid nodes and gives the edge a type and label either 
	 * 	"corridor" or "door". The edge is added to the list of edges of the first endpoint and second endpoint
	 * 	of the edge. 
	 * 
	 * 	@param nodeu, nodev, type, label
	 */
	@Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		// find the index of each node in the list of nodes and the index of each edge list
		int uInd = findIndex(nodeu);
		int vInd = findIndex(nodev);
		ArrayList<GraphEdge> vEdges = edgeList.get(vInd);
		ArrayList<GraphEdge> uEdges = edgeList.get(uInd);
		
		// check if parameters are valid
		if (!label.equals("corridor") && !label.equals("door")) throw new GraphException("Invalid edge");
		if (uInd == -1 || vInd == -1) throw new GraphException("Node does not exist");
		
		GraphEdge edge = new GraphEdge(nodeu, nodev, type, label);
		
		// add edge to node's edge list
		if (vEdges != null) {
			if (vEdges.contains(edge) == true) throw new GraphException("Edge already exists");
			else vEdges.add(edge);
		}
		else {
			edgeList.set(vInd, new ArrayList<GraphEdge>());
			vEdges = edgeList.get(vInd);
			vEdges.add(edge);
		}
		
		// add edge to other node's edge list
		if (uEdges != null) {
			if (uEdges.contains(edge) == true) throw new GraphException("Edge already exists");
			else uEdges.add(edge);
		}
		else {
			edgeList.set(uInd, new ArrayList<GraphEdge>());
			uEdges = edgeList.get(uInd);
			uEdges.add(edge);
		}
	}

	
	/**	METHOD getNode
	 *	This method searches the list of nodes for the node with the specified name
	 *	and returns that node if it is found or throws a GraphException otherwise
	 *
	 *	@param u		the name of the node to be searched for
	 *	@return curr
	 */
	@Override
	public GraphNode getNode(int u) throws GraphException {
		GraphNode curr;
		for (int i = 0; i < nodeList.size(); i++) {
			curr = nodeList.get(i);
			if (curr.getName() == u) {
				return curr;
			}
		}
		throw new GraphException("Node does not exist");
	}

	
	/**	METHOD incidentEdges
	 * 	This method searches the list of edges incident on the node specified and returns
	 * 	and iterator of GraphEdges if it is found or null if it is not found.
	 * 
	 * 	@param u					the node whose edges are to be found
	 * 	@return edges.iterator()
	 */
	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		// retrieve the list of edges of node u
		GraphNode node = getNode(u.getName());
		int nInd = nodeList.indexOf(node);
		ArrayList<GraphEdge> edges = edgeList.get(nInd);
		
		if (edges != null) {
			return edges.iterator();
		}
		else return null;
	}

	
	/**	METHOD getEdge
	 * 	This method finds the edge whose endpoints are the nodes specified and returns it
	 * 	if found or throws a GraphException if it is not found
	 * 
	 * 	@param u		one endpoint of the edge
	 * 	@param v		other endpoint of the edge
	 * 	@return curr
	 */
	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		// find index of the nodes in the list of nodes
		int uInd = findIndex(u);
		int vInd = findIndex(v);
		
		if (uInd == -1 || vInd == -1) throw new GraphException("Node does not exist");
		else {
			// find edge list of each node and check if both contain edges
			ArrayList<GraphEdge> uEdges = edgeList.get(uInd);
			ArrayList<GraphEdge> vEdges = edgeList.get(vInd);
			if (uEdges == null || vEdges == null) throw new GraphException("Edge does not exist");

			// search one of the edge lists since both lists will have the same edge
			for (int i = 0; i < uEdges.size(); i++) {
				GraphEdge curr = uEdges.get(i);
				if (curr.firstEndpoint() == v || curr.secondEndpoint() == v) return curr;
			}
			
			// throw GraphException if edge is not found in the whole list
			throw new GraphException("Edge does not exist");
		}
	}

	
	/**	METHOD areAdjacent
	 * 	This method returns true if there is an edge connecting two nodes and false if not 
	 * 
	 * 	@param u		one endpoint of the edge
	 * 	@param v 		other endpoint of the edge
	 * 	@return true 	if the nodes are connected by an edge
	 * 	@return false	if the nodes are connected by an edge
	 */
	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		GraphEdge edge = getEdge(u,v);
		if ((edge.firstEndpoint() == u || edge.firstEndpoint() == v) && 
				(getEdge(u,v).secondEndpoint() == v || getEdge(u,v).secondEndpoint() == u)) {
			return true;
		}
		else return false;
	}
	
	
	/**	METHOD findIndex
	 * 	This private method searches the list of nodes for the specified node and returns
	 * 	the index of the node in the list or =1 if not found
	 * 
	 *  @param node		the node to be found
	 *	@return i		the index of the node
	 *	@return -1		if the node is not found
	 */
	private int findIndex(GraphNode node) {
		if (nodeList != null) {
			for (int i = 0; i < nodeList.size(); i++) {
				if (node.getName() == nodeList.get(i).getName()) return i;
			}
		}
		return -1;
	}

}

