# Cell Society Design Discussion
### Team Number 3
### Names
* Cate Schick
* Michelle Zhang
* Kyle White
* Samuel Li

### Discussion Questions

 * How does a Cell know what rules to apply for its simulation?

   When determining which simulation to use, a user will select the desired simulation through
a JavaFX button. Once selected, the input will be saved as a Game object to let the model know which 
simulation is being used. Depending on the simulation, there can be several numbers of agents, and 
each subclass of Game (Life, Fire, Segregation, Wa-Tor) will allocate the desired number of agents.

 * How does a Cell know about its neighbors?

   We can have the grid represented by an int[][] matrix in the model. With each iteration of the animation,
we will implement a checkNeighbors method that will inform a cell about its neighor's status.

 * How can a Cell update itself without affecting its neighbors update?

   The first pass needs to determine whether a cell needs to be on or not. These results will be passed 
to the View, where the grid cells will be altered accordingly.

 * What behaviors does the Grid itself have?

   Depending on the game, it can be filled with a number of colors, or not filled.

 * How can a Grid update all the Cells it contains?

   The model will pass a data structure with instructions to the View, which will update the display
to represent these changes.

 * How is configuration information used to set up a simulation?

   There is a CSV file with the initial state passed into the model, and this will determine
the end state of the simulation.

 * How is the GUI updated after all the cells have been updated?

   The grid will be redrawn with the proper information.
 
### Alternate Designs

#### Design Idea #1

 * Data Structure #1 and File Format #1

   A HashMap, where the keys are the cell # and the value is the action that should
be taken on it. 
 
   A CSV File is sent to the model.

#### Design Idea #2

 * Data Structure #1 and File Format #1

   An int[][] matrix detailing the status of each cell and the action it should
   be changed to (0, 1, 2... n).

   A JSON File is sent to the model.
 
### CRC Card Classes

This abstract class's purpose is to represent the simulation being played:

|Game| |
|---|---|
|HashMap<Integer, Integer> onePass  | |

```java
public class Game {
     // Simulate one iteration of the simulation
     public HashMap<Integer, Integer> onePass();
 }
 ```

|Cell| |
|---|---|
|int getValue()   | |
|void setStatus(int state)    |  |
|int getNumNeighbors(int state)  | |


```java
public class Cell {
     // Determines state of a cell
     public int getValue();
     // Sets a cell's state
     void setStatus(int state);
     // Returns number of neighbors with state n
     int getNumNeighbors(int state);
 }
 ```

### Use Cases

 * Apply the rules to a cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all of its neighbors)
```java
public class Game {
  HashMap<Integer, Integer> myMap;
  
   public HashMap<Integer, Integer> onePass() {
     // Determine status of the cell
      for(int i=0; i>numRows; i++) {
        for(int j=0; j>numColumns; j++) {
          // Iterate through every cell
           // Get status of the cell
           Cell c = myMap[i][j];
           int state = c.getState();
           
           int deadNeighbors = getNumNeighbors(0);
           int liveNeighbors = getNumNeighbors(1);

           if (deadNeighbors > 2 && state = 1) {
             c.setState(0);
           } else if (liveNeighbors > 3 && state = 1) {
             c.setState(0);
           } else if (liveNeighbors == 3 && state = 0 {
             c.setState(1);
           } 
        }
      }
   }
}
```

 * Move to the next generation: update all cells in a simulation from their current state to their next state
```java
public class Game() {
  HashMap<Integer, Integer> results = onePass();
  View.updateView(results);
}
```

 * Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
public class View {
  // Clear current drawing
   View.clearScreen();
  // Open a new file and run that simulation
  View.openFile();
  Model.runSimulation(fileName);
}
```
