package kevin.geometry;

import java.awt.geom.Point2D;

public class Angle {
    public static double normalizeAngle(double angle) {
        angle = angle % 360;
        if(angle <= 180) return angle;
        return angle - 360;
    }

    public static double normalizeBearing(double bearing) {
        while(bearing < 0) bearing += 360;
        return bearing % 360;
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

    public static double atan(double x, double y) {
        return inDegrees(Math.atan2(x, y));
    }

    public static double inRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    private static double inDegrees(double radians) {
        return radians / Math.PI * 180;
    }

    public static double bearingTo(Point2D from, Point2D to) {
        double angle = atan((to.getX() - from.getX()), (to.getY() - from.getY()));
        return normalizeBearing(angle);
    }

    public static double differenceBetweenBearings(double a, double b) {
        double difference = Math.abs(a - b);
        if (difference <= 180) {
            return difference;
        }
        return 360 - difference;
    }
}
