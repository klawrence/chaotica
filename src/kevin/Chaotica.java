package kevin;

import kevin.adapters.RobotAdapter;
import kevin.control.Logger;
import kevin.control.*;
import robocode.*;

import java.awt.*;

public class Chaotica extends AbstractRobot {

    @Override
    protected void createCommander(RobotAdapter robot, Logger logger, Scanner scanner, Gunner gunner, Driver driver) {
        logger.enabled = saveData = true;

        controller = new ChaoticCommander(robot, scanner, gunner, driver, logger);
    }

}
