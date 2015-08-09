package kevin.control;

import kevin.adapters.RobotControl;
import kevin.geometry.Angle;
import kevin.strategy.GoToCorner;
import kevin.strategy.SafeDriving;

import java.awt.*;
import java.awt.geom.Point2D;

public class ChaoticCommander extends Commander {

    private final SafeDriving safeDriving;
    private final GoToCorner goToCorner;
    private long sequence;

    public ChaoticCommander(RobotControl robot, Scanner scanner, Gunner gunner, Driver driver, Logger logger) {
        super(scanner, robot, logger, gunner, driver);

        safeDriving = new SafeDriving(robot, scanner);
        goToCorner = new GoToCorner(robot, scanner);

        bodyColor = Color.orange;
        gunColor = Color.red;
    }

    // TODO Virtual bullets: Directly at; aim ahead; split the difference
    // TODO fire at the weakest
    // TODO dodge when fired at in 1 v 1
    // TODO run away from strong robots if(hitMe > hits)

    public void fight() {
        sequence = robot.getTime() % 100;

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
            else if (sequence > 50 && robot.getEnergy() > 10) {
                robot.setGunColor(gunColor);
                driver.headTowards(target);
            }
            else {
                Point2D.Double corner = goToCorner.safestCorner();
                double bearingToCorner = Angle.bearingTo(new Point2D.Double(robot.getX(), robot.getY()), corner);

                if (Angle.differenceBetweenBearings(bearingToCorner, target.absoluteBearing) > 45) {
                    robot.setGunColor(Color.blue);
                    driver.driveTo(corner);
                }
                else {
                    robot.setGunColor(bodyColor);
                    driver.driveToHeading(safeDriving.safestBearing());
                }

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
