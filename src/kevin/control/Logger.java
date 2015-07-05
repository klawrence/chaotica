package kevin.control;

import kevin.control.Enemy;
import robocode.AdvancedRobot;

public class Logger {
    private final AdvancedRobot robot;

    public Logger(AdvancedRobot robot) {
        this.robot = robot;
    }

    public void log(String message, Enemy enemy) {
        log(message + " : " + enemy);
    }
    public void log(String message, double value) {
        log(message + " : " + Math.round(value));
    }

    public void log(String message) {
        robot.out.println(message);
    }
}
