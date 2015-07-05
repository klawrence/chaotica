package kevin.control;

import kevin.adapters.Status;
import robocode.HitRobotEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class Controller {
    private Status robot;
    private final Scanner scanner;
    private final Gunner gunner;
    private final Driver driver;
    private final Logger logger;
    private Enemy target;


    public Controller(Status robot, Scanner scanner, Gunner gunner, Driver driver, Logger logger) {
        this.robot = robot;
        this.scanner = scanner;
        this.gunner = gunner;
        this.driver = driver;
        this.logger = logger;
    }

    public void fight() {
        logger.log("");

        if(target != null ) {
            if(shouldRam(target)) {
                logger.log("Ram", target);
                driver.ram(target);
            }
            else {
                logger.log("Fire at", target);
                driver.headTowards(target);
            }
            gunner.fireAt(target);
        }

        if(target != null && gunner.isGunNearlyCool() ) {
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

    public void onScannedRobot(ScannedRobotEvent event) {
        Enemy enemy = scanner.onScannedRobot(event);
        if(target == null || enemy.distance < target.distance) {
            target = enemy;
        }
        logger.log("Scanned", enemy);
    }

    public void onHitRobot(HitRobotEvent event) {
        target = scanner.onHitRobot(event);
        logger.log("Collision", target);
    }

    public void onRobotDeath(RobotDeathEvent event) {
        Enemy enemy = scanner.onRobotDeath(event);
        logger.log("Dead", enemy);
        if(target == enemy) {
            logger.log("removed", target);
            target = null;
        }
    }
}
