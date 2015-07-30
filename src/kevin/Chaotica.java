package kevin;

import kevin.adapters.RobotAdapter;
import kevin.control.Logger;
import kevin.control.*;
import robocode.*;

import java.awt.*;

public class Chaotica extends AbstractRobot {

    @Override
    protected void createCommander(RobotAdapter adapter, Logger logger, Scanner scanner, Gunner gunner, Driver driver) {
        controller = new ChaoticCommander(adapter, scanner, gunner, driver, logger);
    }

}
