package cellsociety.Model;

import cellsociety.Controller.CSVParser;
import cellsociety.Controller.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class GridTest {
    Grid gameGrid;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        CSVParser csv = new CSVParser(new Parser());

        csv.readFile(new File("data/game_of_life/smallBlinker.csv"));
        // TODO change either CSVParser or map to be consistent to return grid with Map<List<Integer>, Integer>
        gameGrid = csv.getGrid();
    }

    @Test
    void getValue() {
        List<Integer> index = new ArrayList<>(Arrays.asList(0, 0));
        Assertions.assertEquals(gameGrid.getValue(index), 0);
    }

    @Test
    void putValue() {
        List<Integer> index = new ArrayList<>(Arrays.asList(0, 0));
        gameGrid.putValue(index, 1);
        Assertions.assertEquals(gameGrid.getValue(index), 1);
    }

    @Test
    void getRows() {
        Assertions.assertEquals(gameGrid.getRows(), 3);
    }

    @Test
    void getCols() {
        Assertions.assertEquals(gameGrid.getCols(), 3);
    }

    @Test
    void size() {
        Assertions.assertEquals(gameGrid.size(), 9);
    }

    @Test
    void getNeighbors() {
        List<Integer> index = new ArrayList<>(Arrays.asList(0, 0));
        List<List<Integer>> neighbors = gameGrid.getNeighbors(index);
        Assertions.assertEquals(neighbors.size(), 3);
    }

    @Test
    void constructNeighbor() {
        List<Integer> neighbor = gameGrid.constructNeighbor(0, 0, 5);
        Assertions.assertEquals(gameGrid.getValue(neighbor), 1);
    }

    @Test
    void countNeighbors() {
        List<Integer> index = new ArrayList<>(Arrays.asList(0, 0));
        Assertions.assertEquals(gameGrid.countNeighbors(index, 1), 2);
    }

    @Test
    void rowOutOfBounds() {
        Assertions.assertTrue(gameGrid.rowOutOfBounds(0, 0));
    }

    @Test
    void colOutOfBounds() {
        Assertions.assertTrue(gameGrid.colOutOfBounds(0, 0));
    }

    @Test
    void copy() {
        Grid gridCopy = gameGrid.copy();
        Assertions.assertTrue(gameGrid.equals(gridCopy));
    }

    @Test
    void testEquals() {
        Grid gridCopy = gameGrid.copy();
        Assertions.assertTrue(gameGrid.equals(gridCopy));
    }

    @Test
    void getCounts() {
        Map<Integer, Integer> counts = gameGrid.getCounts();
        Assertions.assertEquals(counts.get(0), 7);
    }

    @Test
    void print() {
        System.out.println(gameGrid);
    }

    @Test
    void update() throws FileNotFoundException {
        CSVParser csv = new CSVParser(new Parser());
        csv.readFile(new File("data/game_of_life/smallBlinker2.csv"));
        Grid gameGrid2 = csv.getGrid();
        gameGrid.update(gameGrid2);
        Assertions.assertTrue(gameGrid.equals(gameGrid2));
    }
}