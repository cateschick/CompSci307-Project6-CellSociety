package cellsociety.View;

import cellsociety.Controller.Controller;
import cellsociety.Controller.exceptions.InvalidColor;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class for the initial view where user inputs a file
 *
 * @author Michelle Zhang
 * @author Kyle White
 */
public class FileInputView {

  private static String LANGUAGE;
  private Scene myScene;
  private Stage myStage;
  private VBox dragTarget;
  private final ViewUtil util;
  private final Controller myController;
  private final ResourceBundle myResources;

  /**
   * Initialize a view setting a language.
   *
   * @param language Language of the user
   */

  public FileInputView(String language, Controller controller) {
    util = new ViewUtil(language);
    LANGUAGE = language;
    myController = controller;
    myResources = util.getResources();
  }

  /**
   * Setup file input display.
   *
   * @return JavaFX scene
   */
  public Scene setUpFileInput(Stage stage) {
    myStage = stage;
    dragTarget = setupFileInputActions();

    BorderPane root = new BorderPane();
    root.getStyleClass().add("root");
    root.setCenter(dragTarget);

    myScene = new Scene(root);
    myScene.getStylesheets().addAll(getClass().getResource(util.getStylesheet()).toExternalForm(),
        getClass().getResource(util.getTheme()).toExternalForm());
    return myScene;
  }

  /**
   * question: should we have this only for testing? probably bad design
   *
   * @return dragTarget
   */
  public VBox getDragTarget() {
    return dragTarget;
  }

  // Setup event handler for file input
  private VBox setupFileInputActions() {
    VBox dragTarget = new VBox();
    dragTarget.getStyleClass().add("dragTarget");

    //source: https://stackoverflow.com/questions/32534113/javafx-drag-and-drop-a-file-into-a-program
    dragTarget.setOnDragOver(event -> {
      if (event.getGestureSource() != dragTarget
          && event.getDragboard().hasFiles()) {
        event.acceptTransferModes(TransferMode.ANY);
      }
      event.consume();
    });

    VBox inputBox = setupInputBox();
    dragTarget.getChildren().add(inputBox);

    dragTarget.setOnDragDropped(event -> {
      Dragboard db = event.getDragboard();
      if (db.hasFiles()) {
        if (validateFile(db.getFiles().get(0))) {
          setInputFileText(inputBox, db.getFiles().toString());
          event.setDropCompleted(true);
          displayDoneButton(dragTarget, db.getFiles());
        } else {
          event.setDropCompleted(false);
        }
      }
      event.consume();
    });

    return dragTarget;
  }

  // Checks to make sure file is a sim file
  protected boolean validateFile(File file) {
    String filename = file.toString();
    String[] extensions = myResources.getString("Extensions").split(",");
    String[] lengths = myResources.getString("Extension_Length").split(",");
    for (int i = 0; i < extensions.length; i++) {
      String extension = filename.substring(filename.length() - Integer.parseInt(lengths[i]));
      if (extension.equals(extensions[i])) {
        return true;
      }
    }
    util.showError("WRONG_FILE_EXTENSION");
    return false;
  }

  // Allows the user to input a file
  private void inputFile(FileChooser fileChooser, VBox inputBox) {
    File file = fileChooser.showOpenDialog(myScene.getWindow());
    if (file != null) {
      setInputFileText(inputBox, file.getPath());
      List<File> files = new ArrayList<>();
      files.add(file);
      displayDoneButton(getDragTarget(), files);
    }
  }

  //sets the text after the file has been input
  private void setInputFileText(VBox inputBox, String path) {
    Text text = new Text(path);
    inputBox.getChildren().set(2, text);
  }

  // Create and display done button
  protected void displayDoneButton(VBox dragTarget, List<File> files) {
    if (dragTarget.lookup("#DONE") == null) {
      Button doneButton = util.makeButton("DONE", e -> {
        try {
          myController.parseFile(files.get(0));
          showHomeGrid();
        } catch (InvalidColor | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | FileNotFoundException InvalidColorException) {
          util.showException(InvalidColorException);
        }
      });
      Node buttonBox = util.makeActions(doneButton);
      dragTarget.getChildren().add(buttonBox);
    }
  }

  // Creates the graphic for the input box
  private VBox setupInputBox() {
    VBox inputBox = new VBox();
    inputBox.getStyleClass().add("vbox");

    ImageView fileIcon = util.makeImageView(myResources.getString("Download_Icon"), "FILE_ICON");
    Label label = util.makeLabel("FILE_DRAG");
    Label dropped = util.makeLabel("DROPPED");
    Button fileBrowserButton = util.makeButton("BROWSE_FILE",
        event -> inputFile(myController.createFileBrowser(), inputBox));
    inputBox.getChildren().addAll(fileIcon, label, dropped, fileBrowserButton);
    return inputBox;
  }

  // Switches the display to the grid view
  private void showHomeGrid()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    GridView view = new GridView(LANGUAGE, myStage, myController);
    myStage.setScene(view.setupDisplay());
    myStage.setTitle(myResources.getString("Simulation_Title"));
    myStage.setMaximized(true);
    myStage.show();
  }
}
