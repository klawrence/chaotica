package kevin.fakes;

import kevin.adapters.Status;

public class FakeStatus implements Status {
    public final int width = 16;
    public int energy = 100;
    public int x = 500;
    public int y = 400;
    public int heading = 0;

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getHeading() {
        return heading;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getEnergy() {
        return energy;
    }
}
