package kevin.control;

import kevin.adapters.Gun;
import kevin.fakes.FakeGun;
import kevin.fakes.FakeRobot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GunnerTest {
    private static final double DELTA = 1.0;

    private FakeRobot robot;
    private Enemy enemy;
    private FakeGun gun;
    private Gunner gunner;

    @Before
    public void createScenario() {
        robot = new FakeRobot();
        gun = new FakeGun();
        Logger logger = new Logger(null);
        enemy = new Enemy("baddie", robot);
        gunner = new Gunner(robot, gun, logger);
    }

    @Test
    public void shootStationaryEnemy() {
        enemy.distance = 100;
        enemy.bearing = 0;
        enemy.heading = 0;
        enemy.velocity = 0;

        gunner.fireAt(enemy);
        assertEquals(0, gun.turn, DELTA);
        assertTrue(gun.fired);
        assertEquals(0.1, gun.power, DELTA);
    }

    @Test
    public void dontShootIfGunIsTooHot() {
        enemy.distance = 100;
        enemy.bearing = 0;
        enemy.heading = 0;
        enemy.velocity = 0;

        gun.heat = 2;
        gun.heading = 0;

        gunner.fireAt(enemy);
        assertFalse(gun.fired);
        assertEquals(0, gun.turn, DELTA);
        assertEquals(0, gun.power, DELTA);
    }

    @Test
    public void turnGunAndDontShootIfHeadingIsWrong() {
        enemy.distance = 100;
        enemy.bearing = 0;
        enemy.heading = 0;
        enemy.velocity = 0;

        gun.heat = 0;
        gun.heading = 45;

        gunner.fireAt(enemy);
        assertFalse(gun.fired);
        assertEquals(-45, gun.turn, DELTA);
        assertEquals(0, gun.power, DELTA);
    }
}
