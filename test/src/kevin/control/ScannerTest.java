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
        Logger logger = new Logger(null);
        scanner = new Scanner(radar, robot, new Logger(null));
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

}
