package cellsociety.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * These tests assess the functionality of CSVParser.java class.
 *
 * @author Cate Schick cms168.
 */
public class CSVParserTests {

  /**
   * Tries to read bad file path to check for thrown file not found exception.
   */
  @Test
  void readFileTestFileException() {
    CSVParser csv = new CSVParser(new Parser());

    Assertions.assertThrows(FileNotFoundException.class, () ->
        csv.readFile(new File("bad/file/path")));
  }

  /**
   * Tries to read misspelled file path to check for thrown file not found exception.
   */
  @Test
  void readFileTestFileException2() {
    CSVParser csv = new CSVParser(new Parser());

    Assertions.assertThrows(FileNotFoundException.class, () ->
        csv.readFile(new File("data/game_of_life/blinkerrrrrs.csv")));
  }

  /**
   * Tries to read a file with text instead of numbers to check for thrown format exception.
   */
  @Test
  void readFileTestFormatException() {
    CSVParser csv = new CSVParser(new Parser());

    Assertions.assertThrows(NumberFormatException.class, () ->
        csv.readFile(new File("data/game_of_life/blinkers.sim")));
  }

  /**
   * Tries to read a file with text instead of numbers to check for thrown format exception.
   */
  @Test
  void readFileTestFormatException2() {
    CSVParser csv = new CSVParser(new Parser());

    Assertions.assertThrows(NumberFormatException.class, () ->
        csv.readFile(new File("data/fire/fire_center.sim")));
  }

  /**
   * Tests readFile() to see if a populated hashMap is returned.
   */
  @Test
  void readFileTestContent() throws FileNotFoundException {
    CSVParser csv = new CSVParser(new Parser());

    csv.readFile(new File("data/game_of_life/blinkers.csv"));
    Assertions.assertNotNull(CSVParser.getGrid());
  }

  /**
   * Tests readFile() to see if a populated hashMap is returned.
   */
  @Test
  void readFileTestContent2() throws FileNotFoundException {
    CSVParser csv = new CSVParser(new Parser());

    csv.readFile(new File("data/game_of_life/blinkers.csv"));
  }


  /**
   * Tests readFile() to see if comments are ignored
   */
  @Test
  void readFileTestContentComments() throws FileNotFoundException {
    CSVParser csv = new CSVParser(new Parser());

    csv.readFile(new File("data/saved/testComments.csv"));
  }

  /**
   * Test getMap() method to ensure map is adding data for every cell
   */
  @Test
  void getGridTestSize() throws FileNotFoundException {
    CSVParser csv = new CSVParser(new Parser());

    // Load a 10x10 csv file
    csv.readFile(new File("data/wator/lines.csv"));

    // File is 28x28 = 784
    Assertions.assertEquals(784, CSVParser.getGrid().size());
  }

  /**
   * Test getMap() method to ensure proper data is returned.
   */
  @Test
  void getGridTestContent() throws FileNotFoundException {
    CSVParser csv = new CSVParser(new Parser());

    // Load a file
    csv.readFile(new File("data/game_of_life/blinkers.csv"));

    // Check that example cell at [2,0] is encoded with a value of 1
    ArrayList<Integer> position = new ArrayList<>(Arrays.asList(2, 0));
    Assertions.assertEquals(1, CSVParser.getGrid().getValue(position));
  }
}

