package kevin.strategy;

import kevin.control.Enemy;
import kevin.control.Position;
import kevin.control.Scanner;
import kevin.fakes.FakeRadar;
import kevin.fakes.FakeRobot;
import org.junit.Before;
import org.junit.Test;
import robocode.ScannedRobotEvent;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SafeDrivingTest {
    public static final int DELTA = 1;
    private FakeRobot robot;
    private SafeDriving strategy;
    private HashMap<String, Enemy> enemies;

    @Before
    public void createStrategy() {
        robot = new FakeRobot();
        enemies = new HashMap<String, Enemy>();
        strategy = new SafeDriving(robot, enemies);
    }

    @Test
    public void bearingToFurthestWall() {
        robot.x = 100;
        robot.y = 100;
        assertEquals(120, strategy.safestBearing(), DELTA);

        robot.x = 800;
        robot.y = 100;
        assertEquals(240, strategy.safestBearing(), DELTA);

        robot.x = 800;
        robot.y = 700;
        assertEquals(300, strategy.safestBearing(), DELTA);
    }
}
