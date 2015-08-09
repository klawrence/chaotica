package kevin.adapters;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface RobotControl {
    Rectangle2D.Double getBattleField();
    Point2D.Double centre();

    Point2D.Double getLocation();
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


    int getRound();

}
