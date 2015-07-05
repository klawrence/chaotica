package kevin;

import kevin.adapters.RobotAdapter;
import kevin.control.Logger;
import kevin.control.*;
import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;

public class Chaotica extends AdvancedRobot {
    private Controller controller;

    public Chaotica() {
        super();

        RobotAdapter adapter = new RobotAdapter(this);
        Logger logger = new Logger(this);

        Scanner scanner = new Scanner(adapter, adapter);
        Gunner gunner = new Gunner(adapter, adapter, logger);
        Driver driver = new Driver(adapter, adapter);

        controller = new Controller(adapter, scanner, gunner, driver, logger);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        setBodyColor(Color.orange);
        setGunColor(Color.red);
        setRadarColor(Color.black);
        setBulletColor(Color.orange);
        setScanColor(Color.orange);

        while (true) {
            controller.fight();
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        controller.onScannedRobot(e);
    }

    public void onHitRobot(HitRobotEvent e) {
        controller.onHitRobot(e);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        controller.onRobotDeath(event);
    }
}
