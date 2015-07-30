package kevin.control;

import kevin.adapters.Radar;
import kevin.adapters.RobotControl;
import kevin.geometry.Angle;
import robocode.HitRobotEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scanner {
    public final HashMap<String, Enemy> enemies;
    public final Radar radar;
    private RobotControl robot;

    public Scanner(Radar radar, RobotControl robot) {
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
        double offset = target.offsetToBearing(radar.getRadarHeading()) * 5;
        radar.setTurnRadarRight((int) offset);
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

    public int getEnemyCount() {
        return radar.getOthers();
    }

    public void tidy() {
        // Because sometimes we miss a robot death
        long now = robot.getTime();
        if(radar.getOthers() != enemies.size()) {
            List<String> dead =new ArrayList<String>();

            for(Enemy enemy : enemies.values()){
                if(now - enemy.time > 5){
                    enemy.dead = true;
                    dead.add(enemy.getName());
                }
            }

            for(String name : dead){
                enemies.remove(name);
            }
        }
    }

}
