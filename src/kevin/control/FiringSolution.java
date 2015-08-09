package kevin.control;

import kevin.geometry.Angle;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class FiringSolution {
    public FiringSolution() {

    }

    // sin(R) = Vt.t / Vb.t * sin(T)
    // sin(Hg - Bt) = Vt / Vb * sin(Bt + 180 - Ht)

    public double gunHeadingToHit(Enemy target, double power) {
        return Angle.asin(target.velocity / bulletVelocity(power) * Angle.sin(firingAngle(target))) + target.absoluteBearing;
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

    protected double firingAngle(Enemy target) {
        return target.absoluteBearing + 180 - target.heading;
    }

    protected double bulletVelocity(double power) {
        return 20 - 3 * power;
    }

    protected double bulletDistance(double power) {
        return 20 - 3 * power;
    }

}
