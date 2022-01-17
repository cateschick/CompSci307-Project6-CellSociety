package cellsociety.Controller;

import cellsociety.Model.Grid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class contains methods to read a .csv file and returns
 * important information such as the dimensions of the simulation
 * and individual cell states.
 *
 * @author Cate Schick
 */
public class CSVParser {

  /**
   * Hashtag.
   */
  private static final String HASHTAG = "#";

  /**
   * Parser used to read file.
   */
  private static Parser myParser;

  /**
   * Constructor for .csv class.
   */
  public CSVParser(final Parser p) {
    super();
    myParser = p;
  }

  /**
   * Rows in file split by comma.
   */
  private static final String COMMA = ",";

  /**
   * Controller containing details about cell states.
   */
  private static final Map<List<Integer>, Integer>
      MY_MAP = new HashMap<>();

  /**
   * Returns the Grid Object with initial state values for each cell.
   *
   * @return Controller with cell states.
   */
  public static Grid getGrid() {
    // Grid constructor takes map, numRows, numCols
    return new Grid(MY_MAP, myParser.getSize()[0], myParser.getSize()[1]);
  }

  /**
   * This method parses a .csv file.
   *
   * @param file the name of the file.
   */
  public void readFile(final File file) throws FileNotFoundException,
      NumberFormatException {

    try {
      Scanner sc = new Scanner(file);
      String line = sc.next();
      readFirstLine(sc, line);

      // Iterate through map and add cell states
      populateMap(sc);
    } catch (FileNotFoundException e) { /* CANNOT FIND FILE */
      throw new FileNotFoundException();
    } catch (NumberFormatException e) {
      /* FILE IS NOT A CSV; IMPROPER SYNTAX */
      throw new NumberFormatException();
    }
  }

  /**
   * This method attempts to read the first line in a .csv File and
   * extract the dimensions of it.
   *
   * @throws NumberFormatException when the first line of the .csv is not
   *                               properly formatted with the dimensions
   *                               of the simulation.
   */
  private void readFirstLine(final Scanner sc, String line)
      throws NumberFormatException {
    // Skip first line if it's a comment
    if (line.startsWith(HASHTAG)) {
      sc.nextLine();
      line = sc.next();
    }

    // Header of CSVParser = dimensions of simulation
    try {
      String[] dimensions = line.split(COMMA);

      myParser.setSize(Integer.parseInt(dimensions[0]),
          Integer.parseInt(dimensions[1]));

    } catch (NumberFormatException exception) {
      /* NOT FORMATTED PROPERLY */
      throw new NumberFormatException();
    }
  }

  /**
   * This method adds initial cell states to a map.
   *
   * @param sc the scanner reading file information.
   */
  private void populateMap(final Scanner sc) {
    int rowIndex = 0;

    // Read every line in the file
    while (sc.hasNext()) {
      // Skip comments if they are present in file
      String line = sc.next();
      // Skip first line if it's a comment
      if (line.startsWith(HASHTAG)) {
        sc.nextLine();
        line = sc.next();
      }

      String[] row = line.split(COMMA);
      int colIndex = 0;
      for (String cellState : row) {
        ArrayList<Integer> myPosition = new ArrayList<>();

        // Add position as map key, and cell state as value
        myPosition.add(rowIndex);
        myPosition.add(colIndex);

        MY_MAP.put(myPosition, Integer.parseInt(cellState));
        colIndex++;
      }
      rowIndex++;
    }
  }
}
