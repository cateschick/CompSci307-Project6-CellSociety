package cellsociety.View.CellShape;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class RectangleTest {

  @Test
  void getPointsTest() {
    Rectangle myTestInstance = new Rectangle(0,0,1,1);
    List<Double> points = myTestInstance.getPoints();
    assertEquals(points.size(), 8);
    assertEquals(points.get(0), 0);
    assertEquals(points.get(1), 0);
    assertEquals(points.get(2), 1);
    assertEquals(points.get(3), 0);
    assertEquals(points.get(4), 1);
    assertEquals(points.get(5), 1);
    assertEquals(points.get(6), 0);
    assertEquals(points.get(7), 1);
  }
}