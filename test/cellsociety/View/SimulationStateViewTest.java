package cellsociety.View;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.Controller.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class SimulationStateViewTest extends DukeApplicationTest {

  private static final String myLanguage = "English";
  private SimulationStateView myView;
  private Controller myController;

  @Override
  public void start(Stage stage)
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myController = new Controller();
    myController.parseFile(new File("data/game_of_life/glider.sim"));
    myView = new SimulationStateView(myLanguage, stage, myController);
    stage.setScene(myView.setupDisplay());
    stage.setTitle("Testing");
    stage.setMaximized(true);
    stage.show();
  }

  @Test
  void testStateTitleLabel() {
    Labeled stateTitleLabel = lookup("#STATE_TITLE").query();
    String expected = "See agent state colors and changes:";
    assertEquals(expected, stateTitleLabel.getText());
  }

  @Test
  void testAgentLabels() {
    Labeled deadLabel = lookup("#Dead: ").query();
    Labeled aliveLabel = lookup("#Alive: ").query();

    String expectedDead = "Dead: ";
    String expectedAlive = "Alive: ";
    assertEquals(expectedDead, deadLabel.getText());
    assertEquals(expectedAlive, aliveLabel.getText());
  }

  @Test
  void testAgentColors() {
    Labeled deadColor = lookup("#FF00FF").query();
    Labeled aliveColor = lookup("#00FF00").query();

    String expectedDead = "FF00FF";
    String expectedAlive = "00FF00";
    assertEquals(expectedDead, deadColor.getText());
    assertEquals(expectedAlive, aliveColor.getText());
  }
}

