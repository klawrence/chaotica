package kevin.control;

import kevin.adapters.RobotAdapter;
import kevin.strategy.SafeDriving;

import java.awt.*;

public class TimidCommander extends Commander {
    private final SafeDriving safeDriving;

    public TimidCommander(RobotAdapter robot, Scanner scanner, Gunner gunner, Driver driver, Logger logger) {
        super(scanner, robot, logger, gunner, driver);

        safeDriving = new SafeDriving(robot, scanner.enemies);

        bodyColor = Color.lightGray;
        gunColor = Color.darkGray;
        radarColor = Color.lightGray;
        scanColor = Color.darkGray;
    }

    @Override
    public void fight() {
        target = null;

        scan();
        drive();
        celebrate();
        checkStatus();
    }

    protected void drive() {
        driver.driveToHeading(safeDriving.safestBearing());
    }
}
