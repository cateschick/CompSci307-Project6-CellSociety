package cellsociety.Controller;

import cellsociety.Model.Game;
import cellsociety.Model.Grid;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class stores the map structure so that the Model and View can remain independent of each
 * other.
 *
 * @author Kyle White
 * @author Michelle Zhang
 * @author Cate Schick
 */
public class Controller {

  /**
   * Parser used for Parser.java methods.
   */
  private static Parser myParser;

  public Controller() {
    myParser = new Parser();
  }

  /**
   * Path to create an instance of Game.
   */
  private static final String GAME_PATH = "cellsociety.Model.%s";

  /**
   * Varaible to access resources from resourcesBundle.
   */
  private static final ResourceBundle MY_RESOURCES = ResourceBundle.getBundle(
      "cellsociety.View.resources.English");

  /**
   * Create game class.
   */
  private static Game myGame;

  /**
   * Sets myGame to the proper gameType based on .sim file information using reflection.
   */
  private void setGame()
      throws InstantiationException, NoSuchMethodException,
      IllegalAccessException, ClassNotFoundException, InvocationTargetException {

    Grid myMap = CSVParser.getGrid();
    Map<String, Double> myParameters = myParser.getParameters();

    Class<?> gameClass = Class.forName(
        String.format(GAME_PATH, myParser.getSimFileKeys().get("Type").trim()));

    // call constructor whose parameter matches the given class type
    myGame = (Game) gameClass.getDeclaredConstructor(Grid.class, Map.class)
        .newInstance(myMap, myParameters);

    // Show proper error messages for all possible exceptions
  }

  /**
   * @return The number of rows specified in the file.
   */
  public static int getRows() {
    return myParser.getSize()[1];
  }

  /**
   * @return The number of columns specified in the file.
   */
  public static int getCols() {
    return myParser.getSize()[0];
  }

  /**
   * Parses a sim file and creates a new game instance.
   *
   * @param file The file to be read.
   * @throws ClassNotFoundException    Specified class cannot be found.
   * @throws InvocationTargetException Target cannot be invoked.
   * @throws NoSuchMethodException     No such method exists in program.
   * @throws InstantiationException    Object cannot be instantiated.
   * @throws IllegalAccessException    Request does not have permissions or scope to perform
   *                                   action.
   */
  public void parseFile(final File file)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, FileNotFoundException {

    myParser.parseFile(file);

    // Ensure File specifies which game to simulate
    if (myParser.getSimFileKeys().containsKey("Type")) {
      setGame();
    }
  }

  /**
   * The method gets all the color strings specified in a .sim file
   *
   * @return The colors specified in the file.
   */
  public String[] getColors() {
    return myParser.getColors();
  }

  /**
   * @return The keys in SIM_FILE_KEYS extracted from the .csv file.
   */
  public HashMap<String, String> getSimFileKeys() {
    return myParser.getSimFileKeys();
  }

  /**
   * This method sets a cell's state to a given integer.
   *
   * @param row   the row to be accessed.
   * @param col   the column to be accessed.
   * @param state the state the desired cell will be set to.
   */
  public void setState(final int row, final int col, final int state) {
    myGame.setState(row, col, state);
  }

  /**
   * Get information about a cell at desired location.
   *
   * @param row the row to be accessed.
   * @param col the column to be accessed.
   * @return state of the cell at location (row, col)
   */
  public static int getState(final int row, final int col) {
    return myGame.getState(row, col);
  }

  /**
   * This method creates a fileChooser where users can upload files.
   *
   * @return fileChooser a new FileChooser for users to upload files
   */
  public FileChooser createFileBrowser() {
    FileChooser fileChooser = new FileChooser();

    String currentPath = Paths.get("./data").toAbsolutePath().
        normalize().toString();
    fileChooser.setInitialDirectory(new File(currentPath));

    fileChooser.getExtensionFilters().add(
        new ExtensionFilter("Sim Files",
            String.format("*%s", MY_RESOURCES.getString("Extensions"))));
    return fileChooser;
  }

  /**
   * Calls onePass method in the model with the proper Game object specified in the .sim file.
   */
  public void onePass() {
    myGame.onePass();
  }

  /**
   * This method returns the current number of agents for View's histogram feature.
   *
   * @return current number of agents for histogram.
   */
  public Map<Integer, Integer> getCounts() {
    return myGame.getCounts();
  }

  /**
   * @return CellShape class
   */
  public String getShape() {
    return "cellsociety.View.CellShape.Rectangle";
  }

  /**
   * Saves file based on user inputs
   *
   * @param inputs user inputs
   */
  public void saveFile(String[] inputs) throws IOException {
    String sim = formatSim(inputs);
    BufferedWriter writer = new BufferedWriter(
        new FileWriter(String.format("data/saved/%s.sim", inputs[0])));
    writer.write(sim);
    writer.close();
    createCSV(inputs[0]);
  }

