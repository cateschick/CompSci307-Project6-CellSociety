package cellsociety.View;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.Controller.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class SimulationViewTest extends DukeApplicationTest {

  private static final String myLanguage = "English";
  private SimulationView myView;
  private Controller myController;

  @Override
  public void start(Stage stage)
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myController = new Controller();
    myController.parseFile(new File("data/game_of_life/glider.sim"));
    myView = new SimulationView(myLanguage, stage, myController);
    stage.setScene(myView.setupDisplay());
    stage.setTitle("Testing");
    stage.setMaximized(true);
    stage.show();
  }

  @Test
  void testNewFileButton() {
    Button button = lookup("#NEW_FILE").query();
    String expected = "New file";
    assertEquals(expected, button.getText());
    clickOn(button);
  }

  @Test
  void testSaveFileButton() {
    Button button = lookup("#DOWNLOAD_FILE").query();
    String expected = "Download file";
    assertEquals(expected, button.getText());
    clickOn(button);
  }

}
