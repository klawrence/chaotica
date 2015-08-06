package kevin.control;

import robocode.RobocodeFileWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Stats {
    Map<String, EnemyStats> stats = new HashMap<String, EnemyStats>();

    public Stats() {
    }

    public void save(RobocodeFileWriter file) throws IOException {
        file.write(EnemyStats.headers());

        for(EnemyStats stat : stats.values()){
            file.write(stat.toString());
        }
    }

    public EnemyStats stats_for(String name) {
        EnemyStats stat = stats.get(name);
        if(stat == null) {
            stat = new EnemyStats(name);
            stats.put(name, stat);
        }
        return stat;
    }
}
