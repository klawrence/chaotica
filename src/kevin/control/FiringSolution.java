package kevin.control;

import kevin.adapters.RobotControl;
import kevin.geometry.Angle;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FiringSolution {

    private final List<Wave> waves;
    private final RobotControl robot;
    private Logger logger;

    public FiringSolution(RobotControl robot, Logger logger) {
        this.robot = robot;
        this.logger = logger;
        waves = new ArrayList<Wave>();
    }

    // sin(R) = Vt.t / Vb.t * sin(T)
    // sin(Hg - Bt) = Vt / Vb * sin(Bt + 180 - Ht)

    public double gunHeadingToHit(Enemy target, double power) {
        double bulletVelocity = bulletVelocity(power);
        double heading = target.averageHeadingChange() > 1 || robot.getOthers() == 1 // ONly use circular targeting at the end
                ? linearSolution(target, bulletVelocity)
                : circularSolution(target, bulletVelocity);

//        Wave implementation has a bug
//        waves.add(new Wave(robot, target, offsetToHit, bulletVelocity));

        return heading;
    }

    private double circularSolution(Enemy target, double bulletVelocity) {
        Point2D.Double origin = robot.getLocation();
        double x = target.x;
        double y = target.y;
        double t = 0;
        double heading = target.heading;
        double headingChange = target.averageHeadingChange();
        double range = target.distance;
        double bulletDistance = 0;

        do {
            bulletDistance += bulletVelocity;

            heading += headingChange;
            x += target.velocity * Angle.sin(heading);
            y += target.velocity * Angle.cos(heading);
            range = origin.distance(x, y);

        } while(bulletDistance < range && t++ < 100);

        Point2D.Double intercept = new Point2D.Double(x, y);
        double solution = Angle.bearingTo(origin, intercept);

//        logger.log("position", target.location);
//        logger.log("circular", intercept);
//
//        logger.log("bearing", target.absoluteBearing);
//        logger.log("solution", solution);
//
        return solution;
    }

    protected double linearSolution(Enemy target, double bulletVelocity) {
        double firingAngle = target.absoluteBearing + 180 - target.heading;

        return target.absoluteBearing + Angle.asin(target.velocity / bulletVelocity * Angle.sin(firingAngle));
    }

    // Vt.t = d.sin(Hg-Bt)/sin(Ht-Hg)
    // δx = Vt.t * sin(180 - Ht)
    // δy = Vt.t * cos(180 - Ht)
    public boolean isImpactInBounds(double gunHeading, Enemy target, Rectangle2D.Double battlefield) {
        double Vt_t = target.distance * Angle.sin(gunHeading - target.absoluteBearing);
        double δx = Vt_t * Angle.sin(180 - target.heading);
        double δy = Vt_t * Angle.cos(180 - target.heading);

        Point2D.Double impactPoint = new Point2D.Double(target.x + δx, target.y + δy);
        return battlefield.contains(impactPoint);
    }

    protected double bulletVelocity(double power) {
        return 20 - 3 * power;
    }

    public void update() {
        Iterator<Wave> iterator = waves.iterator();
        while(iterator.hasNext()) {
            Wave wave = iterator.next();
            wave.update();
            if(wave.complete){
                iterator.remove();
            }
        }
    }
}
