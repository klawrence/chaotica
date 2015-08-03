package kevin.fakes;

import kevin.adapters.RobotControl;

import java.awt.*;
import java.awt.geom.Point2D;

public class FakeRobot implements RobotControl {
    public final int width = 16;
    public int energy = 100;
    public int x = 500;
    public int y = 400;
    public int heading = 0;
    public int velocity = 0;
    public int battleFieldWidth = 1000;
    public int battleFieldHeight = 800;
    public int robotCount = 5;

    @Override
    public double getBattleFieldWidth() {
        return battleFieldWidth;
    }

    @Override
    public double getBattleFieldHeight() {
        return battleFieldHeight;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getHeading() {
        return heading;
    }

    @Override
    public double getVelocity() {
        return velocity;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public void setBodyColor(Color color) {

    }

    @Override
    public void setScanColor(Color color) {

    }

    @Override
    public void setGunColor(Color color) {

    }

    @Override
    public long getTime() {
        return 0;
    }

    @Override
    public int getOthers() {
        return robotCount;
    }

    @Override
    public Point2D.Double getLocation() {
        return new Point2D.Double(x, y);
    }
}
