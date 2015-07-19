package kevin.control;

import kevin.adapters.RobotControl;
import kevin.adapters.Steering;

import java.awt.geom.Point2D;

public class Driver {
    private final RobotControl robot;
    private final Steering steering;
    private final Logger logger;
    private final double BearingOffset = 30;
    private final double CruisingSpeed = 50;

    public Driver(RobotControl robot, Steering steering, Logger logger) {
        this.robot = robot;
        this.steering = steering;
        this.logger = logger;
    }

    public void headTowards(Enemy enemy) {
        drive(enemy.distance - robot.getWidth() * 2, enemy.bearing + BearingOffset);
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
        driveTo(centre());
    }

    public void driveToHeading(double heading) {
        drive(heading - robot.getHeading(), CruisingSpeed);
    }

    public void driveTo(Point2D.Double point) {
        double distance = distanceTo(point);
        double turn = headingTo(point) - robot.getHeading();
        drive(distance, turn);
    }

    public double distanceTo(Point2D.Double centre) {
        return centre.distance(new Point2D.Double(robot.getX(), robot.getY()));
    }


    public double headingTo(Point2D point) {
        return Math.atan2(point.getX() - robot.getX(), point.getY() - robot.getY()) * 180 / Math.PI;
    }

    public Point2D.Double centre() {
        return new Point2D.Double(robot.getBattleFieldWidth()/2, robot.getBattleFieldHeight()/2);
    }

    public void avoidTheWall() {
        driveToCentre();
    }

}
