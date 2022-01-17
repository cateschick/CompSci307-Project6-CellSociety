package cellsociety.View;

import cellsociety.Controller.Controller;
import cellsociety.Controller.exceptions.InvalidColor;
import cellsociety.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class for the application view that contains the cell grid
 *
 * @author Kyle White
 * @author Michelle Zhang
 */
public class GridView extends SimulationView {

  private List<CellGrid> myGrids;
  private Timeline myAnimation;
  private String pauseState;
  private static final double SECOND_DELAY = 1;
  private List<Controller> myControllers;
  private final ResourceBundle myResources;
  private Scene myScene;
  private BorderPane root;

  /**
   * Constructor class that creates a cell grid and a timeline
   *
   * @param language Language of user
   */
  public GridView(String language, Stage stage, Controller controller) {
    super(language, stage, controller);
    myControllers = new ArrayList<>();
    myGrids = new ArrayList<>();
    myControllers.add(getController());
    myResources = new ViewUtil(language).getResources();
  }

  @Override
  public Scene setupDisplay()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myScene = super.setupDisplay();
    root = (BorderPane) myScene.getRoot();
    root.setCenter(setupGrid(Controller.getRows(), Controller.getCols()));
    root.setBottom(addDisplayButtons());
    createAnimation();
    return myScene;
  }

  // Creates grid for the scene
  private HBox setupGrid(int rows, int cols)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myGrids.add(new CellGrid(rows, cols, myControllers.get(0)));
    return new HBox(myGrids.get(0).getGridNode());
  }

  // Creates the pause and step button for the scene
  private HBox addDisplayButtons() {
    Button step = new Button(myResources.getString("Step"));
    step.setId("step");
    step.setOnAction(event -> step(step));
    step.setVisible(false);
    pauseState = "Pause";
    Button pause = new Button(myResources.getString("Pause"));
    pause.setId("pause");
    pause.setOnAction(value -> {
      try {
        Method m = this.getClass().getDeclaredMethod(pauseState, Button.class, Button.class);
        m.invoke(this, pause, step);
      } catch (Exception e) {
        throw new RuntimeException(
            String.format("%s: %s", myResources.getString("Reflection_Error"), pauseState), e);
      }
    });
    Button newSimulation = new Button(myResources.getString("NEW_SIMULATION"));
    newSimulation.setId("NEW_SIMULATION");
    newSimulation.setOnAction(event -> handleNewSideBySide(newSimulation));

    Button simulationStateButton = new Button(myResources.getString("SIMULATION_STATE"));
    simulationStateButton.setId("state");
    simulationStateButton.setOnAction(event -> {
      try {
        openSimulationStateView();
      } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
        getUtil().showException(e);
      }
    });

    Button newHistogram = new Button(myResources.getString("HISTOGRAM"));
    newHistogram.setId("HISTOGRAM");
    newHistogram.setOnAction(event -> {
      try {
        handleOpenHistogram();
      } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
        getUtil().showException(e);
      }
    });

//    Button settings = new Button(myResources.getString("SETTINGS_BUTTON"));
//    settings.setId("SETTINGS_BUTTON");
//    settings.setOnAction(event -> handleOpenSettings());

    return new HBox(addAnimationSlider(), pause, step, newSimulation, newHistogram,
        simulationStateButton);
  }

  // Handles opening settings view
//  private void handleOpenSettings() {
//    Stage newWindow = new Stage();
//    //same for this, change so you can get both sims?
//    SettingsView view = new SettingsView(Main.DEFAULT_LANGUAGE, newWindow,
//        myControllers.get(0));
//    newWindow.setScene(view.setupDisplay());
//    newWindow.setTitle(Main.TITLE);
//    newWindow.getIcons().add(new Image("images/happy.png"));
//    newWindow.show();
//  }

  // Handles Side by Side Functionality
  private void handleNewSideBySide(Button button) {
    myControllers.add(new Controller());
    FileChooser fileChooser = myControllers.get(1).createFileBrowser();
    File file = fileChooser.showOpenDialog(myScene.getWindow());
    if (file != null) {
      try {
        myControllers.get(1).parseFile(file);
        addSecondGrid();
      } catch (InvalidColor | IllegalAccessException | FileNotFoundException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException InvalidColorException) {
        getUtil().showException(InvalidColorException);
      }
    }
    button.setVisible(false);
  }

  // Opens new simulation state view
  private void openSimulationStateView()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Stage newWindow = new Stage();
    //same for this, change so you can get both sims?
    SimulationStateView view = new SimulationStateView(Main.DEFAULT_LANGUAGE, newWindow,
        myControllers.get(0));
    newWindow.setScene(view.setupDisplay());
    newWindow.setTitle(Main.TITLE);
    newWindow.getIcons().add(new Image("images/happy.png"));
    newWindow.show();
  }

  //opens histogram
  private void handleOpenHistogram()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Stage newWindow = new Stage();
    //change this so that you can open a histogram for both simulations?
    HistogramView view = new HistogramView(Main.DEFAULT_LANGUAGE, newWindow, myControllers.get(0));
    newWindow.setScene(view.setupDisplay());
    newWindow.setTitle(Main.TITLE);
    newWindow.getIcons().add(new Image("images/happy.png"));
    newWindow.show();
  }

  //Adds second grid to display
  private void addSecondGrid()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myGrids.add(new CellGrid(myControllers.get(1).getRows(), myControllers.get(1).getCols(),
        myControllers.get(1)));
    HBox temp = (HBox) root.getCenter();
    temp.getChildren().add(myGrids.get(1).getGridNode());
    root.setCenter(temp);
  }

  // Add animation slider
  private VBox addAnimationSlider() {
    Slider slider = new Slider(1, 5, 1);
    slider.setId("slider");
    Text sliderText = new Text(
        String.format("%s: %.2f", myResources.getString("Animation_Speed"), slider.getValue()));
    sliderText.setId("sliderText");
    slider.valueProperty().addListener((ov, t, t1) -> {
      sliderText.setText(String.format("%s: %.2f", myResources.getString("Animation_Speed"), t1));
      myAnimation.setRate(slider.getValue());
    });
    return new VBox(slider, sliderText);
  }

  // Pauses timeline and changes text of pause button
  private void Pause(Button pause, Button step) {
    pauseState = "Resume";
    step.setVisible(true);
    pause.setText(myResources.getString(pauseState));
    myAnimation.pause();
  }

  // Resumes timeline and changes text of pause button
  private void Resume(Button pause, Button step) {
    pauseState = "Pause";
    step.setVisible(false);
    pause.setText(myResources.getString(pauseState));
    myAnimation.play();
  }

  //Update the cell grid over time
  private void step() {
    for (int i = 0; i < Controller.getRows(); i++) {
      for (int j = 0; j < Controller.getCols(); j++) {
        for (int k = 0; k < myGrids.size(); k++) {
          myGrids.get(k).update(i, j, myControllers.get(k).getState(i, j));
        }
      }
    }
    for (int k = 0; k < myGrids.size(); k++) {
      myControllers.get(k).onePass();
    }
  }

  //Update the cell grid over time
  private void step(Button s) {
    s.setVisible(false);
    step();
    s.setVisible(true);
  }

  // Creates Timeline for application
  private void createAnimation() {
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step()));
    myAnimation.play();
  }
}
