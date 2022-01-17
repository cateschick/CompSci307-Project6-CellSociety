package cellsociety.View;

import cellsociety.Controller.Controller;
import cellsociety.View.CellShape.CellShape;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * This class represents the grid of cells
 *
 * @author Kyle White
 */
public class CellGrid {

  private static final double SCREEN_WIDTH = 600;
  private static final double SCREEN_HEIGHT = 400;
  private static final ResourceBundle myResources = ResourceBundle.getBundle(
      "cellsociety.View.resources.english");
  private int rows;
  private int cols;
  private final Group myGridNode;
  private final CellShape[][] myCells;
  private final Map<Integer, Color> colorConversion;
  private final Controller myController;

  /**
   * Constructor class for cell grid takes in dimensions of grid and initializes the grid
   *
   * @param rows number of rows
   * @param cols number of columns
   */
  public CellGrid(int rows, int cols, Controller controller)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    this.rows = rows;
    this.cols = cols;
    myGridNode = new Group();
    colorConversion = new HashMap<>();
    myController = controller;
    addConversions(myController.getColors());
    myCells = new CellShape[rows][cols];
    Class<?> cl = Class.forName(controller.getShape());
    Constructor<?> cons = cl.getConstructor(double.class,double.class,double.class,double.class);
    fillGrid(cons);
  }

  /**
   * Add color conversions to map
   */
  private void addConversions(String[] colors) {
    if (colors.length == 1 && colors[0] == "") {
      colors = myResources.getString("DefaultColors").split(",");
    }
    for (int i = 0; i < colors.length; i++) {
      Color c = Color.web(colors[i].trim());
      colorConversion.put(i, c);
    }
  }

  /**
   * @return colorConversions
   */
  public Color getConversion(int state) {
    if (colorConversion.containsKey(state)) {
      return colorConversion.get(state);
    }
    return null;
  }

  /**
   * @return myGridNode
   */
  public Group getGridNode() {
    return myGridNode;
  }

  /**
   * @return myCells
   */
  public CellShape getCell(int row, int col) {
    return myCells[row][col];
  }

  /**
   * @return number of rows in grid
   */
  public int getRows() {
    return rows;
  }

  /**
   * @return number of cols in grid
   */
  public int getCols() {
    return cols;
  }

  //Sets all the rectangles in the grid
  private void fillGrid(Constructor<?> cons)
      throws InvocationTargetException, InstantiationException, IllegalAccessException {
    double height = SCREEN_HEIGHT / rows;
    double width = SCREEN_WIDTH / cols;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        myCells[i][j] = (CellShape) cons.newInstance(j * width, i * height, width, height);
        addMouseEvent(i, j, myCells[i][j]);
        myGridNode.getChildren().add(myCells[i][j]);
      }
    }
  }

  //Adds Mouse Event to rectangle
  private void addMouseEvent(int row, int col, CellShape rec) {
    rec.setOnMouseClicked(t -> {
      if (rec.getFill().equals(colorConversion.get(0))) {
        rec.setFill(colorConversion.get(1));
        myController.setState(row, col, 1);
      } else {
        rec.setFill(colorConversion.get(0));
        myController.setState(row, col, 0);
      }
    });
  }

  /**
   * Updates the colors of the cells
   */
  public void update(int row, int col, int state) {
    myCells[row][col].setFill(colorConversion.get(state));
  }

}
