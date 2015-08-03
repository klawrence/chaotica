package kevin.control;

public class EnemyStats {
    String enemy;

    int shots = 0;
    int hits = 0;
    int kills = 0;
    int shotMe = 0;
    int killedMe = 0;

    public EnemyStats(String enemy) {
        this.enemy = enemy;
    }

    public String toString() {
        return String.format("%d\t%d\t%1f\t%d\t%d\t%d", shots, hits, hitRate(), kills, shotMe, killedMe);
    }

    public double hitRate() {
        if (shots == 0) return 0;
        return hits * 1.0 / shots;
    }
}
