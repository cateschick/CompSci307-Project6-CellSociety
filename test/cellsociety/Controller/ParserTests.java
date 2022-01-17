package cellsociety.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cellsociety.Controller.exceptions.InvalidColor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

/**
 * These tests assess the functionality of Parser.java class.
 *
 * @author Cate Schick cms168.
 */
public class ParserTests {

  /**
   * Test getSize() method to ensure proper width is returned.
   */
  @Test
  void getSizeTestWidth() {
    Parser myParser = new Parser();
    // sample dimensions: width 100, height 200
    myParser.setSize(100, 200);

    int[] dimensions = myParser.getSize();

    assertEquals(100, dimensions[0]);
  }

  /**
   * Test getSize() method to ensure proper width is returned.
   */
  @Test
  void getSizeTest2Width() {
    Parser myParser = new Parser();

    // sample dimensions: width 100, height 200
    myParser.setSize(123123, 200);

    int[] dimensions = myParser.getSize();

    assertEquals(123123, dimensions[0]);
  }

  /**
   * Test getSize() method to ensure proper height is returned.
   */
  @Test
  void getSizeTestHeight() {
    Parser myParser = new Parser();

    // sample dimensions: width 100, height 200
    myParser.setSize(100, 200);

    int[] dimensions = myParser.getSize();

    assertEquals(200, dimensions[1]);
  }

  /**
   * Test getSize() method to ensure proper height is returned.
   */
  @Test
  void getSizeTest2Height() {
    Parser myParser = new Parser();

    // sample dimensions: width 100, height 200
    myParser.setSize(100, 543212345);

    int[] dimensions = myParser.getSize();

    assertEquals(543212345, dimensions[1]);
  }

  /**
   * Tests setSize() method to make sure myWidth is properly set.
   */
  @Test
  void setSizeTestWidth() {
    Parser myParser = new Parser();

    // sample dimensions: width 500, height 1000
    myParser.setSize(500, 1000);

    assertEquals(500, myParser.getSize()[0]);
  }

  /**
   * Tests setSize() method to make sure myWidth is properly set.
   */
  @Test
  void setSizeTest2Width() {
    Parser myParser = new Parser();

    // sample dimensions: width 500, height 1000
    myParser.setSize(908123, 1000);

    assertEquals(908123, myParser.getSize()[0]);
  }

  /**
   * Tests setSize() method to make sure myHeight is properly set.
   */
  @Test
  void setSizeTestHeight() {
    Parser myParser = new Parser();

    // sample dimensions: width 500, height 1000
    myParser.setSize(500, 1000);

    assertEquals(1000, myParser.getSize()[1]);
  }

  /**
   * Ensures parseFile() properly extracts file to be opened.
   */
  @Test
  void parseFilePathTest() throws FileNotFoundException {
    Parser myParser = new Parser();

    myParser.parseFile(new File("data/game_of_life/blinkers.sim"));
    String fileToOpen = myParser.getSimFileKeys().get("InitialStates");

    Assertions.assertEquals("game_of_life/blinkers.csv", fileToOpen);
  }

  /**
   * Ensures parseFile() properly extracts game type being played.
   */
  @Test
  void parseFileGameTypeTest() throws FileNotFoundException {
    Parser myParser = new Parser();

    myParser.parseFile(new File("data/game_of_life/blinkers.sim"));
    String gameType = myParser.getSimFileKeys().get("Type");
    Assertions.assertEquals("GameOfLife", gameType);
  }

  /**
   * Ensures parseFile() reacts properly to a bad file path and throws the proper exception.
   */
  @Test
  void parseFileExceptionTest() {
    Parser myParser = new Parser();

    Assertions.assertThrows(FileNotFoundException.class, () ->
        myParser.parseFile(new File("bad/path/name")));
  }

  @Test
  void getColorsTest() throws FileNotFoundException {
    Parser myParser = new Parser();
    myParser.parseFile(getFile("data/game_of_life/blinkers.sim"));
    String[] colors = myParser.getColors();
    assertEquals(colors.length, 1);
    assertEquals(colors[0], "");
    myParser.parseFile(getFile("data/game_of_life/glider.sim"));
    colors = myParser.getColors();
    assertEquals(colors.length, 2);
    assertEquals(colors[0], "00FF00");
    assertEquals(colors[1], "FF00FF");
    myParser.parseFile(getFile("data/game_of_life/penta_decathlon.sim"));
    colors = myParser.getColors();
    assertEquals(colors.length, 2);
    assertEquals(colors[0], "DDDDDD");
    assertEquals(colors[1], "FF0000");
    checkException(myParser, "Invalid Color: DDDDDDD", getFile("data/game_of_life/InvalidColor.sim"));
    checkException(myParser, "Invalid Color: invalid", getFile("data/game_of_life/InvalidColor2.sim"));
  }

  /**
   * Gets file from path string.
   *
   * @param path the file path.
   * @return File for given path.
   */
  private File getFile(String path) {
    Path p = Paths.get(path);
    return p.toFile();
  }

  /**
   * Checks for invalidColor exception.
   *
   * @param myParser the parser being used.
   * @param expected the expected exception.
   * @param file the file being checked.
   */
  private void checkException(Parser myParser, String expected, File file) {
    Exception exception = assertThrows(InvalidColor.class,
        () -> myParser.parseFile(file));
    String actualMessage = exception.getMessage();
    assertEquals(actualMessage, expected);
  }

  /**
   * Makes sure getSimFileKeys returns correct map information.
   */
  @Test
  void getSimFileKeysTest() throws FileNotFoundException {
    // Add information to map from a file
    Parser myParser = new Parser();

    myParser.parseFile(new File("data/game_of_life/blinkers.sim"));
    HashMap<String, String> testMap = myParser.getSimFileKeys();

    // Controller should now have key value pairs
    Assertions.assertNotNull(testMap);
  }
}
