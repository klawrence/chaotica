package kevin.control;

public class EnemyStats implements Comparable<EnemyStats> {
    String enemy;

    int shots = 0;
    int hits = 0;
    int kills = 0;
    int rams = 0;
    int hitMe = 0;
    int rammedMe = 0;
    int killedMe = 0;

    public EnemyStats(String enemy) {
        this.enemy = enemy;
    }

    public static String headers() {
        return String.format("%30s\t%s\t%s\t%s\t%s\t%s\t%s", "robot", "shots", "hits", "rate", "kills", "hit me", "killed me");
    }

    public String toString() {
        return String.format("%30s\t%d\t%d\t%d\t%d\t%d\t%d", enemy, shots, hits, hitRate(), kills, hitMe, killedMe);
    }

    public int hitRate() {
        if (shots == 0) return 0;
        return (int) (hits * 100.0 / shots);
    }

    @Override
    public int compareTo(EnemyStats other) {
        return this.hitRate() - other.hitRate();
    }
}
