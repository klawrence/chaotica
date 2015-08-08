package kevin.control;

import kevin.adapters.RobotControl;
import robocode.*;

import java.awt.*;
import java.io.IOException;

public abstract class Commander {
    protected final RobotControl robot;
    protected final Scanner scanner;
    protected final Gunner gunner;
    protected final Driver driver;
    protected final Logger logger;

    public Color bodyColor;
    public Color gunColor;
    public Color radarColor;
    public Color scanColor;
    public Color bulletColor;

    protected Enemy target;
    protected int celebrationsRemaining;
    protected boolean roundEnded;

    public Commander(Scanner scanner, RobotControl robot, Logger logger, Gunner gunner, Driver driver) {
        this.scanner = scanner;
        this.robot = robot;
        this.logger = logger;
        this.gunner = gunner;
        this.driver = driver;

        bodyColor = Color.black;
        radarColor = Color.black;
        gunColor = Color.black;
        scanColor = Color.white;
        bulletColor = Color.red;
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        Enemy enemy = scanner.onScannedRobot(event);

        if(target != enemy){
            if(target == null) {
                target = enemy;
            }
            else if (enemy.energy < 20 && enemy.isClose()) {
                target = enemy;
            }
            else if(enemy.isBetterTargetThan(target)) {
                target = enemy;
            }
        }
    }

    public void onHitRobot(HitRobotEvent event) {
        target = scanner.onHitRobot(event);
    }

    public void onRobotDeath(RobotDeathEvent event) {
        Enemy enemy = scanner.onRobotDeath(event);
        logger.log("Dead", enemy);
        if(target == enemy) {
            target = null;
            celebrationsRemaining = 30;
        }
    }

    public void onHitWall(HitWallEvent event) {
        target = null;
        driver.drive(-robot.getWidth(), 0);
        logger.log("Hit the wall", event.getBearing());
    }

    public void onBulletHit(BulletHitEvent event) {
        Enemy enemy = scanner.getEnemy(event.getName());
        gunner.onBulletHit(enemy);
        logger.log("Hit", enemy);
    }

    public void onBulletMissed(BulletMissedEvent event) {
        // Todo keep a record of which bullet was fired at which robot
    }

    public void onHitByBullet(HitByBulletEvent event) {
        Enemy enemy = scanner.getEnemy(event.getName());
        enemy.shotMe(); // Record the power too?
        // How do I know who killed me?
    }

    public void onRoundEnded(RoundEndedEvent event) {
        roundEnded = true;
    }


    public abstract void fight();

    protected void celebrate() {
        if(celebrationsRemaining-- > 0) {
            switch (celebrationsRemaining % 3) {
                case 0:
                    robot.setBodyColor(bodyColor);
                    break;
                case 1:
                    robot.setBodyColor(bodyColor);
                    break;
                case 2:
                    robot.setBodyColor(Color.cyan);
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

    protected void scan() {
        if(target != null && (target.dead || robot.getTime() - target.time > 10)) {
            target = null;  // Get rid of stale targets in case we missed an event
        }

        if(target != null && (robot.getOthers() == 1 || gunner.isGunNearlyCool()) ) {
            scanner.scanFor(target);
        }
        else {
            scanner.fullSweep();
        }
    }

    public void saveStats(RobocodeFileWriter file) throws IOException {
        Scanner.stats.save(file);
    }

}
