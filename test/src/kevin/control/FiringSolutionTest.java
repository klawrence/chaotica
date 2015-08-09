package kevin.control;

import kevin.fakes.FakeRobot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FiringSolutionTest {
    private static final double DELTA = 1.0;

    private FakeRobot robot;
    private Enemy enemy;
    private FiringSolution solution;
    private EnemyStats stats;

    @Before
    public void createScenario() {
        robot = new FakeRobot();
        stats = new EnemyStats("Baddie");
        enemy = new Enemy("baddie", robot, stats);
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
        enemy.heading = 135;
        enemy.velocity = 4;

        double heading = solution.gunHeadingToHit(enemy, 4);
        assertEquals(75, heading, DELTA);

        enemy.distance = 400;
        enemy.x = 200;
        enemy.y = 500;
        assertTrue(solution.isImpactInBounds(heading, enemy, robot.getBattleField()));

        enemy.x = 900;
        enemy.y = 500;
        assertFalse(solution.isImpactInBounds(heading, enemy, robot.getBattleField()));
    }
}
