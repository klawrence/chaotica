package kevin.adapters;

import java.awt.*;

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
}
