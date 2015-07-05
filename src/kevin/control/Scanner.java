package kevin.control;

import kevin.adapters.Radar;
import kevin.adapters.Status;
import robocode.HitRobotEvent;
import robocode.RobotDeathEvent;
import robocode.RobotStatus;
import robocode.ScannedRobotEvent;

import java.util.HashMap;

public class Scanner {
    private final HashMap<String, Enemy> enemies;
    private final Radar radar;
    private Status robot;

    public Scanner(Radar radar, Status robot) {
        this.radar = radar;
        this.robot = robot;
        this.enemies = new HashMap<String, Enemy>();
    }

    public void fullSweep() {
        radar.setTurnRadarRight(360);
    }

    public Enemy getEnemy(String name) {
        Enemy enemy = enemies.get(name);
        if(enemy == null) {
            enemy = new Enemy(name, robot);
            enemies.put(name, enemy);
        }
        return enemy;
    }

    public void scanFor(Enemy target) {
        radar.setTurnRadarRight(360);
    }

    public Enemy onScannedRobot(ScannedRobotEvent event) {
        Enemy target = getEnemy(event.getName());
        target.update(event.getDistance(), event.getBearing(), event.getVelocity(), event.getHeading(), event.getEnergy(), event.getTime());
        return target;
    }

    public Enemy onHitRobot(HitRobotEvent event) {
        Enemy target = getEnemy(event.getName());
        target.update(robot.getWidth(), event.getBearing(), 0, 0, event.getEnergy(), event.getTime());
        return target;
    }

    public Enemy onRobotDeath(RobotDeathEvent event) {
        Enemy target = getEnemy(event.getName());
        enemies.remove(target.getName());
        return target;
    }
}
