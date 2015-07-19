package kevin.geometry;

public class Angle {
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

    public static double asin(double x) {
        return inDegrees(Math.asin(x));
    }

    public static double acos(double x) {
        return inDegrees(Math.acos(x));
    }

    public static double inRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    private static double inDegrees(double radians) {
        return radians / Math.PI * 180;
    }

}
