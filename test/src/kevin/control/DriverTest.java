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
        steering = new FakeSteering();
        robot = new FakeRobot();
        driver = new Driver(robot, steering);
    }

    @Test
    public void driveToCentre() {
        robot.x = (int) (robot.getBattleFieldWidth() / 2 + 100);
        robot.y = (int) (robot.getBattleFieldHeight() / 2 - 100);
        robot.heading = 45;

        driver.driveToCentre();
        assertEquals(141.4, steering.distance, DELTA);
        assertEquals(-90, steering.bearing, DELTA);
    }

}
