package kevin.control;

import kevin.adapters.RobotControl;
import kevin.strategy.SafeDriving;

import java.awt.*;

public class ChaoticCommander extends Commander {

    public ChaoticCommander(RobotControl robot, Scanner scanner, Gunner gunner, Driver driver, Logger logger) {
        super(scanner, robot, logger, gunner, driver);

        bodyColor = Color.orange;
        gunColor = Color.red;
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
            else {
                driver.headTowards(target);
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