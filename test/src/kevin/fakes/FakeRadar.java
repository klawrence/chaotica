package kevin.fakes;

import kevin.adapters.Radar;

public class FakeRadar implements Radar {
    public int angle;

    @Override
    public double getRadarHeading() {
        return 0;
    }

    @Override
    public void setTurnRadarRight(int angle) {
        this.angle = angle;
    }

    @Override
    public int getOthers() {
        return 0;
    }
}
