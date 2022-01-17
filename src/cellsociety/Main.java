package cellsociety;

import cellsociety.Controller.Controller;
import cellsociety.View.FileInputView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main class
 *
 * @author Kyle White
 * @author Michelle Zhang
 * @author Cate Schick
 * @author Samuel Li
 */
public class Main extends Application {

  /**
   * Title of application.
   */
  public static final String TITLE = "CELLULAR AUTOMATA SIMULATION";
  /**
   * Default language of application.
   */
  public static final String DEFAULT_LANGUAGE = "English";

  /**
   * Launches application and calls start method.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Creates File view and displays it.
   *
   * @param stage Stage of the viewer.
   */
  @Override
  public void start(Stage stage) {
    Controller controller = new Controller();
    FileInputView view = new FileInputView(DEFAULT_LANGUAGE, controller);
    stage.setScene(view.setUpFileInput(stage));
    stage.setTitle(TITLE);
    stage.setMaximized(true);
    stage.getIcons().add(new Image("images/happy.png"));
    stage.show();
  }
}
