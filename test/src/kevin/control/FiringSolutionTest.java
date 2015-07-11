package kevin.control;

import kevin.fakes.FakeRobot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FiringSolutionTest {
    private static final double DELTA = 1.0;

    private FakeRobot robot;
    private Enemy enemy;
    private FiringSolution solution;

    @Before
    public void createScenario() {
        robot = new FakeRobot();
        enemy = new Enemy("baddie", robot);
        solution = new FiringSolution();
    }

    @Test
    public void shootStationaryEnemy() {
        enemy.absoluteBearing = 0;
        enemy.distance = 100;
        enemy.heading = 0;
        enemy.velocity = 0;

        double heading = solution.gunHeadingToHit(enemy, 4);
        assertEquals(0, heading, DELTA);
    }

    @Test
    public void shootEnemyTravellingLeftToRight() {
        enemy.absoluteBearing = 0;
        enemy.distance = 100;
        enemy.heading = 90;
        enemy.velocity = 4;

        double heading = solution.gunHeadingToHit(enemy, 4);
        assertEquals(30, heading, DELTA);
    }

    @Test
    public void shootOffsetEnemyTravellingLeftToRight() {
        enemy.absoluteBearing = 45;
        enemy.distance = 141;
        enemy.heading = 135;
        enemy.velocity = 4;

        double heading = solution.gunHeadingToHit(enemy, 4);
        assertEquals(75, heading, DELTA);
    }
}
