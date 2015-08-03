package kevin.geometry;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;

public class AngleTest {
    private static final double DELTA = 1.0;

    @Test
    public void angleBetween() {
        assertAngleBetween(10, 30, 20);
        assertAngleBetween(10, 170, 160);
        assertAngleBetween(10, 200, 170);
        assertAngleBetween(30, 350, 40);
    }

    @Test
    public void bearingOf() {
        Point2D.Double from = new Point2D.Double(500, 500);
        assertBearing(from, 500, 600, 0);
        assertBearing(from, 600, 600, 45);
        assertBearing(from, 600, 500, 90);
        assertBearing(from, 500, 400, 180);
        assertBearing(from, 400, 500, 270);
    }

    private void assertBearing(Point2D.Double from, int x, int y, double expected) {
        assertEquals(expected, Angle.bearingTo(from, new Point2D.Double(x, y)), DELTA);
    }

    protected void assertAngleBetween(int a, int b, int expected) {
        assertEquals(expected, Angle.differenceBetweenBearings(a, b), DELTA);
    }

}
