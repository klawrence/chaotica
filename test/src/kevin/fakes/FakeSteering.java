package kevin.fakes;

import kevin.adapters.Steering;

public class FakeSteering implements Steering {
    public double distance;
    public double bearing;

    @Override
    public void setTurnRight(double bearing) {
        this.bearing = bearing;
    }

    @Override
    public void setAhead(double distance) {
        this.distance = distance;
    }
}
