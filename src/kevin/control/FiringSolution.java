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

    public FiringSolution(RobotControl robot) {
        this.robot = robot;
        waves = new ArrayList<Wave>();
    }

    // sin(R) = Vt.t / Vb.t * sin(T)
    // sin(Hg - Bt) = Vt / Vb * sin(Bt + 180 - Ht)

    public double gunHeadingToHit(Enemy target, double power) {
        double bulletVelocity = bulletVelocity(power);
        double firingAngle = target.absoluteBearing + 180 - target.heading;
        double offsetToHit = Angle.asin(target.velocity / bulletVelocity * Angle.sin(firingAngle));

        waves.add(new Wave(robot, target, offsetToHit, bulletVelocity));

        return offsetToHit * target.aimingFudge() + target.absoluteBearing;
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
