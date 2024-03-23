/**Class Maze
 * This class represents a maze game by creating a Graph object and building the rooms
 * and doors and corridors of the maze by reading in a text file containing the data for
 * the maze game.
 * 
 * @author Sabrina Lee 
 */

import java.io.*;
import java.util.*;
public class Maze {
	// INSTANCE VARIABLES
	private Graph maze;
	private int width;
	private int length;
	private int coins;
	private GraphNode entrance;
	private GraphNode exit;
	private Stack<GraphNode> path;
	
	
	
	/**	CONSTRUCTOR
	 * 	The constructor reads the input file and builds the graph representing the maze.
	 * 	The rooms of the maze are represented by GraphNodes and the corridors and doors
	 * 	are represented by GraphEdges
	 *  @param inputFile	the file containing the data to build the maze
	 */
	Maze(String inputFile){
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			// read in first four lines for data on the maze, skipping the first line
			Integer.parseInt(in.readLine());
			width = Integer.parseInt(in.readLine());
			length = Integer.parseInt(in.readLine());
			coins = Integer.parseInt(in.readLine());
			
			// create a Graph object with the total number of rooms based on the width and 
			//	length of the maze as the number of nodes
			maze = new Graph(width * length);
			
			String line1;
			String line2;
			int roomCount = 0;
			
			// loop ends when a null line is read during an iteration
			while (true) {
				// this section handles rooms and horizontal edges
				line1 = in.readLine();
				for (int i= 0; i < line1.length(); i++) {
					// create the room nodes and set the entrance and exit nodes
					if (i % 2 == 0) {
						switch (line1.charAt(i)) {
							case 's':
								roomCount += 1;
								entrance = maze.getNode(roomCount-1);
								break;
							case 'x':
								roomCount += 1;
								exit = maze.getNode(roomCount-1);
								break;
							case 'o':
								roomCount += 1;
								break;
							default:
								throw new MazeException("Invalid character");
						}
						// create horizontal edges between all the nodes
						if (i+2 <= line1.length()) {
							switch (line1.charAt(i + 1)) {
								case 'W':	// no edge is needed for walls
								case 'w':
									break;
								case 'c':	// create an edge between the current node and node to its right
									maze.insertEdge(maze.getNode(roomCount-1), maze.getNode(roomCount), 0, "corridor");
									break;
								default:	// if the char is a number then create the edge with its specified type 
											//	and the current node and the node to its right
									if (Character.isDigit(line1.charAt(i+1))) {
										maze.insertEdge(maze.getNode(roomCount-1), maze.getNode(roomCount), Character.getNumericValue(line1.charAt(i+1)), "door");
									}
									else throw new MazeException("Invalid character");
									break;
							}
						}
					}
				}
				// this section handles vertical edges
				int colCount = 0;
				line2 = in.readLine();
				if (line2 == null) break;	// after reading the last line of nodes this line will be empty, break the loop
				
				for (int j = 0; j < line2.length(); j++) {
					if (j % 2 == 0) {
						colCount++;
						switch (line2.charAt(j)) {
							case 'W':
							case 'w':
								break;
							case 'c':
								maze.insertEdge(maze.getNode(roomCount-1-width+colCount), maze.getNode(colCount+roomCount-1), 0, "corridor");
								break;
							default:
								if (Character.isDigit(line2.charAt(j))) {
									maze.insertEdge(maze.getNode(roomCount-1-width+colCount), maze.getNode(colCount+roomCount-1), Character.getNumericValue(line2.charAt(j)), "door");
								}
								else throw new MazeException("Invalid character");
						}
					}
				}
			}			
			in.close();
		}
		catch (IOException e){
			System.out.println("Cannot open file");
		}
		catch (MazeException e) {
		    System.out.println(e.getMessage());
		}
		catch (GraphException e) {
			System.out.println("Cannot create maze");
		}
	}
	
	
	/**	METHOD getGraph 
	 * 	This method returns the Graph object representing this maze
	 * 
	 *  @return maze
	 *  @throws MazeException
	 */
	public Graph getGraph() throws MazeException{
		if (maze == null) throw new MazeException("Maze does not exist");
		else return maze;
	}
	
	
	/**	METHOD solve
	 * 	This method retrieves an iterator of all the nodes in the path from the start node
	 * 	to the end node to solve the maze game and returns the iterator or null if the path does not exist
	 * 
	 *  @return path.iterator()		the correct path to solve the game
	 *  @return null				if the path does not exist
	 */
	public Iterator<GraphNode> solve() {
		path = new Stack<GraphNode>();	
		
		// retrieve the path from the entrance node to the exit node with the available number of coins
		Iterator<GraphNode> pathIter = pathFind(entrance, exit, coins);
		
		// check if the path is correct by checking if each node in the path is connected
		//	and the last node in the path is the exit
		if (pathIter == null) return null;
		while (pathIter.hasNext() == true) {
			GraphNode currNode = pathIter.next();
			if (pathIter.hasNext() == true) {
				GraphNode nextNode = pathIter.next();
				try {
					if (!maze.areAdjacent(currNode, nextNode)) {
						return null;
					}
				} 
				catch (GraphException e) {
					System.out.println("Node does not exist");
				}
			}
		}
		return path.iterator();
	}
	
	
	/**	METHOD pathFind
	 * 	This method uses a modified DFS algorithm to find the exit node from the start node 
	 * 	recursively using the available number of coins to open doors in the path and saves 
	 * 	the correct nodes in the path into a stack and returns an iterator of the path or null
	 * 	if a path does not exist
	 * 
	 *  @param start		the starting node of each method call
	 *  @param exit			the exit of the maze
	 *  @param coins		the remaining number of available coins
	 *  @return	pathIter	an iterator of the nodes of the correct path
	 *  @return null		if a path from start to exit does not exist
	 */
	private Iterator<GraphNode> pathFind(GraphNode start, GraphNode exit, int coins) {
		try {
			Iterator<GraphEdge> edges = maze.incidentEdges(start);
			GraphEdge currEdge;
			GraphNode otherNode;
			
			// mark the starting node and push it onto the stack
			start.mark(true);
			path.push(start);
			
			// check if the stack currently has the exit node and return an iterator of the stack if so
			if (path.peek().getName() == exit.getName()) return path.iterator();
			
			// check each incident edge on the start node for a possible path to find the exit node
			while (edges.hasNext()) {
				int currCoins = coins;
				currEdge = edges.next();
				
				// get the node connected to this iteration's edge
				if (currEdge.firstEndpoint() == start) otherNode = currEdge.secondEndpoint();
				else otherNode = currEdge.firstEndpoint();
		
				// skip this edge if there are not enough available coins to open the door or the other node 
				//	has already been visited
				if (currCoins < currEdge.getType()) continue;
				if (otherNode.isMarked()) continue;
				else {
					// use the required coins to open the door and call the function on the next node
					currCoins -= currEdge.getType();
					Iterator<GraphNode> pathIter = pathFind(otherNode, exit, currCoins);
					// if a valid path is found then return it
					if (pathIter != null) return pathIter;
				}
			}
			// if this node has no valid path to follow then unmark it and remove it from the stack
			start.mark(false);
			path.pop();
			return null;
		}
		catch (GraphException e) {
			System.out.println("Path was not found");
			return null;
		}
		
		
		
		
	}
}
