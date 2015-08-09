package kevin.control;

import kevin.fakes.FakeRobot;
import kevin.fakes.FakeSteering;
import org.junit.Before;
import org.junit.Test;
import robocode.ScannedRobotEvent;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class DriverTest {
    public static final int DELTA = 1;
    private FakeSteering steering;
    private FakeRobot robot;

    private Driver driver;

    @Before
    public void createScanner() {
        Logger logger = new Logger(null);
        logger.enabled = false;

        steering = new FakeSteering();
        robot = new FakeRobot();
        driver = new Driver(robot, steering, logger);
    }

    @Test
    public void driveToCentre() {
        robot.x = (int) (robot.getBattleField().width / 2 + 100);
        robot.y = (int) (robot.getBattleField().height / 2 - 100);
        robot.heading = 45;

        driver.driveToCentre();
        assertEquals(141.4, steering.distance, DELTA);
        assertEquals(-90, steering.bearing, DELTA);
    }

    @Test
    public void driveToHeading() {
        robot.heading = 45;

        driver.driveToHeading(45);
        assertEquals(0, steering.bearing, DELTA);

        driver.driveToHeading(60);
        assertEquals(15, steering.bearing, DELTA);

        driver.driveToHeading(350);
        assertEquals(-55, steering.bearing, DELTA);

        robot.heading = 350;
        driver.driveToHeading(10);
        assertEquals(20, steering.bearing, DELTA);
    }

}