  /**
   * Formats .sim file
   *
   * @param inputs list of strings to oinput
   * @return a formatted .sim file
   */
  private String formatSim(String[] inputs) {
    String[] keys = MY_RESOURCES.getString("SaveKeys").split(",");
    StringBuilder sim = new StringBuilder();
    HashMap<String, String> map = getSimFileKeys();
    for (int i = 0; i < keys.length; i++) {
      sim.append(String.format("%s=%s\n", keys[i], inputs[i]));
      map.remove(keys[i]);
    }
    for (String s : map.keySet()) {
      sim.append(String.format("%s=%s\n", s, map.get(s)));
    }
    return sim.toString();
  }

  //Creates CSV for Sim file
  private void createCSV(String name) throws IOException {
    StringBuilder csv = new StringBuilder();
    int rows = getRows();
    int cols = getCols();
    csv.append(String.format("%s,%s\n", rows, cols));
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        csv.append(String.format("%s,", getState(i, j)));
      }
      csv.delete(csv.length() - 1, csv.length());
      csv.append("\n");
    }
    BufferedWriter writer = new BufferedWriter(
        new FileWriter(String.format("data/saved/%s.csv", name)));
    writer.write(csv.toString());
    writer.close();
  }

  /**
   * Returns a randomly-generated grid with desired dimensions.
   *
   * @param numRows   the number of rows.
   * @param numCols   the number of columns.
   * @param numAgents the number of agents in desired simulation.
   * @return Grid object representing the randomly-generated game grid.
   */
  public Grid getRandomGrid(final int numRows, final int numCols,
      final int numAgents) {
    // Create HashMap
    Map<List<Integer>, Integer> map = new HashMap<>();

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        // select random agent from numAgents
        int random = pickRandomAgent(numAgents);

        ArrayList<Integer> position = new ArrayList<>(Arrays.asList(i, j));

        map.put(position, random);
      }
    }
    return new Grid(map, numCols, numRows);
  }

  /**
   * This method selects a random number from teh number of agents and adds it to the current
   * position on the map.
   */
  private int pickRandomAgent(final int numAgents) {
    // Add random agent to cell
    Random r = new Random();
    int num = r.nextInt((numAgents - 1 + 1) + 1);

    return num;
  }

  /**
   * This method returns a randomly generated grid, where the number of each agent desired has been
   * specified by the user.
   *
   * @param numRows             the number of rows.
   * @param numCols             the number of columns.
   * @param numAgents           the number of agents in desired simulation.
   * @param agentSpecifications how many of agent should be on.
   * @return a Grid with desired specifications.
   */
//  public Grid getInformedGrid(final int numRows, final int numCols, final int numAgents, final List<
//      Integer> agentSpecifications) {
//    // Create HashMap of GRID CELLS and AGENT STATES
//    Map<List<Integer>, Integer> myMap = new HashMap<>();
//    Map<Integer, Integer> myAgents = new HashMap<>();
//
//    int counter = 1;
//    for (Integer agent : agentSpecifications) {
//      myAgents.put(counter, agent);
//    }
//
//    System.out.println(myAgents);
//
//    for (int i = 0; i < numRows; i++) {
//      for (int j = 0; j < numCols; j++) {
//        // Select an agent for this cell based on remaining specifications
//        int num = selectInformedAgent(numAgents, myAgents);
//
//        ArrayList<Integer> position = new ArrayList<>(Arrays.asList(i, j));
//        myMap.put(position, num);
//      }
//    }
//
//    return new Grid(myMap, numCols, numRows);
//  }

  /**
   * This method places and agent after checking to ensure the user still requested more of that
   * agent on the board
   */
//  private int selectInformedAgent(final int numAgents, final Map<Integer, Integer> myAgents) {
//
//    // Add random agent to cell
//    Random r = new Random();
//    int num = r.nextInt((numAgents - 1 + 1) + 1);
//
//    // Make sure agents value isn't 0
//    while (myAgents.get(num) == 0) {
//      r = new Random();
//      num = r.nextInt((numAgents - 1 + 1) + 1);
//
//      // Check if all values have 0; if so make rest of cells 0
//      if (checkAllKeys(myAgents)) {
//        break;
//      }
//    }
//
//    // If all agent specifications are fulfilled, make cells 0
//    if (checkAllKeys(myAgents)) {
//      num = 0;
//    } else {
//      myAgents.put(num, myAgents.get(num) - 1);
//    }
//
//    return num;
//  }

  /**
   * This method checks all the keys in a HashMap to check if there are no more agent specifications
   * to be placed in a simulation.
   *
   * @param agentMap
   * @return
   */
//  private boolean checkAllKeys(Map<Integer, Integer> agentMap) {
//    Set<Integer> agents = agentMap.keySet();
//    int size = agents.toArray().length;
//
//    int counter = 0;
//    for (Integer agent : agents) {
//      if (agentMap.get(agent) == 0) {
//        counter++;
//      }
//    }
//
//    return counter == size;
//  }
}
