package kevin.control;

import kevin.adapters.RobotControl;
import robocode.*;

import java.awt.*;

public class Commander {
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


    public Commander(RobotControl robot, Scanner scanner, Gunner gunner, Driver driver, Logger logger) {
        this.robot = robot;
        this.scanner = scanner;
        this.gunner = gunner;
        this.driver = driver;
        this.logger = logger;
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
        robot.setBodyColor(BodyColor);

        if(target != null ) {
            if(shouldRam(target)) {
                robot.setBodyColor(Color.magenta);
                driver.ram(target);
            }
            else {
                driver.headTowards(target);
            }
            gunner.fireAt(target);
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
            robot.setScanColor(getRainbow());
        }
    }

    public Color getRainbow() {
        return Color.getHSBColor((float) scanner.radar.getRadarHeading() * 13 % 100 / 100, 1, 1);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    private boolean shouldRam(Enemy target) {
        if(target.nearlyDead()) return true;
        if(scanner.getEnemyCount() > 1) return false;

        return target.energy < robot.getEnergy();
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        Enemy enemy = scanner.onScannedRobot(event);

        if(target != enemy){
            boolean changeTarget = false;

            if(target == null) {
                changeTarget = true;
            }
            else if (enemy.energy < 20 && enemy.distance < 200) {
                changeTarget = true;
            }
            else if(target.energy > 20 && enemy.distance < target.distance) {
                changeTarget = true;
            }

            if(changeTarget) {
                target = enemy;
                gunner.resetPower();
            }
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
            target = null;
            celebrationsRemaining = 30;
            gunner.resetPower();
        }
    }

    public void onHitWall(HitWallEvent event) {
        target = null;
        driver.drive(-robot.getWidth(), 0);
        logger.log("Hit the wall", event.getBearing());
    }

    public void onBulletHit(BulletHitEvent event) {
//        Enemy enemy = scanner.getEnemy(event.getName());
        gunner.onBulletHit(event);
//        logger.log("Hit", enemy);
    }

    public void onBulletMissed(BulletMissedEvent event) {
        gunner.onBulletMissed(event);
    }

    public void onRoundEnded(RoundEndedEvent event) {
        roundEnded = true;
    }
}
