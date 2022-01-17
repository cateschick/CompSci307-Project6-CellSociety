package cellsociety.Model;

import java.util.*;

public class Percolation extends Game {

  private Grid gameGrid;

  /**
   * This method initializes the Fire States are as follows
   * 0: open, 1: percolated, 2: blocked
   */
  public Percolation(Grid grid, Map<String, Double> map) {
    super(grid, map);
    gameGrid = grid;
  }

  /**
   * This method simulates an iteration of the Game of Fire.
   *
   * @return A HashMap of each cell, followed by their state.
   */
  @Override
  public Grid onePass() {
    Grid gridCopy = gameGrid.copy();
    for (int i = 0; i < gameGrid.getRows(); i++) {
      for (int j = 0; j < gameGrid.getCols(); j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));
        if (gameGrid.getValue(index) == 1 || gameGrid.getValue(index) == 2) {
          // blocked or percolated
          continue;
        } else if (gameGrid.countNeighbors(index, 1) >= 1) {
          gridCopy.putValue(index, 1);
        }
      }
    }
    gameGrid.update(gridCopy);
    return gameGrid;
  }
}
