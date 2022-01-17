package cellsociety.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Fire extends Game {

  private static final double DEFAULTIGNITE = 0.5;
  private static final double DEFAULTTREE = 0.5;
  private Grid gameGrid;
  private final double randomIgniteChance;
  private final double randomTreeChance;

  /**
   * This method initializes the Fire States are as follows 0: empty 1: tree 2: burning
   */
  public Fire(Grid grid, Map<String, Double> map) {
    super(grid, map);
    gameGrid = grid;
    randomIgniteChance = map.getOrDefault("RandomIgnite", DEFAULTIGNITE);
    randomTreeChance = map.getOrDefault("RandomTree", DEFAULTTREE);
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
        Random rand = new Random();

        if (gameGrid.getValue(index) == 2) {
          // burning cell turns into empty cell
          gridCopy.putValue(index, 0);
        } else if (gameGrid.getValue(index) == 1 && (gameGrid.countNeighbors(index, 2) >= 1
            || rand.nextDouble() < randomIgniteChance)) {
          // tree burns if at least 1 neighbor is burning
          // tree ignites with probability even if no neighbors burning
          gridCopy.putValue(index, 2);
        } else if (gameGrid.getValue(index) == 0 && (rand.nextDouble() < randomTreeChance)) {
          // empty space fills with tree with probability
          gridCopy.putValue(index, 1);
        }
      }
    }
    gameGrid.update(gridCopy);
    return gameGrid;
  }
}
