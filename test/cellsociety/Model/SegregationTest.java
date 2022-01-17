package cellsociety.Model;

import cellsociety.Controller.CSVParser;
import cellsociety.Controller.Parser;
import java.io.FileNotFoundException;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

class SegregationTest {
    Segregation game;
    Grid gameGrid;

    @BeforeEach
    void setUp() throws FileNotFoundException  {
        CSVParser csv = new CSVParser(new Parser());

        csv.readFile(new File("data/schelling/smallSchelling.csv"));
        gameGrid = csv.getGrid();
        game = new Segregation(gameGrid, Map.of("SatisfactionThreshold", 0.5));
    }

    @Test
    void countNeighbors() {
        List<Integer> key = Arrays.asList(1, 1);
        int neighbors = game.countNeighbors(key, 0);
        Assertions.assertEquals(1, neighbors);
    }

    @Test
    void onePass() throws FileNotFoundException {
        CSVParser csv = new CSVParser(new Parser());

        Grid next = game.onePass();
        csv.readFile(new File("data/schelling/smallSchelling2.csv"));
        Assertions.assertTrue(csv.getGrid().equals(next));
    }
}