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

    int virtualShotsFired = 0;
    double aimingFudge = 1;

    public EnemyStats(String enemy) {
        this.enemy = enemy;
    }

    public static String headers() {
        return String.format("%30s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", "robot", "shots", "hits", "rate", "kills", "hit me", "fudge", "value");
    }

    public String toString() {
        return String.format("%30s\t%d\t%d\t%d\t%d\t%d\t%.1f\t%d", enemy, shots, hits, hitRate(), kills, hitMe, aimingFudge, targetValue());
    }

    public int hitRate() {
        if (shots == 0) return 0;
        return (int) (hits * 100.0 / shots);
    }

    @Override
    public int compareTo(EnemyStats other) {
        return this.targetValue() - other.targetValue();
    }

    public int targetValue() {
        if(shots == 0) return 0;
        return (int) (100.0 * (hits - hitMe) / shots);
    }

    public void updateFudgeFactor(double fudgeFactor) {
        aimingFudge = (aimingFudge * virtualShotsFired + fudgeFactor) / (virtualShotsFired+1);
        virtualShotsFired++;
    }
}
