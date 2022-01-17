package cellsociety.View;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.Controller.Controller;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests functionality of GridView using TestFX
 *
 * @author Kyle White
 * @author Michelle Zhang
 */
class GridViewTest extends DukeApplicationTest {

  private static final String myLanguage = "English";
  private GridView myView;
  private Controller myController;

  @Override
  public void start(Stage stage)
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myController = new Controller();
    myController.parseFile(new File("data/game_of_life/glider.sim"));
    myView = new GridView(myLanguage, stage, myController);
    stage.setScene(myView.setupDisplay());
    stage.setTitle("Testing");
    stage.setMaximized(true);
    stage.show();
  }

  @Test
  void testPauseButton() {
    Button button = lookup("#pause").query();
    String expected = "Pause";
    assertEquals(expected, button.getText());
    // GIVEN, button says pause
    // WHEN, button is clicked
    clickOn(button);
    // THEN, check label text has been changed to resume
    expected = "Resume";
    assertEquals(expected, button.getText());
    clickOn(button);
  }

  @Test
  void testStepButton() {
    Button button = lookup("#pause").query();
    clickOn(button);
    button = lookup("#step").query();
    assertEquals("Step", button.getText());
    clickOn(button);
  }

  @Test
  void testSliderAction() {
    Slider slider = lookup("#slider").query();
    Text text = lookup("#sliderText").query();
    String expected = "Animation Speed: 1.00";
    //setValue(slider, 5);
    assertEquals(slider.getValue(), 1);
    assertEquals(text.getText(), expected);
  }

  @Test
  void testNewFile() {
    Button button = lookup("#NEW_FILE").query();
    clickOn(button);
    button = lookup("#BROWSE_FILE").query();
    assertEquals("Click here to browse your files.", button.getText());
  }

  @Test
  void testSaveFile() {
    Button button = lookup("#DOWNLOAD_FILE").query();
    clickOn(button);
    TextField tf = lookup("#INPUT_SIMULATION_NAME").query();
    String expected = "TestFX";
    clickOn(tf).write(expected).write(KeyCode.ENTER.getChar());
    tf = lookup("#INPUT_SIMULATION_AUTHOR").query();
    expected = "Kyle White";
    clickOn(tf).write(expected).write(KeyCode.ENTER.getChar());
    TextArea ta = lookup("#INPUT_SIMULATION_DESCRIPTION").query();
    expected = "This is a TestFX test";
    clickOn(ta).write(expected).write(KeyCode.ENTER.getChar());
    button = lookup("#SUBMIT_INFO").query();
    clickOn(button);
  }

  @Test
  void testSideBySide() {
    Button button = lookup("#NEW_SIMULATION").query();
    clickOn(button);
    assertEquals("Add new Side-by-Side", button.getText());
    applyPath("game_of_life", "glider.sim");
  }

  @Test
  void testHistogramButton() {
    Button button = lookup("#HISTOGRAM").query();
    clickOn(button);
    assertEquals("Open a Histogram", button.getText());
  }

  @Test
  void testSimulationStateButton() {
    Button button = lookup("#state").query();
    clickOn(button);
    assertEquals("View Simulation State", button.getText());
  }

  //Applys path to file chooser
  private void applyPath(String directory, String file){
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection stringSelection = new StringSelection(directory);
    clipboard.setContents(stringSelection, stringSelection);
    press(KeyCode.CONTROL).press(KeyCode.V).release(KeyCode.V).release(KeyCode.CONTROL);
    push(KeyCode.ENTER);
    stringSelection = new StringSelection(file);
    clipboard.setContents(stringSelection, stringSelection);
    press(KeyCode.CONTROL).press(KeyCode.V).release(KeyCode.V).release(KeyCode.CONTROL);
    push(KeyCode.ENTER);
  }

}