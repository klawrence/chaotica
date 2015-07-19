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
    public static final double SafeDistance = 200;
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


    public List<Point2D.Double> compassPointsAtDistance(double distance) {
        ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
        for(int bearing = 0; bearing < 360; bearing += 45) {
            double x = robot.getX() + Angle.sin(bearing) * distance;
            double y = robot.getY() - Angle.cos(bearing) * distance;
            points.add(new Point2D.Double(x, y));
        }
        return points;
    }

    public Point2D.Double safestCompassPointWithin(double distance) {
        Rectangle2D.Double battleField = getSafeBattlefield();
        List<Point2D.Double> points = compassPointsAtDistance(distance);

        Point2D.Double safest = points.get(0);
        int minEnemiesWithRange = 999;

        for(Point2D.Double point : points){
            if(battleField.contains(point)){
                int enemiesWithinRange = countEnemiesNearPoint(point);
                if(enemiesWithinRange < minEnemiesWithRange) {
                    minEnemiesWithRange = enemiesWithinRange;
                    safest = point;
                }
            }
        }

        return safest;
    }

    public int countEnemiesNearBy() {
        return countEnemiesNearPoint(robot.getLocation());
    }

    public int countEnemiesNearPoint(Point2D.Double point) {
        int enemiesWithinRange = 0;
        for(Enemy enemy : enemies.values()) {
            if(enemy.distanceTo(point) < SafeDistance) {
                enemiesWithinRange += 1;
            }
        }
        return enemiesWithinRange;
    }

    private Rectangle2D.Double getSafeBattlefield() {
        return new Rectangle2D.Double(SafeDistance, SafeDistance, robot.getBattleFieldWidth() - SafeDistance, robot.getBattleFieldHeight() - SafeDistance);
    }

}
