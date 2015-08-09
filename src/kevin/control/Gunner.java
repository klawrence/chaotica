package kevin.control;

import kevin.adapters.Gun;
import kevin.adapters.RobotControl;
import kevin.geometry.Angle;
import robocode.BulletMissedEvent;

public class Gunner {
    public static final int GunBearingTolerance = 10;

    private final Gun gun;
    private final FiringSolution solution;

    private RobotControl robot;
    private Logger logger;

    public Gunner(RobotControl robot, Gun gun, Logger logger) {
        this.robot = robot;
        this.gun = gun;
        this.logger = logger;
        this.solution = new FiringSolution();
    }

    public void fireAt(Enemy target) {
        double power = Math.min(target.powerToHit(), robot.getEnergy() / 2);

        double heading = solution.gunHeadingToHit(target, power);
        double offset = Angle.normalizeAngle(heading - gun.getGunHeading());

        gun.setTurnGunRight(offset);
        if(Math.abs(offset) < GunBearingTolerance && isGunCool() && ! isDisabled()) {
            gun.setFire(power);
            target.firedAt(power);
            logger.log("shoot", target);
        }
    }

    public boolean isGunCool() {
        return gun.getGunHeat() == 0;
    }

    public boolean isGunNearlyCool() {
        return gun.getGunHeat() / gun.getCoolingRate() < 5;
    }

    public void onBulletHit(Enemy target) {
        target.hit();
    }

    public boolean isDisabled() {
        return robot.getEnergy() < 0.2;
    }
}
