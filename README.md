# mazeSolver

Given a maze, this program finds the first available path from the start to the end using a modified depth-first search algorithm. Paths in the maze can be locked behind doors with a fee of coins required to pass and each maze is given a number of coins to start with. A text file is read by the program and the maze is constructed using an undirected graph where the nodes represent rooms in a grid shaped maze and edges represent corridors or locked doors. Upon starting the program, a graphical representation of the maze is shown, and when prompted the solution path will be highlighted; if it exists.

## Installation

1. Clone the repository: `git clone https://github.com/sabrinajlee/mazeSolver`
2. Navigate to the project directory
3. Use 'java Solve fileName' where fileName is the text file containing the maze
*This project was developed and tested using Java version 17

## Creating Mazes

The first four lines of the text file will each have one number per line representing the scale factor, number of rooms per row of the maze, number of rooms per column of the maze, and number of starting coins. The following lines are alternating between horizontal corridors and rooms, and vertical corridors and walls. Each room is designated as one of the following: the entrance, the exit, a regular room, or a passageway (corridor). Corridors between rooms can be designated as one of the following: a passageway, a wall, or an integer between 0-9 representing the fee to pass that door. Walls are impassable, and there is only one entrance and exit per maze.
There are example mazes provided in this repo.

’s’: entrance to the maze
’x’: exit of the maze
’o’: room
’c’: corridor
’w’: wall
’0’, ’1’, ... ’9’: door requiring coin fee
