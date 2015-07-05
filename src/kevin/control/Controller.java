package kevin.control;

import kevin.adapters.Status;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

public class Controller {
    private final Status status;
    private final Scanner scanner;
    private final Gunner gunner;
    private final Driver driver;
    private final Logger logger;
    private Enemy target;


    public Controller(Status status, Scanner scanner, Gunner gunner, Driver driver, Logger logger) {
        this.status = status;
        this.scanner = scanner;
        this.gunner = gunner;
        this.driver = driver;
        this.logger = logger;
    }

    public void fight() {
        logger.log("");

        if(target != null) {
            logger.log("Fire at", target);
            driver.headTowards(target);
            gunner.fireAt(target);
        }

        if(target != null && gunner.isGunNearlyCool() ) {
            scanner.scanFor(target);
        }
        else {
            scanner.fullSweep();
        }
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        target = scanner.onScannedRobot(event);
        logger.log("Bearing", event.getBearing());
        logger.log("Scanned", target);
    }

    public void onHitRobot(HitRobotEvent event) {
        target = scanner.onHitRobot(event);
//        logger.log("Hit", target);
    }
}
