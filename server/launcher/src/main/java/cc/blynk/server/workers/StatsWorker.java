package cc.blynk.server.workers;

import cc.blynk.server.Holder;
import cc.blynk.server.core.BlockingIOProcessor;
import cc.blynk.server.core.dao.SessionDao;
import cc.blynk.server.core.dao.UserDao;
import cc.blynk.server.core.stats.GlobalStats;
import cc.blynk.server.core.stats.model.Stat;
import cc.blynk.server.db.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Worker responsible for logging current request rate,
 * methods invocation statistics, active channels count and
 * currently pending blocking tasks.
 *
 * The Blynk Project.
 * Created by Dmitriy Dumanskiy.
 * Created on 18.04.15.
 */
public class StatsWorker implements Runnable {

    private static final Logger log = LogManager.getLogger(StatsWorker.class);

    private final GlobalStats stats;
    private final SessionDao sessionDao;
    private final UserDao userDao;
    private final DBManager dbManager;
    private final String region;
    private final BlockingIOProcessor blockingIOProcessor;

    public StatsWorker(Holder holder) {
        this.stats = holder.stats;
        this.sessionDao = holder.sessionDao;
        this.userDao = holder.userDao;
        this.dbManager = holder.dbManager;
        this.region = holder.region;
        this.blockingIOProcessor = holder.blockingIOProcessor;
    }

    @Override
    public void run() {
        try {
            Stat stat = new Stat(sessionDao, userDao, blockingIOProcessor, stats, true);
            log.info(stat);
            dbManager.insertStat(this.region, stat);
        } catch (Exception e) {
            log.error("Error making stats.", e);
        }
    }

}

