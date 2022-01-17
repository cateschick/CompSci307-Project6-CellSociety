package cellsociety.View;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests view utility class
 *
 * @author Kyle White
 */
class ViewUtilTest extends DukeApplicationTest {

  private static final String myLanguage = "English";
  private ViewUtil myTestInstance;

  @Override
  public void start(Stage stage) {
    myTestInstance = new ViewUtil(myLanguage);
    TextArea ta = myTestInstance.makeTextArea("testArea");
    TextField tf = myTestInstance.makeTextField("testField");

    setupStage(ta, tf);
  }

  @Test
  void makeButton() {
    Button button = myTestInstance.makeButton("NEW_FILE", event -> {
    });
    assertEquals("New file", button.getText());
    assertEquals("NEW_FILE", button.getId());
  }

  @Test
  void makeLabel() {
    Label label = myTestInstance.makeLabel("NEW_FILE");
    assertEquals("New file", label.getText());
    assertEquals("NEW_FILE", label.getId());
  }

  @Test
  void makeImageView() {
    ImageView fileIcon = myTestInstance.makeImageView("images/bi_download.png", "FILE_ICON");
    assertEquals("FILE_ICON", fileIcon.getId());
  }

  @Test
  void setID() {
    Button b = new Button("Test");
    myTestInstance.setID("Test", b);
    assertEquals("Test", b.getId());
  }

  @Test
  void makeActions() {
    Button b = new Button("Test");
    Node test = myTestInstance.makeActions(b);
    assertEquals("button-box", test.getStyleClass().toString());
  }

  @Test
  void makeToolBar() {
    Button b = new Button("Test");
    ToolBar tb = myTestInstance.makeToolBar("Test", b);
    assertEquals("Test", tb.getId());
    assertEquals("tool-bar toolbar", tb.getStyleClass().toString());
  }

  @Test
  void makeUniversalLabel() {
    Label label = myTestInstance.makeUniversalLabel("universalLabel");
    assertEquals("universalLabel", label.getId());
    assertEquals("label label", label.getStyleClass().toString());
  }

  @Test
  void makeTextField() {
    String expected = "text field test!";
    TextField textField = lookup("#testField").query();
    clickOn(textField).write(expected).write(KeyCode.ENTER.getChar());
    assertEquals("text field test!", textField.getText());
  }

  @Test
  void makeTextArea() {
    String expected = "text area test!";
    TextArea textArea = lookup("#testArea").query();
    clickOn(textArea).write(expected).write(KeyCode.ENTER.getChar());
    assertEquals("text area test!", textArea.getText().trim());
  }

  private void setupStage(Node... tested) {
    Stage stage = new Stage();
    VBox root = new VBox();
    root.getChildren().addAll(tested);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}