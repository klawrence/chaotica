package kevin.control;

import kevin.adapters.Status;
import kevin.adapters.Steering;

public class Driver {
    private Status robot;
    private final Steering steering;

    public Driver(Status robot, Steering steering) {
        this.robot = robot;
        this.steering = steering;
    }

    public void headTowards(Enemy enemy) {
        drive(enemy.distance - robot.getWidth() * 2, enemy.bearing);
    }

    public void ram(Enemy enemy) {
        drive(enemy.distance + robot.getWidth(), enemy.bearing);
    }

    public void drive(double distance, double turn) {
        steering.setTurnRight(turn);
        steering.setAhead(distance);
    }
}
