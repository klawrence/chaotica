package kevin.strategy;

import kevin.adapters.RobotControl;
import kevin.control.Enemy;
import kevin.geometry.Angle;

import java.awt.geom.Rectangle2D;
import java.util.Map;

public class SafeDriving {
    private RobotControl robot;
    private Map<String, Enemy> enemies;

    public SafeDriving(RobotControl robot, Map<String, Enemy> enemies) {
        this.robot = robot;
        this.enemies = enemies;
    }

    // Defined as: the bearing with the greatest distance before a collision with a robot or wall
    public double safestBearing() {
        int sectorAngle = 30;
        int safestBearing = 0;
        double distanceToClosestObstacle = 0;

        for(int bearing = 0; bearing < 360; bearing += sectorAngle) {
            int from = bearing - sectorAngle/2;
            int to = bearing + sectorAngle/2;

            double closest = distanceToClosestWallOnBearing(bearing);
            if(closest > distanceToClosestObstacle) {
                distanceToClosestObstacle = closest;
                safestBearing = bearing;
            }
        }

        return safestBearing;
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
