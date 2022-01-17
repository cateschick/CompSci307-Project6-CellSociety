package cellsociety.View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.Controller.Controller;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Testing class for FileInputView
 *
 * @author Michelle Zhang
 * @author Kyle White
 */
class FileInputViewTest extends DukeApplicationTest {

  private static final String myLanguage = "English";
  private FileInputView myView;
  private Stage myStage;

  @Override
  public void start(Stage stage) {
    myStage = stage;
    myView = new FileInputView(myLanguage, new Controller());
    myStage.setScene(myView.setUpFileInput(myStage));
    myStage.setTitle("Testing");
    myStage.setMaximized(true);
    myStage.show();
  }

  @Test
  void testLoadFile() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        List<File> files = new ArrayList<>();
        files.add(new File("data/game_of_life/blinkers.sim"));
        myView.displayDoneButton(myView.getDragTarget(), files);
        Button button = lookup("#DONE").query();
        clickOn(button);
      }
    });
  }

  @Test
  void showError() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        List<File> files = new ArrayList<>();
        files.add(new File("data/game_of_life/InvalidColor.sim"));
        myView.displayDoneButton(myView.getDragTarget(), files);
        Button button = lookup("#DONE").query();
        clickOn(button);
        files = new ArrayList<>();
        files.add(new File("data/game_of_life/InvalidColor2.sim"));
        myView.displayDoneButton(myView.getDragTarget(), files);
        button = lookup("#DONE").query();
        clickOn(button);
      }
    });
  }

  @Test
  void validateFile() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        File file = new File("data/game_of_lif/blinkers.sim");
        assertTrue(myView.validateFile(file));
        file = new File("data/game_of_lif/blinkers.csv");
        assertFalse(myView.validateFile(file));
      }
    });
  }

  @Test
  void browseFile() {
    Button browse = lookup("#BROWSE_FILE").query();
    assertEquals("Click here to browse your files.", browse.getText());
    clickOn(browse);
  }

  @Test
  void browseAndInputFile() throws InterruptedException {
    Button browse = lookup("#BROWSE_FILE").queryButton();
    clickOn(browse);
    File file = new File("data/game_of_life/blinkers.sim");
    applyPath(file.getAbsolutePath().trim());
    Button doneButton = lookup("#DONE").query();
    assertEquals(doneButton.getText(), "Done");
  }

  private void applyPath(String filePath) {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection stringSelection = new StringSelection(filePath);
    clipboard.setContents(stringSelection, stringSelection);
    press(KeyCode.CONTROL).press(KeyCode.V).release(KeyCode.V).release(KeyCode.CONTROL);
    push(KeyCode.ENTER);
  }

}
