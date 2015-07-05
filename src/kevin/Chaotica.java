package kevin;

import kevin.adapters.RobotAdapter;
import kevin.control.Logger;
import kevin.control.*;
import robocode.*;

import java.awt.*;

public class Chaotica extends AdvancedRobot {
    private Controller controller;

    public Chaotica() {
        super();

        RobotAdapter adapter = new RobotAdapter(this);
        Logger logger = new Logger(this);
        logger.enabled = false;

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

        setAdjustGunForRobotTurn(true);

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

    public void onHitWall(HitWallEvent e) {
        controller.onHitWall(e);
    }

    public void onBulletHit(BulletHitEvent e) {
        controller.onBulletHit(e);
    }

    public void onBulletMissed(BulletMissedEvent e) {
        controller.onBulletMissed(e);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        controller.onRobotDeath(event);
    }
}
