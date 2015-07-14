package kevin.control;

import kevin.adapters.Gun;
import kevin.adapters.RobotControl;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;

public class Gunner {
    public static final int GunBearingTolerance = 15;
    public static final double InitialPower = 0.1;

    private final Gun gun;
    private final FiringSolution solution;

    private RobotControl robot;
    private Logger logger;
    private double power;

    public Gunner(RobotControl robot, Gun gun, Logger logger) {
        this.robot = robot;
        this.gun = gun;
        this.logger = logger;
        this.power = InitialPower;
        this.solution = new FiringSolution();
    }

    public void fireAt(Enemy target) {
        double heading = solution.gunHeadingToHit(target, power);
        double offset = normalize(heading - gun.getGunHeading());

        gun.setTurnGunRight(offset);
        if(Math.abs(offset) < GunBearingTolerance && isGunCool()) {
            gun.setFire(power);
            reducePowerBy(0.1);
        }
    }

    private double normalize(double angle) {
        angle = angle % 360;
        if(angle < 180) return angle;
        return angle - 360;
    }


    public void resetPower() {
        power = InitialPower;
    }
    public boolean isGunCool() {
        return gun.getGunHeat() == 0;
    }

    public boolean isGunNearlyCool() {
        return gun.getGunHeat() / gun.getCoolingRate() < 5;
    }

    public void onBulletHit(BulletHitEvent event) {
        increasePowerBy(1);
    }

    public void onBulletMissed(BulletMissedEvent event) {
        reducePowerBy(0.2);
    }

    public void reducePowerBy(double delta) {
        increasePowerBy(-delta);
    }

    public void increasePowerBy(double delta) {
        power = Math.max(Math.min(power + delta, 4), 0.1);
    }
}
