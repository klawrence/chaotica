package kevin.strategy;

import kevin.adapters.RobotControl;
import kevin.control.Enemy;
import kevin.control.Scanner;
import kevin.geometry.Angle;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GoToCorner {
    public static final int SectorAngle = 30;
    public static final int Inset = 100;
    private Point2D.Double[] corners;
    private RobotControl robot;
    private Scanner scanner;

    public GoToCorner(RobotControl robot, Scanner scanner) {
        this.robot = robot;
        this.scanner = scanner;
    }

    protected Point2D.Double[] fourCorners() {
        if (corners == null) {
            int x = Inset;
            int y = Inset;
            double h = robot.getBattleField().height - Inset;
            double w = robot.getBattleField().width - Inset;
            corners = new Point2D.Double[]{
                    new Point2D.Double(x, y),
                    new Point2D.Double(x, h),
                    new Point2D.Double(w, y),
                    new Point2D.Double(w, h),
            };
        }

        return corners;
    }

    public Point2D.Double safestCorner() {
        return cornerFurthestFromCentreOfGravity();
    }

    protected Point2D.Double cornerFurthestFromCentreOfGravity() {
        Point2D.Double furthest = null;
        double distance = 0;
        Point2D.Double cog = centreOfGravity();

        // The corner furthest from the centre of gravity
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

        Collection<Enemy> enemies = scanner.enemies.values();
        for(Enemy enemy : enemies) {
            x += enemy.x;
            y += enemy.y;
        }

        return new Point2D.Double(x / enemies.size(), y / enemies.size());
    }
}
