package kevin.control;

import kevin.adapters.RobotControl;
import kevin.adapters.Steering;

import java.awt.geom.Point2D;

public class Driver {
    private final RobotControl robot;
    private final Steering steering;
    private final Logger logger;
    private final double BearingOffset = 50;
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
        if(turn > 180) turn -= 360;
        else if(turn < -180) turn += 360;

        if(turn > 90){
            turn -= 180;
            distance = -distance;
        }
        else if(turn < -90){
            turn += 180;
            distance = -distance;
        }

//        logger.log("distance", distance);
//        logger.log("turn", turn);

        steering.setTurnRight(turn);
        steering.setAhead(distance);
    }

    public double distanceToWall() {
        return Math.min(
                Math.min(robot.getX(), robot.getBattleField().width - robot.getX()),
                Math.min(robot.getY(), robot.getBattleField().height - robot.getY())
        );
    }

    public void driveToCentre() {
        driveTo(robot.centre());
    }

    public void driveToHeading(double heading) {
        double turn = heading - robot.getHeading();
        double speed = CruisingSpeed;
        drive(speed, turn);
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

    public void avoidTheWall() {
        driveToCentre();
    }

}
