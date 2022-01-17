package cellsociety.View;

import cellsociety.Controller.Controller;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class for the application view that contains the histogram.
 *
 * @author Michelle Zhang
 */
public class HistogramView extends SimulationView {

  private final Controller myController;
  private final ResourceBundle histogramResources;

  /**
   * Constructor for histogram view.
   *
   * @param language   of the user
   * @param stage      window of the view
   * @param controller of the application
   */
  public HistogramView(String language, Stage stage, Controller controller) {
    super(language, stage, controller);
    myController = getController();
    histogramResources = ResourceBundle.getBundle(
        "cellsociety.View.resources.simulationAgentNames");
  }

  @Override
  public Scene setupDisplay()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Scene scene = super.setupDisplay();
    BorderPane root = (BorderPane) scene.getRoot();
    root.setCenter(setupHistogram());
    return scene;
  }

  // Creates bar chart for histogram
  private HBox setupHistogram() {
    BarChart<String, Number> histogram = new BarChart<>(setupXAxis(), setupYAxis());
    histogram.setId("histogram");
    histogram.setCategoryGap(2);

    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    series1.setName("population");

    String simulationType = myController.getSimFileKeys().get("Type").trim();
    String[] simulationAgents = histogramResources.getString(simulationType).split(",");
    for (String agent : simulationAgents) {
      series1.getData().add(new XYChart.Data<>(agent, 0));
    }
    histogram.getData().add(series1);
    animateHistogram(histogram);

    return new HBox(histogram);
  }

  // Makes histogram dynamically change with simulation
  private void animateHistogram(BarChart<String, Number> histogram) {
    Timeline timeline = new Timeline();
    timeline.getKeyFrames().add(
        new KeyFrame(Duration.millis(500),
            actionEvent -> {
              Map<Integer, Integer> myCountMap = myController.getCounts();
              Map<String, Integer> stringToInt = Map.of("Alive", 1, "Dead", 0, "Tree", 1,
                  "Burning", 2, "Agent1", 1, "Agent2", 2, "Prey", 1, "Predator", 2, "Blocked", 2,
                  "Water", 1);

              for (Series<String, Number> series : histogram.getData()) {
                for (Data<String, Number> data : series.getData()) {
                  data.setYValue(myCountMap.get(stringToInt.get(data.getXValue())));
                }
              }
            }
        ));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.setAutoReverse(true);
    timeline.play();
  }

  // Creates X axis with label
  private CategoryAxis setupXAxis() {
    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("Species");
    return xAxis;
  }

  // Creates Y axis with label
  private NumberAxis setupYAxis() {
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Population");
    return yAxis;
  }
}
