package kevin.adapters;

import robocode.AdvancedRobot;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RobotAdapter implements Radar, Gun, Steering, RobotControl {
    private final AdvancedRobot robot;
    private Rectangle2D.Double battlefield;

    public RobotAdapter(AdvancedRobot robot) {
        this.robot = robot;
    }

    @Override
    public double getWidth() {
        return robot.getWidth();
    }

    @Override
    public double getEnergy() {
        return robot.getEnergy();
    }

    @Override
    public void setBodyColor(Color color) {
        robot.setBodyColor(color);
    }

    @Override
    public void setGunColor(Color color) {
        robot.setGunColor(color);
    }

    @Override
    public void setScanColor(Color color) {
        robot.setScanColor(color);
    }

    @Override
    public long getTime() {
        return robot.getTime();
    }

    @Override
    public Point2D.Double getLocation() {
        return new Point2D.Double(getX(), getY());
    }

    @Override
    public int getRound() {
        return robot.getRoundNum();
    }

    @Override
    public Rectangle2D.Double getBattleField() {
        if(battlefield == null){
            battlefield = new Rectangle2D.Double(0, 0, robot.getBattleFieldWidth(), robot.getBattleFieldHeight());
        }
        return battlefield;
    }

    @Override
    public double getX() {
        return robot.getX();
    }

    @Override
    public double getY() {
        return robot.getY();
    }

    @Override
    public double getHeading() {
        return robot.getHeading();
    }

    @Override
    public double getVelocity() {
        return robot.getVelocity();
    }

    @Override
    public void setTurnRight(double bearing) {
        robot.setTurnRight(bearing);
    }

    @Override
    public void setAhead(double distance) {
        robot.setAhead(distance);
    }

    @Override
    public double getGunHeading() {
        return robot.getGunHeading();
    }

    @Override
    public double getGunHeat() {
        return robot.getGunHeat();
    }

    @Override
    public double getCoolingRate() {
        return robot.getGunCoolingRate();
    }

    @Override
    public void setTurnGunRight(double bearing) {
        robot.setTurnGunRight(bearing);
    }

    @Override
    public void setFire(double power) {
        robot.setFire(power);
    }

    @Override
    public double getRadarHeading() {
        return robot.getRadarHeading();
    }

    @Override
    public void setTurnRadarRight(int angle) {
        robot.setTurnRadarRight(angle);
    }

    @Override
    public int getOthers() {
        return robot.getOthers();
    }

    @Override
    public Point2D.Double centre() {
        return new Point2D.Double(getBattleField().width/2, getBattleField().height/2);
    }
}
