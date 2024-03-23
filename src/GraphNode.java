/**Class GraphNode
 * This class represents a node in a graph identified by a name and status as marked or unmarked
 * in the Maze class
 * 
 * @author Sabrina Lee 
 */

public class GraphNode {
	// INSTANCE VARIABLES
	private boolean marked;
	private int name;
	
	
	
	/**	CONSTRUCTOR
	 * sets the name field with the given number and initializes the marked field with false
	 * @param name
	 */
	public GraphNode(int name){
		this.name = name;
		marked = false;
	}
	
	
	/**	METHOD mark
	 * This method sets the marked field with true or false 
	 * @param mark		boolean value
	 */
	public void mark(boolean mark) {
		marked = mark;
	}
	
	
	/**	METHOD isMarked
	 * This method returns the boolean represented by the field marked
	 * @return
	 */
	public boolean isMarked() {
		return marked;
	}
	
	
	/**	METHOD getName
	 * This method returns the name field
	 * @return
	 */
	public int getName() {
		return name;
	}
}
