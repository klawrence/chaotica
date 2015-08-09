package kevin.control;

import robocode.RobocodeFileWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Stats {
    Map<String, EnemyStats> stats;

    public Stats() {
        stats = new HashMap<String, EnemyStats>();
    }

    public void save(Writer out) throws IOException {
        out.write(EnemyStats.headers());
        out.write("\n");

        ArrayList<EnemyStats> list = new ArrayList<EnemyStats>((stats.values()));
        Collections.sort(list);
        for(EnemyStats stat : list) {
            out.write(stat.toString());
            out.write("\n");
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
