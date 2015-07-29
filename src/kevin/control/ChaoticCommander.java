package kevin.control;

import kevin.adapters.RobotControl;
import kevin.strategy.SafeDriving;

import java.awt.*;

public class ChaoticCommander extends Commander {
    public static final Color BodyColor = Color.orange;
    public static final Color GunColor = Color.red;

    private final SafeDriving safeDriving;


    public ChaoticCommander(RobotControl robot, Scanner scanner, Gunner gunner, Driver driver, Logger logger) {
        super(scanner, robot, logger, gunner, driver);

        safeDriving = new SafeDriving(robot, scanner.enemies);
        bodyColor = Color.orange;
    }

    // TODO Better defensive manouevring
    // TODO Circular aiming
    // TODO 1 v 1

    public void fight() {
        attack();
        avoid();
        scan();
        celebrate();
        checkStatus();
    }

    private void checkStatus() {
        scanner.tidy();
        if(target != null && target.dead){
            target = null;
        }
    }

    private void avoid() {
        if(driver.tooCloseToWall()) {
            driver.avoidTheWall();
        }
    }

    private void attack() {
        robot.setBodyColor(bodyColor);

        if(target != null ) {
            if(shouldRam(target)) {
                robot.setBodyColor(Color.magenta);
                driver.ram(target);
            }
//            else if(scanner.getEnemyCount() > 3) {
//                double heading = safeDriving.safestBearing();
//                logger.log("safe heading", heading);
//                driver.driveToHeading(heading);
//            }
            else {
//                logger.log("target", target);
                driver.headTowards(target);
            }
            if(shouldShoot(target)) {
                gunner.fireAt(target);
            }
        }
    }

    private void scan() {
        if(target != null && (scanner.getEnemyCount() == 1 || gunner.isGunNearlyCool()) ) {
            scanner.scanFor(target);
        }
        else {
            scanner.fullSweep();
        }
    }

    @SuppressWarnings("SimplifiableIfStatement")
    private boolean shouldRam(Enemy target) {
        if(target.nearlyDead()) return true;
        if(scanner.getEnemyCount() > 1) return false;

        return target.energy < robot.getEnergy();
    }

    private boolean shouldShoot(Enemy target) {
        return !( target.nearlyDead() && target.isVeryClose() && target.energy < robot.getEnergy());
    }

}
