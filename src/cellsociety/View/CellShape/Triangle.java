package cellsociety.View.CellShape;

public class Triangle extends CellShape{

  public Triangle(double x, double y, double width, double height) {
    super(x, y, width, height);
  }

  Double[] getPoints(double x, double y, double width, double height) {
    Double[] points = new Double[6];
    points[0] = x + width / 2;
    points[1] = y;
    points[2] = x + width;
    points[3] = y + height;
    points[4] = x;
    points[5] = y + height;
    return points;
  }

}
