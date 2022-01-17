package cellsociety.Controller;

import cellsociety.Model.Grid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * These tests assess the functionality of Controller.java class.
 *
 * @author Cate Schick cms168.
 */
public class ControllerTests {

  /**
   * Ensures method returns the proper number of rows.
   */
  @Test
  void getRowsTest() throws FileNotFoundException {
    Parser myParser = new Parser();
    CSVParser csv = new CSVParser(myParser);

    // fire_corner is 8x8
    csv.readFile(new File("data/fire/fire_corner.csv"));

    Assertions.assertEquals(8, myParser.getSize()[0]);
  }

  /**
   * Ensures method returns the proper number of rows.
   */
  @Test
  void getRowsTest2() throws FileNotFoundException {
    Parser myParser = new Parser();
    CSVParser csv = new CSVParser(myParser);

    // long_pipe is 9x7
    csv.readFile(new File("data/percolation/long_pipe.csv"));

    Assertions.assertEquals(9, myParser.getSize()[0]);
  }

  /**
   * Ensures method returns the proper number of columns.
   */
  @Test
  void getColsTest() throws FileNotFoundException {
    Parser myParser = new Parser();
    CSVParser csv = new CSVParser(myParser);

    // buffet is 10x1
    csv.readFile(new File("data/wator/buffet.csv"));

    Assertions.assertEquals(1, myParser.getSize()[1]);
  }

  /**
   * Ensures method returns the proper number of columns.
   */
  @Test
  void getColsTest2() throws FileNotFoundException {
    Parser myParser = new Parser();
    CSVParser csv = new CSVParser(myParser);

    // long_pipe is 9x7
    csv.readFile(new File("data/percolation/volcano.csv"));

    Assertions.assertEquals(8, myParser.getSize()[1]);
  }

  /**
   * Ensures the file is parsed and necessary information is extracted.
   */
  @Test
  void parseFileTest()
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Controller c = new Controller();

    c.parseFile(new File("data/percolation/volcano.sim"));

    Assertions.assertTrue(c.getSimFileKeys().containsKey("Type"));

  }

  /**
   * Ensures the file is parsed and necessary information is extracted.
   */
  @Test
  void parseFileTest2()
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Controller c = new Controller();

    c.parseFile(new File("data/schelling/divided.sim"));

    Assertions.assertEquals("Segregation", c.getSimFileKeys().get("Type").trim());
  }

  /**
   * Ensures the file is parsed and necessary information is extracted.
   */
  @Test
  void parseFileTestNoType()
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Controller c = new Controller();

    c.parseFile(new File("data/saved/testNoType.sim"));

    Assertions.assertFalse(c.getSimFileKeys().containsKey("Type"));
  }

  /**
   * Assesses the functionality of saveFile method.
   *
   * @throws IOException               IO invalid input.
   * @throws ClassNotFoundException    Specified class not found in program.
   * @throws InvocationTargetException Target not found.
   * @throws NoSuchMethodException     Specified method not found in program.
   * @throws InstantiationException    Cannot create specified object.
   * @throws IllegalAccessException    Method does not have necessary access or scope.
   */
  @Test
  void saveFileTest()
      throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Controller myTestInstance = new Controller();
    myTestInstance.parseFile(new File("data/game_of_life/glider.sim"));
    myTestInstance.saveFile(new String[]{"test", "Kyle White", "This is a test"});
    myTestInstance.saveFile(new String[]{"test2", "Kyle White", "This is also a test"});
    File sim = new File("data/saved/test2.sim");
    File csv = new File("data/saved/test2.csv");
  }

  /**
   * Assesses the functionality of setState method.
   */
  @Test
  public void setStateTest()
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Controller c = new Controller();

    c.parseFile(new File("data/game_of_life/blinkers.sim"));

    c.setState(1, 1, 1);
    int currentState = Controller.getState(1, 1);

    Assertions.assertEquals(1, currentState);
  }

  /**
   * Assesses the functionality of setState method.
   */
  @Test
  public void setStateTest2()
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Controller c = new Controller();

    c.parseFile(new File("data/fire/fire_center.sim"));

    c.setState(3, 3, 2);
    int currentState = Controller.getState(3, 3);

    Assertions.assertEquals(2, currentState);
  }

  /**
   * Checks that setState doesn't crash when state is out of bounds.
   */
  @Test
  public void setStateTest3()
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Controller c = new Controller();
    c.parseFile(new File("data/game_of_life/blinkers.sim"));

    c.setState(1000, 1000, 2);
    int currentState = Controller.getState(1000, 1000);

    Assertions.assertEquals(2, currentState);
  }

  /**
   * Tests functionality of getRandomGrid() method when
   * creating a Grid with n rows.
   */
  @Test
  public void getRandomGridTestRows() {
    Controller c = new Controller();

    Grid gameGrid = c.getRandomGrid(1, 10, 1);

    Assertions.assertEquals(gameGrid.getRows(), 1);
  }

  /**
   * Tests functionality of getRandomGrid() method when creating
   * a grid with n columns.
   */
  @Test
  public void getRandomGridTestCols() {
    Controller c = new Controller();

    Grid gameGrid = c.getRandomGrid(1, 10, 1);

    Assertions.assertEquals(gameGrid.getCols(), 10);
  }

  /**
   * Tests functionality of getRandomGrid() method to ensure it results
   * in a populated map.
   */
  @Test
  public void getRandomGridTestCell() {
    Controller c = new Controller();

    Grid gameGrid = c.getRandomGrid(10, 10, 1);

    Assertions.assertNotNull(gameGrid);
  }
}

