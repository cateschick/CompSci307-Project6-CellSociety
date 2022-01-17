package cellsociety.View.CellShape;

public class Rectangle extends CellShape{

  public Rectangle(double x, double y, double width, double height) {
    super(x, y, width, height);
  }

  Double[] getPoints(double x, double y, double width, double height) {
    Double[] points = new Double[8];
    points[0] = x;
    points[1] = y;
    points[2] = x + width;
    points[3] = y;
    points[4] = x + width;
    points[5] = y + height;
    points[6] = x;
    points[7] = y + height;
    return points;
  }
}
