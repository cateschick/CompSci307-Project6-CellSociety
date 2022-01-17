package cellsociety.View;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.Controller.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class that tests the methods of the cell grid class
 *
 * @author Kyle White
 */
class CellGridTest {

  private CellGrid myTestInstance;
  private Controller myController;

  @BeforeEach
  void setUp()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException,
      FileNotFoundException {
    myController = new Controller();
    myController.parseFile(new File("data/game_of_life/glider.sim"));
  }

  @Test
  void update()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myTestInstance = new CellGrid(400,400, myController);
    fillGrid(400,400,0);
    checkGridValue(0);
    fillGrid(400,400,1);
    checkGridValue(1);
  }

  // Fill grid with value
  private void fillGrid (int rows, int cols, int fill) {
    LinkedHashMap<ArrayList<Integer>, Integer> map = new LinkedHashMap<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        myTestInstance.update(i,j,fill);
      }
    }
  }

  // Loops through the grid and checks the expected state with the current color
  private void checkGridValue(int num) {
    int rows = myTestInstance.getRows();
    int cols = myTestInstance.getCols();
    Color c = myTestInstance.getConversion(num);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        assertEquals(c, myTestInstance.getCell(i,j).getFill());
      }
    }
  }

  @Test
  void addConverstionsTest()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, FileNotFoundException {
    myTestInstance = new CellGrid(400,400, myController);
    Color c = myTestInstance.getConversion(0);
    assertEquals(c, Color.web("00ff00"));
    c = myTestInstance.getConversion(1);
    assertEquals(c, Color.web("FF00FF"));
    c = myTestInstance.getConversion(2);
    assertEquals(c, null);
  }

}