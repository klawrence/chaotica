package kevin.control;

import robocode.RobocodeFileWriter;

import java.io.IOException;
import java.util.*;

public class Stats {
    Map<String, EnemyStats> stats;

    public Stats() {
        stats = new HashMap<String, EnemyStats>();
    }

    public void save(RobocodeFileWriter file) throws IOException {
        file.write(EnemyStats.headers());
        file.write("\n");

        ArrayList<EnemyStats> list = new ArrayList<EnemyStats>((stats.values()));
        Collections.sort(list);
        for(EnemyStats stat : list) {
            file.write(stat.toString());
            file.write("\n");
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
