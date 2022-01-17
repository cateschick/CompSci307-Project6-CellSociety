package cellsociety.Model;

import cellsociety.Controller.CSVParser;
import cellsociety.Controller.Parser;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

class FireTest {
    Fire game;
    Grid gameGrid;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        CSVParser csv = new CSVParser(new Parser());
        csv.readFile(new File("data/fire/smallFire.csv"));
        gameGrid = CSVParser.getGrid();
        game = new Fire(gameGrid, Map.of("RandomIgnite", 0.5, "RandomTree", 0.5));
    }

    @Test
    void countNeighbors() {
        List<Integer> key = Arrays.asList(1, 1);
        int neighbors = gameGrid.countNeighbors(key, 0);
        Assertions.assertEquals(0, neighbors);
    }

    @Test
    void onePass() throws FileNotFoundException {
        CSVParser csv = new CSVParser(new Parser());

        Grid next = game.onePass();
        csv.readFile(new File("data/fire/smallFire2.csv"));
        Assertions.assertTrue(CSVParser.getGrid().equals(next));
    }
}