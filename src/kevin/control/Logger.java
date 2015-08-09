package kevin.control;

import kevin.control.Enemy;
import robocode.AdvancedRobot;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

public class Logger {
    private final AdvancedRobot robot;
    public boolean enabled;

    public Logger(AdvancedRobot robot) {
        this.robot = robot;
    }

    public void log(String message, Enemy enemy) {
        log(String.format( "%s: \t%s", message, enemy));
    }

    public void log(String message, double value) {
        log(String.format( "%-12s: %.1f", message, value ));
    }

    public void log(String message, Point2D.Double point) {
        log(String.format( "%s: (%.1f, %.1f)", message, point.x, point.y));
    }

    public void log(String message) {
        if(enabled) robot.out.println(message);
    }

    public void log(Stats stats) throws IOException {
        if(enabled) stats.save(new PrintWriter(robot.out));
    }
}
