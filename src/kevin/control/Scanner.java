package kevin.control;

import kevin.adapters.Radar;
import kevin.adapters.RobotControl;
import kevin.geometry.Angle;
import robocode.HitRobotEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scanner {
    public static final int SectorAngle = 30;
    public static final Stats stats = new Stats();

    public final HashMap<String, Enemy> enemies;
    private final HashMap<String, Enemy> dead;
    public final Radar radar;
    private RobotControl robot;
    private Logger logger;
    private int[] sectors;

    public Scanner(Radar radar, RobotControl robot, Logger logger) {
        this.radar = radar;
        this.robot = robot;
        this.logger = logger;

        this.enemies = new HashMap<String, Enemy>();
        this.dead = new HashMap<String, Enemy>();
    }

    public void fullSweep() {
        radar.setTurnRadarRight(360);
    }

    public Enemy getEnemy(String name) {
        Enemy enemy = enemies.get(name);
        if(enemy == null) {
            enemy = new Enemy(name, robot, stats.stats_for(name));
            if(! dead.containsKey(name)) {
                enemies.put(name, enemy);
                logger.log("spotted", enemy);
            }
        }
        return enemy;
    }

    public void scanFor(Enemy target) {
        double offset = target.offsetToBearing(radar.getRadarHeading()) * 5;
        radar.setTurnRadarRight((int) offset);
    }

    public Enemy onScannedRobot(ScannedRobotEvent event) {
        Enemy target = getEnemy(event.getName());
        target.update(event.getDistance(), event.getBearing(), event.getVelocity(), event.getHeading(), event.getEnergy(), event.getTime());
        return target;
    }

    public Enemy onHitRobot(HitRobotEvent event) {
        Enemy target = getEnemy(event.getName());
        target.update(robot.getWidth(), event.getBearing(), 0, 0, event.getEnergy(), event.getTime());
        return target;
    }

    public Enemy onRobotDeath(RobotDeathEvent event) {
        Enemy target = getEnemy(event.getName());
        enemies.remove(target.getName());
        dead.put(target.getName(), target);
        target.killed();
        return target;
    }

    public int getEnemyCount() {
        return radar.getOthers();
    }

    public double nearestEnemyOnBearing(int bearing) {
        double from = Angle.normalizeBearing(bearing - SectorAngle / 2);
        double to = Angle.normalizeBearing(bearing + SectorAngle / 2);
        double distanceToClosest = Double.MAX_VALUE;
        boolean adjust = false;
        if(from > 360 - SectorAngle) {
            adjust = true;
            from = from - 360;
        }

        for(Enemy enemy : enemies.values()) {
            double enemyBearing = enemy.absoluteBearing;
            if(enemy.distance < distanceToClosest) {
                if(adjust && enemyBearing > 360 - SectorAngle) {
                    enemyBearing = enemyBearing - 360;
                }
                if(enemyBearing > from && enemyBearing < to) {
                    distanceToClosest = enemy.distance;
                }
            }
        }

        return distanceToClosest;
    }

    public int[] sectors() {
        if (sectors == null) {
            sectors = new int[360 / SectorAngle];
            for(int i = 0; i < sectors.length; i++) {
                sectors[i] = i * SectorAngle;
            }
        }
        return sectors;
    }


//    public void tidy() {
//        // Because sometimes we miss a robot death
//        long now = robot.getTime();
//        if(radar.getOthers() != enemies.size()) {
//            List<String> dead =new ArrayList<String>();
//
//            for(Enemy enemy : enemies.values()){
//                if(now - enemy.time > 5){
//                    enemy.dead = true;
//                    dead.add(enemy.getName());
//                }
//            }
//
//            for(String name : dead){
//                enemies.remove(name);
//            }
//        }
//    }

}
