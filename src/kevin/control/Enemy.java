package kevin.control;

import kevin.adapters.RobotControl;
import kevin.geometry.Angle;

import java.awt.geom.Point2D;

public class Enemy {
    private final String name;
    private final RobotControl me;

    public double distance;
    public double bearing;
    public double absoluteBearing;
    public double velocity;
    public double heading;
    public double energy;
    public long time;
    public boolean dead;
    public double x;
    public double y;

    public Enemy(String name, RobotControl me) {
        this.name = name;
        this.me = me;
        this.dead = false;
    }

    public void update(double distance, double bearing, double velocity, double heading, double energy, long time) {
        this.distance = distance;
        this.bearing = bearing;
        this.velocity = velocity;
        this.heading = heading;
        this.energy = energy;
        this.time = time;

        this.absoluteBearing = me.getHeading() + bearing;

        this.x = me.getX() + distance * Angle.sin(absoluteBearing);
        this.y = me.getY() - distance * Angle.cos(absoluteBearing);
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

    public boolean nearlyDead() {
        return energy < 5;
    }

    public double offsetToBearing(double bearing) {
        double offset = absoluteBearing - bearing;
        while(offset < -180) offset += 360;
        while(offset > 180) offset -= 360;
        return offset;
    }

    public boolean isClose() {
        return distance < 200;
    }

    public boolean isVeryClose() {
        return distance < 100;
    }

    public double distanceTo(Point2D.Double point) {
        return point.distance(new Point2D.Double(x, y));
    }

    public String toString() {
        return String.format("%s: %d,%d   [%d,%d]", name, (int) bearing, (int) distance, (int) x, (int) y);
    }

}
