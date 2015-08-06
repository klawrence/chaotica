package kevin.control;

import kevin.fakes.FakeRobot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnemyTest {
    public static final int DELTA = 1;
    private Enemy enemy;
    private FakeRobot status;
    private EnemyStats stats;

    @Before
    public void createScanner() {
        status = new FakeRobot();
        stats = new EnemyStats("Baddie");
        enemy = new Enemy("Baddie", status, stats);
    }

    @Test
    public void updateEnemyLocation() {
        status.x = 100;
        status.y = 150;
        status.heading = 30;

        enemy.update(141, 15, 10, 90, 100, 123);

        assertEquals(141, enemy.distance, DELTA);
        assertEquals(15, enemy.bearing, DELTA);
        assertEquals(45, enemy.absoluteBearing, DELTA);
        assertEquals(200, enemy.x, DELTA);
        assertEquals(50, enemy.y, DELTA);
    }
}
