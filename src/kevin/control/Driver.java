package kevin.control;

import kevin.adapters.RobotControl;
import kevin.adapters.Steering;

import java.awt.geom.Point2D;

public class Driver {
    private RobotControl robot;
    private final Steering steering;

    public Driver(RobotControl robot, Steering steering) {
        this.robot = robot;
        this.steering = steering;
    }

    public void headTowards(Enemy enemy) {
        drive(enemy.distance - robot.getWidth() * 2, enemy.bearing);
    }

    public void ram(Enemy enemy) {
        drive(enemy.distance + robot.getWidth(), enemy.bearing);
    }

    /**
     * Turn is relative to the current heading
     */
    public void drive(double distance, double turn) {
        steering.setTurnRight(turn);
        steering.setAhead(distance);
    }

    public boolean tooCloseToWall() {
        return distanceToWall() < robot.getWidth() * 2;
    }

    public double distanceToWall() {
        return Math.min(
                Math.min(robot.getX(), robot.getBattleFieldWidth() - robot.getX()),
                Math.min(robot.getY(), robot.getBattleFieldHeight() - robot.getY())
        );
    }

    public void driveToCentre() {
        Point2D.Double centre = centre();
        drive(distanceTo(centre), headingTo(centre) - robot.getHeading());
    }

    public double distanceTo(Point2D.Double centre) {
        return centre.distance(new Point2D.Double(robot.getX(), robot.getY()));
    }


    public double headingTo(Point2D centre) {
        return Math.atan2(centre.getX() - robot.getX(), centre.getY() - robot.getY()) * 180 / Math.PI;
    }

    public Point2D.Double centre() {
        return new Point2D.Double(robot.getBattleFieldWidth()/2, robot.getBattleFieldHeight()/2);
    }

    public void avoidTheWall() {
        // TODO make this better
        // Try turning in the safest direction
        driveToCentre();
    }
}
