package cellsociety.Model;

import cellsociety.Controller.CSVParser;
import cellsociety.Controller.Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameOfLifeTest {
    GameOfLife game;
    Grid gameGrid;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        CSVParser csv = new CSVParser(new Parser());

        csv.readFile(new File("data/game_of_life/smallBlinker.csv"));
        // TODO change either CSVParser or map to be consistent to return grid with Map<List<Integer>, Integer>
        gameGrid = csv.getGrid();
        game = new GameOfLife(gameGrid, null);
    }

    @Test
    void countNeighbors() {
        List<Integer> key = Arrays.asList(1, 1);
        int neighbors = gameGrid.countNeighbors(key, 1);
        Assertions.assertEquals(2, neighbors);
    }

    @Test
    void onePass() throws FileNotFoundException {
        CSVParser csv = new CSVParser(new Parser());

        Grid next = game.onePass();
        csv.readFile(new File("data/game_of_life/smallBlinker2.csv"));
        Assertions.assertTrue(csv.getGrid().equals(next));
    }
}