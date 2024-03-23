/**Class GraphEdge
 * This class represents an edge between two nodes with a type that represents the number
 * of coins needed in the Maze class to play the game and a label indicating whether the
 * edge is a door or a corridor
 * @author Sabrina Lee
 */

public class GraphEdge {
	// INSTANCE VARIABLES
	private GraphNode u;
	private GraphNode v;
	private int type;
	private String label;
	
	/**	CONSTRUCTOR
	 * @param u			first endpoint of the node
	 * @param v			second endpoint of the node
	 * @param type		number of coins needed in the Maze class
	 * @param label		the type of passage in the Maze class
	 */
	public GraphEdge(GraphNode u, GraphNode v, int type, String label){
		this.u = u;
		this.v = v;
		this.type = type;
		this.label = label;
	}
	
	
	/**	METHOD firstEndpoint
	 * 	This method returns the first node of the edge
	 * @return u
	 */
	public GraphNode firstEndpoint() {
		return u;
	}
	
	
	/**	METHOD secondEndpoint
	 * 	This method returns the second node of the edge
	 * @return v
	 */
	public GraphNode secondEndpoint() {
		return v;
	}
	
	
	/**	METHOD getType
	 * 	This method return the type field
	 * @return
	 */
	public int getType() {
		return type;
	}
	
	
	/**	METHOD setType
	 * 	This method sets the type field
	 */
	public void setType(int newType){
		type = newType;
	}
	
	
	/**	METHOD getLabel
	 * 	This method return the label field
	 * @return label
	 */
	public String getLabel() {
		return label;
	}
	
	
	/**	METHOD setLabel
	 * 	This method sets the label field
	 */
	public void setLabel(String newLabel){
		label = newLabel;
	}
}
