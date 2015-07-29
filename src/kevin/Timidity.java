package kevin;

import kevin.adapters.RobotAdapter;
import kevin.control.*;

import java.awt.*;

public class Timidity extends AbstractRobot {

    public Timidity() {
        super();

        RobotAdapter adapter = new RobotAdapter(this);
        Logger logger = new Logger(this);
        logger.enabled = true;

        Scanner scanner = new Scanner(adapter, adapter);
        Gunner gunner = new Gunner(adapter, adapter, logger);
        Driver driver = new Driver(adapter, adapter, logger);

        controller = new ChaoticCommander(adapter, scanner, gunner, driver, logger);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        setBodyColor(ChaoticCommander.BodyColor);
        setGunColor(ChaoticCommander.GunColor);
        setRadarColor(Color.black);
        setBulletColor(Color.orange);
        setScanColor(Color.orange);

        setAdjustGunForRobotTurn(true);

        while (true) {
            controller.fight();
            execute();
        }
    }

}
