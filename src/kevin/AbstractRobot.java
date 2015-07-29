package kevin;

import kevin.control.ChaoticCommander;
import kevin.control.Commander;
import robocode.*;

public abstract class AbstractRobot extends AdvancedRobot {
    protected Commander controller;

    public AbstractRobot() {
        super();
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
    public void onRoundEnded(RoundEndedEvent event) {
        controller.onRoundEnded(event);
    }
}
