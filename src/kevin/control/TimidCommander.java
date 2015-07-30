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
        scan();
        drive();
        shoot();
        celebrate();
        checkStatus();
    }

    protected void shoot() {
        if(target != null) {
            gunner.fireAt(target);
        }
    }

    protected void drive() {
        if(target != null && target.distance > 200){
            logger.log("attack", target);
            driver.headTowards(target);
        }
        else {
            driver.driveToHeading(safeDriving.safestBearing());
        }
    }
}
