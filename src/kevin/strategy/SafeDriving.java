package kevin.strategy;

import kevin.adapters.RobotControl;
import kevin.control.Enemy;
import kevin.control.Scanner;
import kevin.geometry.Angle;

import java.util.Map;

public class SafeDriving {
    private RobotControl robot;
    private Scanner scanner;

    public SafeDriving(RobotControl robot, Scanner scanner) {
        this.robot = robot;
        this.scanner = scanner;
    }

    // Defined as: the bearing with the greatest distance before a collision with a robot or wall
    public double safestBearing() {
        int safestBearing = 0;
        double distanceToClosestObstacle = 0;

        for(int sector : scanner.sectors()) {
            double closestWall = distanceToClosestWallOnBearing(sector);
            double doubleNearestEnemy = scanner.nearestEnemyOnBearing(sector);
            double closest = Math.min(closestWall, doubleNearestEnemy);

            if(closest > distanceToClosestObstacle) {
                distanceToClosestObstacle = closest;
                safestBearing = sector;
            }
        }

        return safestBearing;
    }

    // distanceToWall = deltaX / sin bearing = deltaY / cos bearing
    public double distanceToClosestWallOnBearing(double bearing) {
        double distanceToTopOrBottom;
        double distanceToLeftOrRight;

        if(bearing > 270 || bearing < 90) {
            distanceToTopOrBottom = (robot.getBattleFieldHeight() - robot.getY()) / Angle.cos(bearing);
        }
        else {
            distanceToTopOrBottom = robot.getY() / Angle.cos(bearing);
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
