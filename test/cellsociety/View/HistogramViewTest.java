package cellsociety.View;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.Controller.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.chart.BarChart;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class HistogramViewTest extends DukeApplicationTest {

  private static final String myLanguage = "English";
  private HistogramView myView;
  private Controller myController;

  @Override
  public void start(Stage stage)
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myController = new Controller();
    myController.parseFile(new File("data/game_of_life/glider.sim"));
    myView = new HistogramView(myLanguage, stage, myController);
    stage.setScene(myView.setupDisplay());
    stage.setTitle("Testing");
    stage.setMaximized(true);
    stage.show();
  }

  @Test
  void testHistogramXAxisLabel() {
    BarChart histogram = lookup("#histogram").query();
    String expected = "Species";
    assertEquals(expected, histogram.getXAxis().getLabel());
  }

  @Test
  void testHistogramYAxisLabel() {
    BarChart histogram = lookup("#histogram").query();
    String expected = "Population";
    assertEquals(expected, histogram.getYAxis().getLabel());
  }
}
