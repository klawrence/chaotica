package kevin;

import kevin.adapters.RobotAdapter;
import kevin.control.*;

import java.awt.*;

public class Timidity extends AbstractRobot {

    @Override
    protected void createCommander(RobotAdapter adapter, Logger logger, Scanner scanner, Gunner gunner, Driver driver) {
        controller = new TimidCommander(adapter, scanner, gunner, driver, logger);
    }



}
