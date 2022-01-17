package cellsociety.View.CellShape;

import javafx.scene.shape.Polygon;

public abstract class CellShape extends Polygon {

  public CellShape(double x, double y, double width, double height) {
    Double[] points = getPoints(x, y, width, height);
    this.getPoints().addAll(points);
  }

  //Gets points for polygon
  abstract Double[] getPoints(double x, double y, double width, double height);
}
