package cellsociety.Controller;

import cellsociety.Controller.exceptions.InvalidColor;
import cellsociety.View.Resources;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.scene.paint.Color;

/**
 * This class contains methods that will open, read, and extract
 * information from a .sim file.
 *
 * @author Cate Schick
 */
public class Parser {
  /**
   * Constructor for Parser
   */
  public Parser() {
  }

  /**
   * Resource Bundle containing keys and values for GUI text.
   */
  public static final ResourceBundle MY_RESOURCES =
      Resources.getResources(Resources.getPath());

  /**
   * Hashtag for skipComments method.
   */
  private static final String HASHTAG = "#";
  /**
   * Key used when splitting lines in a file by equals sign.
   */
  private static final String EQUALS = "=";
  /**
   * Key used when splitting lines in a file by white space.
   */
  private static final String COMMA = ",";
  /**
   * Key used when splitting lines in a file by new lines.
   */
  private static final String NEW_LINE = "\n";
  /**
   * Key containing initial states from .sim file.
   */
  private static final String INITIAL_STATES_KEY = "InitialStates";
  /**
   * Key containing state color information from .sim file.
   */
  private static final String STATE_COLORS_KEY = "StateColors";
  /**
   * Key containing invalid color information from .sim file.
   */
  private static final String INVALID_COLOR_KEY = "InvalidColor";
  /**
   * Key containing parameter information from .sim file.
   */
  private static final String PARAMETERS_KEY = "NECESSARY_PARAMETERS";
  /**
   * Prefix required to open file.
   */
  private static final String DATA_PREFIX = "data/";

  /**
   * Keys and values extracted from .sim file with simulation title,
   * author, initial states, description, and game type.
   */
  private final HashMap<String, String>
      SIM_FILE_KEYS = new HashMap<>();

  /**
   * Height of simulation.
   */
  private int MY_HEIGHT = 0;
  /**
   * Width of simulation.
   */
  private int MY_WIDTH = 0;

  /**
   * Returns size of the simulation.
   *
   * @return int[] array containing width and height of simulation.
   */
  public int[] getSize() {
    return new int[]{MY_WIDTH, MY_HEIGHT};
  }

  /**
   * Sets size of the simulation.
   *
   * @param width  the width of the simulation.
   * @param height the width of the simulation.
   */
  public void setSize(final int width, final int height) {
    MY_HEIGHT = height;
    MY_WIDTH = width;
  }

  /**
   * Returns key and value pairs parsed from the .sim file.
   *
   * @return a Hashmap containing information from .sim file.
   */
  public HashMap<String, String> getSimFileKeys() {
    return SIM_FILE_KEYS;
  }

  /**
   * Reads the sim file for a simulation, then calls readFile on that file.
   *
   * @param simFilePath the path of the .sim file to be read.
   */
  public void parseFile(final File simFilePath) throws FileNotFoundException {
    try {
      Scanner sc = new Scanner(simFilePath);
      // separate by new line rather than whitespace to preserve
      // key and value pairs
      sc.useDelimiter(NEW_LINE);

      // Read each row in the .sim file
      while (sc.hasNext()) {
        readRow(sc);
      }
      // Invalid file path
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    }

    // Open the file and set game type
    openFile();
  }

  /**
   * This method attempts to read a .csv file, with catches for users using a
   * PC and a case where the file cannot be found.
   */
  private void openFile() throws FileNotFoundException {
    if (haveKey(STATE_COLORS_KEY)) {
      checkColors(SIM_FILE_KEYS.get(STATE_COLORS_KEY));
    }

    if (haveKey(INITIAL_STATES_KEY)) {
      try {

        // need to add "data/" to path to reach correct file
        String fileToOpen = DATA_PREFIX + getSimFileKeys().get(
            INITIAL_STATES_KEY);

        new CSVParser(this).readFile(new File(fileToOpen.trim()));
      }  catch (FileNotFoundException ex) {
        throw new FileNotFoundException();
      }
    }
  }

  /**
   * This method parses a .sim file and extracts the keys and values.
   *
   * @param sc Scanner used to parse the file.
   */
  private void readRow(final Scanner sc) {
    // Avoid comments
    String preprocessedLine = sc.next();
    String line = skipComments(preprocessedLine, sc);

    // Keys and values separated by "="
    try {
      String[] row = line.split(EQUALS);
      // Add keys (row[0]) and values (row[1]) from sim
      // file to a HashMap
      try {
        SIM_FILE_KEYS.put(row[0], row[1]);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException();
      }

    } catch (NoSuchElementException e) {
      // Wrong file / .sim file does not contain "="
      throw new NoSuchElementException();
    }
  }

  /**
   * Get color scheme selected by user.
   *
   * @return String[] of Colors.
   */
  public String[] getColors() {
    if (haveKey(STATE_COLORS_KEY)) {
      String colors = SIM_FILE_KEYS.get(STATE_COLORS_KEY);
      return colors.split(COMMA);
    }
    return new String[]{""};
  }

  /**
   * Checks that user colors are valid.
   *
   * @param colors String of selected colors.
   */
  private void checkColors(final String colors) {
    if (haveKey(STATE_COLORS_KEY)) {
      String[] hex = colors.split(COMMA);
      for (String s : hex) {
        try {
          Color.web(s.trim());
        } catch (Exception e) {
          throw new InvalidColor(MY_RESOURCES.getString(
              INVALID_COLOR_KEY), s);
        }
      }
    }
  }

  /**
   * Returns parameters in a .sim file.
   *
   * @return HashMap of Parameters
   */
  public HashMap<String, Double> getParameters() {
    List<String> necessaryParameters = List.of(
        MY_RESOURCES.getString(PARAMETERS_KEY).split(COMMA));
    HashMap<String, Double> map = new HashMap<>();
    for (String key : this.getSimFileKeys().keySet()) {
      if (!necessaryParameters.contains(key)) {
        map.put(key, Double.parseDouble(this.getSimFileKeys().get(key)));
      }
    }
    return map;
  }

  /**
   * This method checks to see if a line in a .csv file is a comment,
   * which would begin with a '#'.
   *
   * @param line the line being examined.
   * @param sc   the Scanner used to parse the file.
   */
  protected static String skipComments(String line, final Scanner sc) {
    if (line.startsWith(HASHTAG)) {
      sc.nextLine();
      line = sc.next();
    }
    return line;
  }

  /**
   * Checks SIM_FILE_KEYS for a desired key.
   *
   * @return if SIM_FILE_KEYS contains desired key.
   */
  private boolean haveKey(final String key) {
    return SIM_FILE_KEYS.containsKey(key);
  }
}


