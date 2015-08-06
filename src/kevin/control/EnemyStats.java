package kevin.control;

public class EnemyStats {
    String enemy;

    int shots = 0;
    int hits = 0;
    int kills = 0;
    int rams = 0;
    int shotMe = 0;
    int rammedMe = 0;
    int killedMe = 0;

    public EnemyStats(String enemy) {
        this.enemy = enemy;
    }

    public static String headers() {
        return String.format("%-30s\t%s\t%s\t%s\t%s\t%s\t%s", "robot", "shots", "hits", "hit rate", "kills", "shot me", "killed me");
    }

    public String toString() {
        return String.format("%s\t%d\t%d\t%.1f\t%d\t%d\t%d", enemy, shots, hits, hitRate(), kills, shotMe, killedMe);
    }

    public double hitRate() {
        if (shots == 0) return 0;
        return hits * 1.0 / shots;
    }
}
