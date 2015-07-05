package kevin.fakes;

import kevin.adapters.Radar;

public class FakeRadar implements Radar {
    public int angle;

    @Override
    public void setTurnRadarRight(int angle) {
        this.angle = angle;
    }
}
