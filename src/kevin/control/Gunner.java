package kevin.control;

import kevin.adapters.Gun;
import kevin.adapters.RobotControl;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;

public class Gunner {
    public static final int GunBearingTolerance = 5;

    private final Gun gun;

    private RobotControl robot;
    private Logger logger;
    private double power;

    public Gunner(RobotControl robot, Gun gun, Logger logger) {
        this.robot = robot;
        this.gun = gun;
        this.logger = logger;
        this.power = 0.1;
    }

    public void fireAt(Enemy target) {
        gun.setTurnGunRight(getOffsetToTarget(target));
        if(isPointingAt(target) && isGunCool()) {
            gun.setFire(power);
        }
    }

    // cos(R) = Vt.t / Vb.t * cos(T)
    // cos(Hg - Bt) = Vt / Vb * cos(Bt + 180 - Ht)

    public boolean isPointingAt(Enemy target) {
        double offset = getOffsetToTarget(target);
        return Math.abs(offset) < GunBearingTolerance;
    }

    public boolean isGunCool() {
        return gun.getGunHeat() == 0;
    }

    public boolean isGunNearlyCool() {
        return gun.getGunHeat() / gun.getCoolingRate() < 5;
    }

    private double getOffsetToTarget(Enemy target) {
        return target.offsetToBearing(gun.getGunHeading());
    }

    public void onBulletHit(BulletHitEvent event) {
        power = Math.min(power + 1, 4);
    }

    public void onBulletMissed(BulletMissedEvent event) {
        power = Math.max(power / 2, 0.1);
    }
}
