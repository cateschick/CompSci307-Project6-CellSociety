package cellsociety.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Segregation extends Game {

  private static final double DEFAULTTHRESHOLD = 0.3;
  private Grid gameGrid;
  private final double threshold;
  private final ArrayList<ArrayList<Integer>> emptySpaces;

  /**
   * This method initializes Segregation States are as follows 0: empty 1: agent A 2: agent B
   */
  public Segregation(Grid grid, Map<String, Double> map) {
    super(grid, map);
    gameGrid = grid;
    threshold = map.getOrDefault("SatisfactionThreshold", DEFAULTTHRESHOLD);
    emptySpaces = new ArrayList<>();
    // populate list of all empty places
    // https://stackoverflow.com/questions/4005816/map-how-to-get-all-keys-associated-with-a-value
    for (int i = 0; i < gameGrid.getRows(); i++) {
      for (int j = 0; j < gameGrid.getCols(); j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));
        if (gameGrid.getValue(index) == 0) {
          emptySpaces.add(index);
        }
      }
    }
  }

  /**
   * This method simulates an iteration of the Game of Segregation.
   *
   * @return A HashMap of each cell, followed by their state.
   */
  @Override
  public Grid onePass() {
    Grid gridCopy = gameGrid.copy();
    for (int i = 0; i < gameGrid.getRows(); i++) {
      for (int j = 0; j < gameGrid.getCols(); j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));

        int value = gameGrid.getValue(index);
        double satisfaction =
            (double) gameGrid.countNeighbors(index, value) / gameGrid.countNeighbors(index,
                3 - value);

        if (satisfaction < threshold) {
          // unsatisfied, need to move to random empty spot
          ArrayList<Integer> emptySpot = emptySpaces.get(new Random().nextInt(emptySpaces.size()));

          gridCopy.putValue(index, 0);
          gridCopy.putValue(emptySpot, value);
          emptySpaces.add(index);
          emptySpaces.remove(emptySpot);
        }
      }
    }
    gameGrid.update(gridCopy);
    return gameGrid;
  }
}
