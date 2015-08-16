package kevin.control;

import kevin.adapters.RobotControl;
import kevin.geometry.Angle;

import java.awt.geom.Point2D;

public class Enemy {
    public static final double InitialPowerToHit = 1;

    private final String name;
    private final RobotControl me;
    public final EnemyStats stats;

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
    public Point2D.Double location;

    private double powerToHit;
    private double headingChange;
    private long interval;

    public Enemy(String name, RobotControl me, EnemyStats stats) {
        this.name = name;
        this.me = me;
        this.stats = stats;
        this.dead = false;

//        TODO Compute the power to hit from stats
        powerToHit = InitialPowerToHit;
    }

    public void update(double distance, double bearing, double velocity, double heading, double energy, long time) {
        this.headingChange = Angle.normalizeAngle(heading - this.heading);
        this.interval = time - this.time;

        this.distance = distance;
        this.bearing = bearing;
        this.velocity = velocity;
        this.heading = heading;
        this.energy = energy;
        this.time = time;


        this.absoluteBearing = Angle.normalizeBearing(me.getHeading() + bearing);

        this.x = me.getX() + distance * Angle.sin(absoluteBearing);
        this.y = me.getY() + distance * Angle.cos(absoluteBearing);
        this.location = new Point2D.Double(x, y);
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

    public boolean isAlmostTouching() {
        return distance < me.getWidth() * 2;
    }

    public double distanceTo(Point2D.Double point) {
        return point.distance(location);
    }

    public void increasePowerToHitBy(double delta) {
        powerToHit = Math.max(Math.min(powerToHit + delta, 4), 0.1);
    }

    public void reducePowerToHitBy(double delta) {
        increasePowerToHitBy(-delta);
    }

    public double powerToHit() {
        return Math.min(powerToHit, energy / 4);
    }

    public void firedAt(double power) {
        reducePowerToHitBy(0.2);
        stats.shots++;
    }

    public void hit() {
        increasePowerToHitBy(1.0);
        stats.hits++;
    }

    public void killed() {
        stats.kills++;
    }

    public void shotMe() {
        stats.hitMe++;
    }

    public void killedMe() {
        stats.killedMe++;
    }

    public boolean isBetterTargetThan(Enemy other) {
        return this.stats.shots < 10 || this.stats.targetValue() > other.stats.targetValue();
    }

    protected long hitRate() {
        return stats.hitRate();
    }

    public boolean closerThan(Enemy other) {
        return this.distance < other.distance;
    }

    public boolean isGoodTarget() {
        return stats.targetValue() > 5;
    }

    public String toString() {
        return String.format("%-40s:  \t%d", name, stats.targetValue());
    }

    public double aimingFudge() {
        return 1.0;
//        return stats.aimingFudge;
    }

    public String pattern() {
        return String.format("%-30s: \t%.1f \t%.1f \t%d", name, headingChange, velocity, interval);
    }

    public double averageHeadingChange() {
        return headingChange / interval;
    }
}
