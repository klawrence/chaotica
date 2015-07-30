package kevin.strategy;

import kevin.control.Enemy;
import kevin.fakes.FakeRobot;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SafestPointTest {
    public static final int DELTA = 1;
    private FakeRobot robot;
    private SafestPoint strategy;
    private HashMap<String, Enemy> enemies;

    @Before
    public void createStrategy() {
        robot = new FakeRobot();
        enemies = new HashMap<String, Enemy>();
        strategy = new SafestPoint(robot, enemies);
    }

    @Test
    public void compassPoints() {
        robot.x = 500;
        robot.y = 300;

        // Compass points clockwise from north
        List<Point2D.Double> points = strategy.compassPointsAtDistance(200);
        assertEquals(8, points.size());
        assertPoint(new Point2D.Double(500.0, 300.0 - 200), points.get(0));
        assertPoint(new Point2D.Double(500.0 + 200 * .707, 300.0 - 200 * .707), points.get(1));
        assertPoint(new Point2D.Double(500.0 + 200, 300.0), points.get(2));
    }

    @Test
    public void safestCompassPointWithNoEnemiesIsFirstOneOnBattlefield() {
        robot.x = 80;
        robot.y = 170;

        // Skip North & northeast because they are outside the battle field
        Point2D.Double point = strategy.safestCompassPointWithin(100);
        assertPoint(new Point2D.Double(80 + 100, 170.0), point);
    }

    @Test
    public void skipTheFirstCompassPointIfThereIsAnEnemyNearby() {
        robot.x = 80;
        robot.y = 170;

        Enemy enemy = new Enemy("Baddie", robot);
        enemy.x = 200;
        enemy.y = 150;
        enemies.put(enemy.getName(), enemy);

        // Skip North & northeast because they are outside the battle field
        // Skip East because of the enemy there
        // Go Southeast
        Point2D.Double point = strategy.safestCompassPointWithin(100);
        assertPoint(new Point2D.Double(80 + 100 * .707, 170.0 + 100 * .707), point);
    }

    private void assertPoint(Point2D.Double expected, Point2D.Double actual) {
        assertEquals(expected.x, actual.x, DELTA);
        assertEquals(expected.y, actual.y, DELTA);
    }
}
