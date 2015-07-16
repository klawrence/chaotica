package kevin.geometry;

public class Angle {
    public static double inRadians(double angle) {
        return angle * Math.PI / 180;
    }

    public static double normalize(double angle) {
        angle = angle % 360;
        if(angle < 180) return angle;
        return angle - 360;
    }

    public static double sin(double angleInDegrees) {
        return Math.sin(inRadians(angleInDegrees));
    }

    public static double cos(double angleInDegrees) {
        return Math.cos(inRadians(angleInDegrees));
    }
}
