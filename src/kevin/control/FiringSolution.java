package kevin.control;

public class FiringSolution {
    public FiringSolution() {

    }

    // sin(R) = Vt.t / Vb.t * sin(T)
    // sin(Hg - Bt) = Vt / Vb * sin(Bt + 180 - Ht)

    public double gunHeadingToHit(Enemy enemy, double power) {
        double bulletVelocity = 20 - 3 * power;
        return asin(enemy.velocity / bulletVelocity * sin(enemy.bearing + 180 - enemy.heading)) + enemy.bearing;
    }

    private double sin(double degrees) {
        return Math.sin(inRadians(degrees));
    }

    private double asin(double x) {
        return inDegrees(Math.sin(x));
    }

    private double inRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    private double inDegrees(double radians) {
        return radians / Math.PI * 180;
    }
}
