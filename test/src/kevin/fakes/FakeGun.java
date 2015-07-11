package kevin.fakes;

import kevin.adapters.Gun;

public class FakeGun implements Gun {

    public int heading;
    public int heat;
    public double turn;
    public double power;
    public boolean fired;

    @Override
    public double getGunHeading() {
        return heading;
    }

    @Override
    public double getGunHeat() {
        return heat;
    }

    @Override
    public double getCoolingRate() {
        return 0.1;
    }

    @Override
    public void setTurnGunRight(double bearing) {
        turn = bearing;
    }

    @Override
    public void setFire(double power) {
        this.power = power;
        this.fired = true;
    }
}
