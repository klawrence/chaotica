package kevin.control;

import kevin.adapters.Gun;
import kevin.adapters.Status;

public class Gunner {
    public static final int GunBearingTolerance = 5;

    private Status robot;
    private final Gun gun;
    private Logger logger;

    public Gunner(Status robot, Gun gun, Logger logger) {
        this.robot = robot;
        this.gun = gun;
        this.logger = logger;
    }

    public void fireAt(Enemy target) {
        gun.setTurnGunRight(getOffsetToTarget(target));
        if(isPointingAt(target) && isGunCool()) {
          gun.setFire(1);
            // todo increase the gun power with increasing confidence
        }
    }

    public boolean isPointingAt(Enemy target) {
        double offset = getOffsetToTarget(target);

        logger.log("Heading", robot.getHeading());
        logger.log("Bearing", target.getAbsoluteBearing());
        logger.log("Gun Heading", gun.getGunHeading());
        logger.log("Temp", gun.getGunHeat());
        logger.log("Offset", offset);

        return Math.abs(offset) < GunBearingTolerance;
    }

    public boolean isGunCool() {
        return gun.getGunHeat() == 0;
    }

    public boolean isGunNearlyCool() {
        return gun.getGunHeat() / gun.getCoolingRate() < 5;
    }

    private double getOffsetToTarget(Enemy target) {
        return target.getAbsoluteBearing() - gun.getGunHeading();
    }

}
