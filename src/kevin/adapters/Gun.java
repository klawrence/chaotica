package kevin.adapters;

public interface Gun {
    double getGunHeading();
    double getGunHeat();
    double getCoolingRate();
    void setTurnGunRight(double bearing);
    void setFire(int power);
}
