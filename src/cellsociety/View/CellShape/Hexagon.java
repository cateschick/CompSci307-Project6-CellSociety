package cellsociety.View.CellShape;

public class Hexagon extends CellShape{

  public Hexagon(double x, double y, double width, double height) {
    super(x, y, width, height);
  }

  Double[] getPoints(double x, double y, double width, double height) {
    Double[] points = new Double[12];
    points[0] = x + width / 3;
    points[1] = y;
    points[2] = x + 2 * width / 3;
    points[3] = y;
    points[4] = x + width;
    points[5] = y + height / 2;
    points[6] = x + 2 * width / 3;
    points[7] = y + height;
    points[8] = x + width / 3;
    points[9] = y + height;
    points[10] = x;
    points[11] = y + height / 2;
    return points;
  }

}
