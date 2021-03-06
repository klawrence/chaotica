package kevin;

import kevin.adapters.RobotAdapter;
import kevin.control.*;
import robocode.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractRobot extends AdvancedRobot {
    private final Logger logger;
    private final Scanner scanner;
    private final Gunner gunner;
    private final Driver driver;

    protected Commander controller;

    public AbstractRobot() {
        super();

        RobotAdapter adapter = new RobotAdapter(this);
        logger = new Logger(this);
        logger.enabled = false;

        scanner = new Scanner(adapter, adapter, logger);
        gunner = new Gunner(adapter, adapter, logger);
        driver = new Driver(adapter, adapter, logger);

        createCommander(adapter, logger, scanner, gunner, driver);

    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        setBodyColor(controller.bodyColor);
        setGunColor(controller.gunColor);
        setRadarColor(controller.radarColor);
        setBulletColor(controller.bulletColor);
        setScanColor(controller.scanColor);

        setAdjustGunForRobotTurn(true);

        while (true) {
            controller.fight();
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        controller.onScannedRobot(e);
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        controller.onHitRobot(e);
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        controller.onHitWall(e);
    }

    @Override
    public void onBulletHit(BulletHitEvent e) {
        controller.onBulletHit(e);
    }

    @Override
    public void onBulletMissed(BulletMissedEvent e) {
        controller.onBulletMissed(e);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        controller.onRobotDeath(event);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        controller.onHitByBullet(event);
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        controller.onRoundEnded(event);
    }

    @Override
    public void onDeath(DeathEvent event) {
        controller.onDeath(event);
    }

    @Override
    public void onSkippedTurn(SkippedTurnEvent event) {
        logger.log("skipped a turn");
    }

    public void onBattleEnded(BattleEndedEvent event) {
        saveStats();
    }

    protected void saveStats() {
        if(logger.enabled) {
            try {
                File file = getDataFile("stats.txt");
                RobocodeFileWriter writer = new RobocodeFileWriter(file);
                out.print("Writing to file at " + file);
                Scanner.stats.save(writer);
                writer.close();
            } catch (IOException e) {
                out.print("Saving stats failed");
            }
        }
    }


    protected abstract void createCommander(RobotAdapter adapter, Logger logger, Scanner scanner, Gunner gunner, Driver driver);
}
