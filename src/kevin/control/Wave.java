package kevin.control;

import kevin.adapters.RobotControl;
import kevin.geometry.Angle;

import java.awt.geom.Point2D;

public class Wave {
    private final RobotControl robot;
    private final Enemy target;
    private final double originalGunHeading;
    private final long firedAtTime;
    private final Point2D.Double start;
    private final double originalBearing;
    private final double bulletVelocity;

    public boolean complete;

    public Wave(RobotControl robot, Enemy target, double offsetToHit, double bulletVelocity) {
        this.robot = robot;
        this.target = target;

        this.start = robot.getLocation();
        this.bulletVelocity = bulletVelocity;
        this.firedAtTime = robot.getTime();

        this.originalBearing = target.absoluteBearing;
        this.originalGunHeading = originalBearing + offsetToHit;
    }

    public void update() {
        long interval = robot.getTime() - firedAtTime;
        double radius = bulletVelocity * interval;
        if(radius > target.distance) {
            complete = true;

            double originalFiringAngle = Angle.normalizeAngle(originalGunHeading - originalBearing);
            double correctGunHeading = Angle.bearingTo(start, target.location);
            double correctFiringAngle = Angle.normalizeAngle(correctGunHeading - originalBearing);
            boolean sameSign = originalFiringAngle * correctFiringAngle > 0;
            if(sameSign && Math.abs(correctFiringAngle) <= Math.abs(originalFiringAngle)) {
                double fudgeFactor = correctFiringAngle / originalFiringAngle;
                target.stats.updateFudgeFactor(fudgeFactor);
            }
        }

    }

}
