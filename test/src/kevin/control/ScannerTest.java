package kevin.control;

import static org.junit.Assert.*;
import kevin.fakes.FakeRobot;
import kevin.fakes.FakeRadar;
import org.junit.Before;
import org.junit.Test;
import robocode.ScannedRobotEvent;

import java.awt.geom.Point2D;
import java.util.List;

public class ScannerTest {
    public static final int DELTA = 1;
    private FakeRadar radar;
    private FakeRobot robot;

    private Scanner scanner;

    @Before
    public void createScanner() {
        radar = new FakeRadar();
        robot = new FakeRobot();
        scanner = new Scanner(radar, robot);
    }

    @Test
    public void scanStoresEnemy() {
        int energy = 123;
        int distance = 250;
        int bearing = 0;
        int heading = 0;
        int velocity = 0;
        int time = 100;

        ScannedRobotEvent event = new ScannedRobotEvent("baddie", energy, bearing, distance, heading, velocity, false);
        event.setTime(time);
        scanner.onScannedRobot(event);

        Enemy enemy = scanner.getEnemy("baddie");
        assertEquals("baddie", enemy.getName());
        assertEquals(energy, enemy.energy, DELTA);
        assertEquals(distance, enemy.distance, DELTA);
        assertEquals(bearing, enemy.bearing, DELTA);
        assertEquals(heading, enemy.heading, DELTA);
        assertEquals(velocity, enemy.velocity, DELTA);
        assertEquals(time, enemy.time, DELTA);
    }

    @Test
    public void scanComputesRobotPositionFromOffset() {
        int bearing = 0;
        int distance = 150;
        int heading = 0;
        int velocity = 0;
        int energy = 123;
        int timeNow = 100;

        ScannedRobotEvent event = new ScannedRobotEvent("baddie", energy, bearing, distance, heading, velocity, false);
        event.setTime(timeNow);
        scanner.onScannedRobot(event);

        int atTime = 100;

        Enemy enemy = scanner.getEnemy("baddie");
        Position position = enemy.getPositionAtTime(atTime);
        assertEquals(500, position.getX(), DELTA);
        assertEquals(250, position.getY(), DELTA);
    }

    @Test
    public void compassPoints() {
        robot.x = 500;
        robot.y = 300;

        // Compass points clockwise from north
        List<Point2D.Double> points = scanner.compassPointsAtDistance(200);
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
        Point2D.Double point = scanner.safestCompassPointWithin(100);
        assertPoint(new Point2D.Double(80 + 100, 170.0), point);
    }

    @Test
    public void skipTheFirstCompassPointIfThereIsAnEnemyNearby() {
        robot.x = 80;
        robot.y = 170;

        Enemy enemy = new Enemy("Baddie", robot);
        enemy.x = 200;
        enemy.y = 150;
        scanner.enemies.put(enemy.getName(), enemy);

        // Skip North & northeast because they are outside the battle field
        // Skip East because of the enemy there
        // Go Southeast
        Point2D.Double point = scanner.safestCompassPointWithin(100);
        assertPoint(new Point2D.Double(80 + 100 * .707, 170.0 + 100 * .707), point);
    }

    private void assertPoint(Point2D.Double expected, Point2D.Double actual) {
        assertEquals(expected.x, actual.x, DELTA);
        assertEquals(expected.y, actual.y, DELTA);
    }
}
