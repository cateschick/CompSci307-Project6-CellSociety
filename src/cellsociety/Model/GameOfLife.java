package cellsociety.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GameOfLife extends Game {

  private Grid gameGrid;

  /**
   * This method initializes the GameOfLife States are as follows 0: dead 1: alive
   */
  public GameOfLife(Grid grid, Map<String, Double> map) {
    super(grid, map);
    gameGrid = grid;
  }

  /**
   * This method simulates an iteration of the Game of GameOfLife.
   *
   * @return A HashMap of each cell, followed by their state.
   */
  @Override
  public Grid onePass() {
    Grid gridCopy = gameGrid.copy();
    for (int i = 0; i < gameGrid.getRows(); i++) {
      for (int j = 0; j < gameGrid.getCols(); j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));

        // 0/Dead, 1/Alive
        if (gameGrid.getValue(index) == 1 && (gameGrid.countNeighbors(index, 1) == 2
            || gameGrid.countNeighbors(index, 1) == 3)) {
          continue;
        } else if (gameGrid.getValue(index) == 0 && gameGrid.countNeighbors(index, 1) == 3) {
          gridCopy.putValue(index, 1);
        } else {
          gridCopy.putValue(index, 0);
        }
      }
    }
    gameGrid.update(gridCopy);
    return gameGrid;
  }
}
