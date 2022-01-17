package cellsociety.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WatorWorld extends Game {

  private static final double DEFAULTENERGY = 5.0;
  private static final double DEFAULTFISH = 5.0;
  private static final double DEFAULTSHARK = 5.0;
  private Grid gameGrid;
  private final double energyGain;
  private final Map<List<Integer>, List<Double>> energyGrid;
  private final double fishRep;
  private final double sharkRep;


  /**
   * This method initializes Wa-tor States are as follows 0: empty 1: fish 2: shark
   */
  public WatorWorld(Grid grid, Map<String, Double> map) {
    super(grid, map);
    gameGrid = grid;
    energyGain = map.getOrDefault("EnergyGain", DEFAULTENERGY);
    fishRep = map.getOrDefault("FishReproduction", DEFAULTFISH);
    sharkRep = map.getOrDefault("SharkReproduction", DEFAULTSHARK);
    energyGrid = new HashMap<>();

    buildEnergyGrid();
  }

  private void buildEnergyGrid() {
    for (int i = 0; i < gameGrid.getRows(); i++) {
      for (int j = 0; j < gameGrid.getCols(); j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));
        // associate coordinate with fish->has [life, 0.0], shark->has [life, energy], empty->has [0.0, 0.0]
        ArrayList<Double> lifeAndEnergy =
            gameGrid.getValue(index) == 2 ? new ArrayList<>(Arrays.asList(0.0, energyGain))
                : new ArrayList<>(Arrays.asList(0.0, 0.0));
        //            ArrayList<Integer> lifeAndEnergy = value == 0 ? new ArrayList<>(Arrays.asList(0, 0)) : value == 1 ? new ArrayList<>(Arrays.asList(0, 0)) : new ArrayList<>(Arrays.asList(0, energy));
        energyGrid.put(index, lifeAndEnergy);
      }
    }
  }

  /**
   * This method simulates an iteration of the Game of Wa-tor.
   *
   * @return A HashMap of each cell, followed by their state.
   */
  @Override
  public Grid onePass() {
    Grid gridCopy = gameGrid.copy();
    for (int i = 0; i < gameGrid.getRows(); i++) {
      for (int j = 0; j < gameGrid.getCols(); j++) {
        ArrayList<Integer> index = new ArrayList<>(Arrays.asList(i, j));

        // get a list of empty neighbors
        List<List<Integer>> emptyNeighbors = new ArrayList<>();
        List<List<Integer>> fishNeighbors = new ArrayList<>();
        identifyNeighbors(gridCopy, i, j, emptyNeighbors, fishNeighbors);

        if (gridCopy.getValue(index) == 1) {
          fishAction(gridCopy, index, emptyNeighbors);
        } else if (gridCopy.getValue(index) == 2) {
          sharkAction(gridCopy, index, emptyNeighbors, fishNeighbors);
        }
      }
    }
    gameGrid.update(gridCopy);
    return gameGrid;
  }

  private void identifyNeighbors(Grid gridCopy, int row, int col,
      List<List<Integer>> emptyNeighbors, List<List<Integer>> fishNeighbors) {
    List<Integer> index = new ArrayList<>(Arrays.asList(row, col));
    List<List<Integer>> neighbors = gridCopy.getNeighbors(index);

    for (List<Integer> neighbor : neighbors){
      if (gridCopy.getValue(neighbor) == 0) {
        emptyNeighbors.add(neighbor);
      } else if (gridCopy.getValue(neighbor) == 1) {
        fishNeighbors.add(neighbor);
      }
    }
  }

  private void sharkAction(Grid gridCopy, List<Integer> index,
      List<List<Integer>> emptyNeighbors, List<List<Integer>> fishNeighbors) {
    // sharks
    List<Integer> moveTo =
        fishNeighbors.size() > 0 ? fishNeighbors.get(new Random().nextInt(fishNeighbors.size()))
            : emptyNeighbors.size() > 0 ? emptyNeighbors.get(
                new Random().nextInt(emptyNeighbors.size())) : index;
    // calculate the shark energy
    double sharkEnergy = fishNeighbors.size() > 0 ? energyGrid.get(index).get(1) + energyGain
        : energyGrid.get(index).get(1) - 1;
    if(energyGrid.get(index).get(1) < 1){
      // shark dies
      gridCopy.putValue(index, 0);
    }
    else{
      moveAnimal(gridCopy, index, moveTo, sharkEnergy, sharkRep, energyGain, 2);
    }
  }

  private void fishAction(Grid gridCopy, List<Integer> index,
      List<List<Integer>> emptyNeighbors) {
    List<Integer> moveTo = emptyNeighbors.size() > 0 ? emptyNeighbors.get(
        new Random().nextInt(emptyNeighbors.size())) : index;
    // move a fish to an empty neighbor
    moveAnimal(gridCopy, index, moveTo, 0.0, fishRep, 0.0, 1);
  }

  private void moveAnimal(Grid gridCopy, List<Integer> index, List<Integer> moveTo,
      double energy, double reproduction, double energyGain, int species) {
    // move animal to spot
    gridCopy.putValue(moveTo, gridCopy.getValue(index));
    // update the animal energies as well
    ArrayList<Double> sharkLife = new ArrayList<>(
        Arrays.asList(energyGrid.get(index).get(0) + 1, energy));
    energyGrid.put(moveTo, sharkLife);
    // animal has enough energy to reproduce
    canReproduce(gridCopy, index, reproduction, energyGain, species);
  }

  private void canReproduce(Grid gridCopy, List<Integer> index, double reproduction,
      double energyGain, int species) {
    if (energyGrid.get(index).get(0) > reproduction) {
      energyGrid.put(index, new ArrayList<>(Arrays.asList(0.0, energyGain)));
      gridCopy.putValue(index, species);
    } else {
      energyGrid.put(index, new ArrayList<>(Arrays.asList(0.0, 0.0)));
      gridCopy.putValue(index, 0);
    }
  }
}
