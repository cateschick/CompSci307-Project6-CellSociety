package cellsociety.Model;

import cellsociety.Controller.CSVParser;
import cellsociety.Controller.Parser;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

class PercolationTest {
    Percolation game;
    Grid gameGrid;

    @BeforeEach
    void setUp() throws FileNotFoundException  {
        CSVParser csv = new CSVParser(new Parser());

        csv.readFile(new File("data/percolation/smallPercolation.csv"));
        gameGrid = csv.getGrid();
        game = new Percolation(gameGrid, null);
    }

    @Test
    void countNeighbors() {
        List<Integer> key = Arrays.asList(1, 1);
        int neighbors = game.countNeighbors(key, 0);
        Assertions.assertEquals(7, neighbors);
    }

    @Test
    void countNeighborsVolcano() throws FileNotFoundException {
        CSVParser csv = new CSVParser(new Parser());

        csv.readFile(new File("data/percolation/volcano.csv"));
        gameGrid = csv.getGrid();
        game = new Percolation(gameGrid, null);
        game.onePass();
        game.onePass();

        List<Integer> key = Arrays.asList(6, 5);
        int neighbors = game.countNeighbors(key, 1);
        Assertions.assertEquals(2, neighbors);

        game.onePass();
        key = Arrays.asList(6, 8);
        neighbors = game.countNeighbors(key, 1);
        Assertions.assertEquals(1, neighbors);
    }

    @Test
    void onePass() throws FileNotFoundException {
        CSVParser csv = new CSVParser(new Parser());

        Grid next = game.onePass();
        csv.readFile(new File("data/percolation/smallPercolation2.csv"));
        Assertions.assertTrue(CSVParser.getGrid().equals(next));
    }
}