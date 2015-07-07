package kevin.control;

import kevin.adapters.RobotControl;

import java.awt.geom.Point2D;

public class Enemy {
    private final String name;
    private final RobotControl me;

    double distance;
    double bearing;
    double absoluteBearing;
    double velocity;
    double heading;
    double energy;
    long time;

    public Enemy(String name, RobotControl me) {
        this.name = name;
        this.me = me;
    }

    public void update(double distance, double bearing, double velocity, double heading, double energy, long time) {
        this.distance = distance;
        this.bearing = bearing;
        this.velocity = velocity;
        this.heading = heading;
        this.energy = energy;
        this.time = time;

        this.absoluteBearing = me.getHeading() + bearing;
    }

    public double getAbsoluteBearing() {
        return absoluteBearing;
    }

    public String getName() {
        return name;
    }

    public Position getPositionAtTime(long atTime) {
        return new Position(myLocation(), me.getHeading(), distance, bearing);
    }

    private Point2D.Double myLocation() {
        return new Point2D.Double(me.getX(), me.getY());
    }

    public String toString() {
        return String.format("%s: %d,%d", name, (int) bearing, (int) distance);
    }

    public boolean nearlyDead() {
        return energy < 1;
    }

    public double offsetToBearing(double bearing) {
        double offset = getAbsoluteBearing() - bearing;
        while(offset < -180) offset += 360;
        while(offset > 180) offset -= 360;
        return offset;
    }
}
