package kevin.control;

import kevin.adapters.RobotControl;
import kevin.strategy.GoToCorner;
import kevin.strategy.SafeDriving;

import java.awt.*;

public class ChaoticCommander extends Commander {

    private final SafeDriving safeDriving;
    private final GoToCorner goToCorner;
    private long sequence;

    public ChaoticCommander(RobotControl robot, Scanner scanner, Gunner gunner, Driver driver, Logger logger) {
        super(scanner, robot, logger, gunner, driver);

        safeDriving = new SafeDriving(robot, scanner.enemies);
        goToCorner = new GoToCorner(robot, scanner.enemies);

        bodyColor = Color.orange;
        gunColor = Color.red;
    }

    // TODO Better defensive manouevring
    // TODO Circular aiming
    // TODO 1 v 1

    public void fight() {
        sequence = robot.getTime() % 50;

        attack();
        scan();
        celebrate();
    }

    private void attack() {
        robot.setBodyColor(bodyColor);

        if(target != null ) {
            if(shouldRam(target)) {
                robot.setGunColor(Color.magenta);
                driver.ram(target);
            }
            else if (sequence < 20) {
                robot.setGunColor(gunColor);
                driver.headTowards(target);
            }
            else if (robot.getOthers() > 4) {
                robot.setGunColor(bodyColor);
                driver.driveTo(goToCorner.safestPoint());
            }
            else {
                robot.setGunColor(bodyColor);
                driver.driveToHeading(safeDriving.safestBearing());
            }

            if(shouldShoot(target)) {
                gunner.fireAt(target);
            }
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
