package kevin.adapters;

import robocode.AdvancedRobot;

import java.awt.*;

public class RobotAdapter implements Radar, Gun, Steering, RobotControl {
    private final AdvancedRobot robot;

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
    public void setScanColor(Color color) {
        robot.setScanColor(color);
    }

    @Override
    public long getTime() {
        return robot.getTime();
    }

    @Override
    public double getBattleFieldWidth() {
        return robot.getBattleFieldWidth();
    }

    @Override
    public double getBattleFieldHeight() {
        return robot.getBattleFieldHeight();
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

}
