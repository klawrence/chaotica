package kevin.control;

import kevin.adapters.RobotControl;
import robocode.*;

import java.awt.*;

public class Controller {
    public static final Color BodyColor = Color.orange;
    public static final Color AlternateBodyColor = Color.red;
    public static final Color GunColor = Color.red;

    private RobotControl robot;
    private final Scanner scanner;
    private final Gunner gunner;
    private final Driver driver;
    private final Logger logger;
    private Enemy target;
    private int celebrationsRemaining;
    private boolean roundEnded;


    public Controller(RobotControl robot, Scanner scanner, Gunner gunner, Driver driver, Logger logger) {
        this.robot = robot;
        this.scanner = scanner;
        this.gunner = gunner;
        this.driver = driver;
        this.logger = logger;
    }

    // TODO If anyone is less than 20 health, kill them
    // TODO Aim ahead
    // TODO Better defensive manouevring
    // TODO better power escalation
    // TODO Circular aiming
    // TODO 1 v 1

    public void fight() {
        if(target != null ) {
            if(shouldRam(target)) {
                logger.log("Ram", target);
                driver.ram(target);
            }
            else {
                driver.headTowards(target);
            }
            gunner.fireAt(target);
        }

        if(driver.tooCloseToWall()) {
            driver.driveToCentre();
        }

        if(target != null && (scanner.getEnemyCount() == 1 || gunner.isGunNearlyCool()) ) {
            scanner.scanFor(target);
        }
        else {
            scanner.fullSweep();
        }

        celebrate();
    }

    private void celebrate() {
        if(celebrationsRemaining-- > 0) {
            switch (celebrationsRemaining % 3) {
                case 0:
                    robot.setBodyColor(BodyColor);
                    break;
                case 1:
                    robot.setBodyColor(Color.red);
                    break;
                case 2:
                    robot.setBodyColor(Color.black);
                    break;
            }
        }

        if(roundEnded){
            driver.driveToCentre();
            scanner.fullSweep();
            robot.setScanColor(Color.getHSBColor((float) scanner.radar.getRadarHeading() * 13 % 100 / 100, 1, 1));
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
            celebrationsRemaining = 30;
        }
    }

    public void onHitWall(HitWallEvent event) {
        target = null;
        driver.drive(-robot.getWidth(), 0);
        logger.log("Hit the wall");
    }

    public void onBulletHit(BulletHitEvent event) {
        Enemy enemy = scanner.getEnemy(event.getName());
        gunner.onBulletHit(event);
        logger.log("Hit", enemy);
    }

    public void onBulletMissed(BulletMissedEvent event) {
        gunner.onBulletMissed(event);
    }

    public void onRoundEnded(RoundEndedEvent event) {
        roundEnded = true;
    }
}
