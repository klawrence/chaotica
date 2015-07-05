package kevin.control;

import java.awt.*;
import java.awt.geom.Point2D;

public class Position {
    private Point2D.Double myLocation;
    private Point enemyLocation;
    private double myHeading;
    private final double distance;
    private final double bearing;

    public Position(Point2D.Double myLocation, double myHeading, double distance, double bearing) {
        this.myLocation = myLocation;
        this.myHeading = myHeading;
        this.distance = distance;
        this.bearing = bearing;

        double absoluteBearing = myHeading + bearing;
        double x = myLocation.getX() + Math.cos(toRadians(90 - absoluteBearing)) * distance;
        double y = myLocation.getY() - Math.sin(toRadians(90 - absoluteBearing)) * distance;
        enemyLocation = new Point((int) x, (int) y);
    }

    private double toRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    public double getX() {
        return enemyLocation.getX();
    }

    public double getY() {
        return enemyLocation.getY();
    }
}
