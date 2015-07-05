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
        steering.setTurnRight(enemy.bearing);
        steering.setAhead(enemy.distance - robot.getWidth());
    }
}
