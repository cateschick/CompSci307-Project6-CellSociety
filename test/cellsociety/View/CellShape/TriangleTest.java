package cellsociety.View.CellShape;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class TriangleTest {

  @Test
  void getPointsTest() {
    Triangle myTestInstance = new Triangle(0,0,1,1);
    List<Double> points = myTestInstance.getPoints();
    assertEquals(points.size(), 6);
    assertEquals(points.get(0), 0.5);
    assertEquals(points.get(1), 0);
    assertEquals(points.get(2), 1);
    assertEquals(points.get(3), 1);
    assertEquals(points.get(4), 0);
    assertEquals(points.get(5), 1);
  }
}