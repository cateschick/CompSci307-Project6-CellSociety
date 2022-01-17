package cellsociety.Model;

import cellsociety.Controller.Controller;

import java.util.*;

public class Grid implements Cloneable{

  private final Map<List<Integer>, Integer> gameGrid;
  private final int numRows;
  private final int numCols;
  private final EdgePolicy policy;
  private final GridType type;

  /**
   * different edge policies for dealing with neighborhoods at the edge
   */
  public enum EdgePolicy {
    FINITE,
    TOROIDAL,
    RANDOM
  }

  public enum GridType {
    COMPLETE,
    RECTANGLE,
    TRIANGLE,
    HEXAGON,
    CORNER
  }

  /**
   * this method constructs a new Grid object
   *
   * @param grid
   * @param rows
   * @param cols
   */
  public Grid(Map<List<Integer>, Integer> grid, int cols, int rows) {
    this(grid, cols, rows, EdgePolicy.FINITE, GridType.COMPLETE);
  }

  public Grid(Map<List<Integer>, Integer> grid, int cols, int rows, EdgePolicy edges, GridType gtype) {
    gameGrid = grid;
    numRows = rows;
    numCols = cols;
    policy = edges;
    type = gtype;
  }

  /**
   * return the value at an index
   *
   * @param index
   * @return
   */
  public int getValue(List<Integer> index) {
//    System.out.println("getting value "+index);
    return gameGrid.get(index);
  }

  /**
   * inserts a value at an index
   *
   * @param index
   * @param value
   */
  public void putValue(List<Integer> index, int value) {
    gameGrid.put(index, value);
  }

  /**
   * return the number of rows in the grid
   *
   * @return
   */
  public int getRows() {
    return numRows;
  }

  /**
   * return the number of columns in the grid
   *
   * @return
   */
  public int getCols() {
    return numCols;
  }

  /**
   * return the number of entries in grid
   *
   * @return
   */
  public int size() {
    return numRows * numCols;
  }

  /**
   * This method gets a list of all the neighbors around a specified cell it iterates through
   * indexes 0-8 in the following way 0 | 1 | 2 3 | 4 | 5 6 | 7 | 8
   *
   * @param index
   * @return
   */
  public List<List<Integer>> getNeighbors(List<Integer> index) {
    List<List<Integer>> neighbors = new ArrayList<>();
    int row = index.get(0);
    int col = index.get(1);

    for (int i = 0; i < 9; i++) {
      if (i == 4 || modGrid(i, GridType.RECTANGLE, 2, 0) || triangleGrid(i) || modGrid(i, GridType.HEXAGON, 3, 1) || modGrid(i, GridType.CORNER, 2, 1)) {
        continue;
      }
      if (constructNeighbor(row, col, i) != null){
        neighbors.add(constructNeighbor(row, col, i));
      }
    }
    return neighbors;
  }

  private boolean modGrid(int index, GridType gridType, int a, int b) {
    return type == gridType && index % a == b;
  }

  private boolean triangleGrid(int i) {
    return type == GridType.TRIANGLE && i != 1 && i != 6 && i != 8;
  }

  /**
   * this method constructs a neighbor based on a policy.
   * @param row
   * @param col
   * @param index
   * @return
   */
  public List<Integer> constructNeighbor(int row, int col, int index) {
    List<Integer> neighbor = null;
    if (policy == EdgePolicy.FINITE) {
      // make sure cell is not itself, or out of bounds
      if (rowOutOfBounds(row, index) || colOutOfBounds(col, index)) {
        return null;
      }
      neighbor = constructFiniteNeighbor(row, col, index);
    } else if (policy == EdgePolicy.TOROIDAL) {
      neighbor = constructToroidalNeighbor(row, col, index);
    } else if (policy == EdgePolicy.RANDOM) {
      neighbor = constructRandomNeighbor();
    }
    return neighbor;
  }

  private List<Integer> constructFiniteNeighbor(int row, int col, int index){
    return Arrays.asList(row - 1 + (index / 3), col - 1 + (index % 3));
  }

  private List<Integer> constructToroidalNeighbor(int row, int col, int index){
    return Arrays.asList(((row - 1 + (index / 3)) + numRows) % numRows, ((col - 1 + (index % 3)) + numCols) % numCols);
  }

  private List<Integer> constructRandomNeighbor(){
    return Arrays.asList(new Random().nextInt(numRows), new Random().nextInt(numCols));
  }

  /**
   * This method counts the neighbors with a given state
   *
   * @param index     the [x,y] coordinate of a cell to count neighbors around
   * @param withState only count neighbor if it has this state
   * @return the number of neighbors
   */
  public int countNeighbors(List<Integer> index, int withState) {
    int liveNeighbors = 0;
    for (List<Integer> neighbor : getNeighbors(index)) {
      liveNeighbors += gameGrid.get(neighbor) == withState ? 1 : 0;
    }
    return liveNeighbors;
  }

  /**
   * This method determines if a row is out of bounds of the grid
   *
   * @param row
   * @param index
   * @return
   */
  protected boolean rowOutOfBounds(int row, int index) {
    return (row - 1 + (index / 3) < 0) || (row - 1 + (index / 3) >= numRows);
  }

  /**
   * this method determines if a column is out of bounds of the grid
   *
   * @param col
   * @param index
   * @return
   */
  protected boolean colOutOfBounds(int col, int index) {
    return (col - 1 + (index % 3) < 0) || (col - 1 + (index % 3) >= numCols);
  }

  /**
   * this method returns a new grid object matching the current state of the existing grid object.
   * it allows for cloning a grid.
   *
   * @return
   */
  public Grid copy(){
    Map<List<Integer>, Integer> grid = new HashMap<>();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));
        grid.put(index, getValue(index));
      }
    }
    Grid gridCopy = new Grid(grid, numCols, numRows, policy, type);
    return gridCopy;
  }

  /**
   * this method overrides equals and allows us to compare the internal state of two grids
   * @param grid
   * @return
   */
  public boolean equals(Grid grid)
  {
    if (grid == null || numRows != grid.getRows() || numCols != grid.getCols()){
      return false;
    }
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));
        if(getValue(index) != grid.getValue(index)){
          return false;
        }
      }
    }
    return true;
  }

  /**
   * returns a map of the number of occurrences of each cell value in the grid
   * @return
   */
  public Map<Integer, Integer> getCounts(){
    Map<Integer, Integer> counts = new HashMap<>();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));

        counts.putIfAbsent(getValue(index), 1);
        counts.put(getValue(index), counts.get(getValue(index)) + 1);
      }
    }
    return counts;
  }

  /**
   * prints out the grid in a easier to understand way
   * @return
   */
  @Override
  public String toString(){
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));
        s.append(gameGrid.get(index));
        s.append(" ");
      }
      s.append("\n");
    }
    return s.toString();
  }

  /**
   * this method updates the values in a grid
   * @param grid
   */
  public void update(Grid grid){
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));
        gameGrid.put(index, grid.getValue(index));
      }
    }
  }
}
