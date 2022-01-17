package cellsociety.View.CellShape;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class HexagonTest {

  @Test
  void getPointsTest() {
    Hexagon myTestInstance = new Hexagon(0,0,3,3);
    List<Double> points = myTestInstance.getPoints();
    assertEquals(points.size(), 12);
    assertEquals(points.get(0), 1);
    assertEquals(points.get(1), 0);
    assertEquals(points.get(2), 2);
    assertEquals(points.get(3), 0);
    assertEquals(points.get(4), 3);
    assertEquals(points.get(5), 1.5);
    assertEquals(points.get(6), 2);
    assertEquals(points.get(7), 3);
    assertEquals(points.get(8), 1);
    assertEquals(points.get(9), 3);
    assertEquals(points.get(10), 0);
    assertEquals(points.get(11), 1.5);
  }

}