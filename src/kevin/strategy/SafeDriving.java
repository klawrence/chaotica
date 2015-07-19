package kevin.strategy;

import kevin.adapters.RobotControl;
import kevin.control.Enemy;
import kevin.geometry.Angle;

import java.util.Map;

public class SafeDriving {
    public static final int SectorAngle = 30;
    private RobotControl robot;
    private Map<String, Enemy> enemies;

    public SafeDriving(RobotControl robot, Map<String, Enemy> enemies) {
        this.robot = robot;
        this.enemies = enemies;
    }

    // Defined as: the bearing with the greatest distance before a collision with a robot or wall
    public double safestBearing() {
        int safestBearing = 0;
        double distanceToClosestObstacle = 0;

        for(int bearing = 0; bearing < 360; bearing += SectorAngle) {
            double closestWall = distanceToClosestWallOnBearing(bearing);
            double doubleNearestEnemy = nearestEnemyOnBearing(bearing);
            double closest = Math.min(closestWall, doubleNearestEnemy);

            if(closest > distanceToClosestObstacle) {
                distanceToClosestObstacle = closest;
                safestBearing = bearing;
            }
        }

        return safestBearing;
    }

    private double nearestEnemyOnBearing(int bearing) {
        double from = Angle.normalizeBearing(bearing - SectorAngle / 2);
        double to = Angle.normalizeBearing(bearing + SectorAngle / 2);
        double distanceToClosest = Double.MAX_VALUE;
        boolean adjust = false;
        if(from > 360 - SectorAngle) {
            adjust = true;
            from = from - 360;
        }

        for(Enemy enemy : enemies.values()) {
            double enemyBearing = enemy.absoluteBearing;
            if(enemy.distance < distanceToClosest) {
                if(adjust && enemyBearing > 360 - SectorAngle) {
                    enemyBearing = enemyBearing - 360;
                }
                if(enemyBearing > from && enemyBearing < to) {
                    distanceToClosest = enemy.distance;
                }
            }
        }

        return distanceToClosest;
    }

    // distanceToWall = deltaX / sin bearing = deltaY / cos bearing
    private double distanceToClosestWallOnBearing(double bearing) {
        double distanceToTopOrBottom;
        double distanceToLeftOrRight;

        if(bearing > 270 || bearing < 90) {
            distanceToTopOrBottom = robot.getY() / Angle.cos(bearing);
        }
        else {
            distanceToTopOrBottom = (robot.getBattleFieldHeight() - robot.getY()) / Angle.cos(bearing);
        }

        if(bearing > 180) {
            distanceToLeftOrRight = robot.getX() / Angle.sin(bearing);
        }
        else {
            distanceToLeftOrRight = (robot.getBattleFieldWidth() - robot.getX()) / Angle.sin(bearing);
        }

        return Math.min(Math.abs(distanceToLeftOrRight), Math.abs(distanceToTopOrBottom));
    }
}
