package kevin.control;

import kevin.adapters.RobotControl;
import robocode.*;

import java.awt.*;

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
            boolean changeTarget = false;

            if(target == null) {
                changeTarget = true;
            }
            else if (enemy.energy < 20 && enemy.isClose()) {
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
//        logger.log("Collision", target);
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
        gunner.onBulletHit(event);
    }

    public void onBulletMissed(BulletMissedEvent event) {
        gunner.onBulletMissed(event);
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
        if(target != null && (scanner.getEnemyCount() == 1 || gunner.isGunNearlyCool()) ) {
            scanner.scanFor(target);
        }
        else {
            scanner.fullSweep();
        }
    }

    protected void checkStatus() {
        scanner.tidy();
        if(target != null && target.dead){
            target = null;
        }
    }
}
