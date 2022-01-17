package cellsociety.Model;

import cellsociety.Controller.CSVParser;
import cellsociety.Controller.Parser;
import java.io.FileNotFoundException;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

class WatorWorldTest {
  Grid gameGrid;
  WatorWorld game;

  @BeforeEach
  void setUp() throws FileNotFoundException {
    CSVParser csv = new CSVParser(new Parser());

    csv.readFile(new File("data/wator/smallWator.csv"));
    gameGrid = csv.getGrid();
    game = new WatorWorld(gameGrid, Map.of("EnergyGain", 5.0, "FishReproduction", 5.0, "SharkReproduction", 5.0));
  }

  @Test
  void countNeighbors() {
    List<Integer> key = Arrays.asList(1, 1);
    int neighbors = game.countNeighbors(key, 1);
    Assertions.assertEquals(7, neighbors);
  }

  @Test
  void onePass() throws FileNotFoundException {
    CSVParser csv = new CSVParser(new Parser());

    Grid next = game.onePass();
    csv.readFile(new File("data/wator/smallWator2.csv"));
    Assertions.assertTrue(csv.getGrid().equals(next));
  }
}