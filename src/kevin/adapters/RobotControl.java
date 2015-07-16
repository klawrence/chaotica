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
    void setScanColor(Color color);

    long getTime();

    Point2D.Double getLocation();
}
