package cellsociety.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Game {

  private final Grid gameGrid;
  private final Map<String, Double> myMap;

  public Game(Grid grid, Map<String, Double> map) {
    gameGrid = grid;
    myMap = map;
  }

  /**
   * Returns the state of the game grid at a desired position.
   *
   * @param row Specified row number.
   * @param col Specified column number.
   * @return the state of the cell at that location.
   */

  public int getState(int row, int col) {
    ArrayList<Integer> position = new ArrayList<>();
    position.add(row);
    position.add(col);
    return gameGrid.getValue(position);
  }

  /**
   * This abstract method will implement one iteration of each game sub-class added to this
   * program.
   *
   * @return A HashMap detailing each cell in the grid's index number, followed by that cell's
   * state.
   */
  public abstract Grid onePass();

  /**
   * Sets the state of cell at the coordinates
   *
   * @param row   coordinate of cell
   * @param col   coordinate of cell
   * @param state of cell
   */
  public void setState(int row, int col, int state) {
    ArrayList<Integer> coordinates = new ArrayList<>();
    coordinates.add(row);
    coordinates.add(col);
    gameGrid.putValue(coordinates, state);
  }

  /**
   * This method counts the neighbors around a cell with a given state.
   *
   * @param index     the [x,y] coordinate of a cell to count neighbors around
   * @param withState only count neighbor if it has this state
   * @return the number of neighbors
   */
  public int countNeighbors(List<Integer> index, int withState) {
    return gameGrid.countNeighbors(index, withState);
  }

  /**
   * @return counts of current agents
   */
  public Map<Integer, Integer> getCounts() {
    return gameGrid.getCounts();
  }
}
