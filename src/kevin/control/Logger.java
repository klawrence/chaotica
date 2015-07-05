package kevin.control;

import kevin.control.Enemy;
import robocode.AdvancedRobot;

import java.text.MessageFormat;

public class Logger {
    private final AdvancedRobot robot;
    public boolean enabled;

    public Logger(AdvancedRobot robot) {
        this.robot = robot;
    }

    public void log(String message, Enemy enemy) {
        log(message + " : " + enemy);
    }
    public void log(String message, double value) {
        log(String.format( "%s: %.1f", message, value ));
    }

    public void log(String message) {
        if(enabled) robot.out.println(message);
    }
}
