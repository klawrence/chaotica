package kevin.adapters;

import java.awt.*;
import java.awt.geom.Point2D;

public interface RobotControl {
    double getBattleFieldWidth();
    double getBattleFieldHeight();
    double getX();
    double getY();
    double getHeading();
    double getVelocity();
    double getWidth();
    double getEnergy();

    void setBodyColor(Color color);
    void setGunColor(Color color);
    void setScanColor(Color color);

    long getTime();
    int getOthers();

    Point2D.Double getLocation();
}
