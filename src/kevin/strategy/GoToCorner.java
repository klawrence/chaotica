package kevin.strategy;

import kevin.adapters.RobotControl;
import kevin.control.Enemy;
import kevin.geometry.Angle;

import java.awt.geom.Point2D;
import java.util.Map;

public class GoToCorner {
    public static final int SectorAngle = 30;
    public static final int Inset = 100;
    private Point2D.Double[] corners;
    private RobotControl robot;
    private Map<String, Enemy> enemies;

    public GoToCorner(RobotControl robot, Map<String, Enemy> enemies) {
        this.robot = robot;
        this.enemies = enemies;
    }

    protected Point2D.Double[] fourCorners() {
        if (corners == null) {
            int x = Inset;
            int y = Inset;
            double h = robot.getBattleFieldHeight() - Inset;
            double w = robot.getBattleFieldWidth() - Inset;
            corners = new Point2D.Double[]{
                    new Point2D.Double(x, y),
                    new Point2D.Double(x, h),
                    new Point2D.Double(w, y),
                    new Point2D.Double(w, h),
            };
        }

        return corners;
    }

    public Point2D.Double safestPoint() {
        // The corner furthest from the centre of gravity
        Point2D.Double furthest = null;
        double distance = 0;
        Point2D.Double cog = centreOfGravity();

        for(Point2D.Double corner : fourCorners()) {
            double d = corner.distanceSq(cog);
            if(d > distance) {
                distance = d;
                furthest = corner;
            }
        }

        return furthest;
    }

    private Point2D.Double centreOfGravity() {
        double x = 0;
        double y = 0;

        for(Enemy enemy : enemies.values()) {
            x += enemy.x;
            y += enemy.y;
        }

        return new Point2D.Double(x / enemies.size(), y / enemies.size());
    }
}
