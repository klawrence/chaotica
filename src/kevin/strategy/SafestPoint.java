package kevin.strategy;

import kevin.adapters.RobotControl;
import kevin.control.Enemy;
import kevin.geometry.Angle;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SafestPoint {
    public static final double SafeDistance = 100;

    private RobotControl robot;
    private Map<String, Enemy> enemies;

    public SafestPoint(RobotControl robot, Map<String, Enemy> enemies) {
        this.robot = robot;
        this.enemies = enemies;
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
