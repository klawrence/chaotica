package kevin.control;

import kevin.geometry.Angle;

public class FiringSolution {
    public FiringSolution() {

    }

    // sin(R) = Vt.t / Vb.t * sin(T)
    // sin(Hg - Bt) = Vt / Vb * sin(Bt + 180 - Ht)

    public double gunHeadingToHit(Enemy enemy, double power) {
        double bulletVelocity = 20 - 3 * power;
        return Angle.asin(enemy.velocity / bulletVelocity * Angle.sin(enemy.absoluteBearing + 180 - enemy.heading)) + enemy.absoluteBearing;
    }

}
