package ru.job4j.interview;

import org.apache.log4j.Logger;
import org.jsoup.select.Elements;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

/**
 * App class is initial point of application.
 */
public class App {

    private static final Logger LOG = Logger.getLogger(App.class);

    private SQLStorage storage = new SQLStorage();
    private VacancySaver saver = new VacancySaver();
    private Config config = new Config();

    private boolean isFirstRun = true;

    /**
     * Start the whole process of downloading and parsing vacancies
     */
    public void start() {
        Runnable task = this::parse;
        ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.MINUTES);
    }

    /**
     * Init table in database. Should be invoke before all other methods.
     */
    public void init() {
        storage.initDB();
    }

    /**
     * Parse vacancies from site. Should be invoke after init.
     * For the first time it starts parsing from the beginning of the year.
     * During the next runs it checks only last updates
     */
    private void parse() {
        Timestamp recentCreateDate = saver.lastUpdateDate();
        if (isFirstRun && recentCreateDate == null) {
            isFirstRun = false;
            LocalDateTime date = LocalDateTime.of(LocalDate.now().getYear(), 1, 1, 0, 0);
            recentCreateDate = Timestamp.valueOf(date);
        }
        Set<Vacancy> vacancies = grabVacancies(recentCreateDate.getTime());
        addVacanciesToDB(vacancies);
        LOG.info(String.format("Parse %d vacancies", vacancies.size()));
    }

    private Set<Vacancy> grabVacancies(final long recentCreateDate) {
        VacancyParser parser = new VacancyParser();
        Elements linkEls =
            parser.scrollPages(config.getStartURL(), recentCreateDate);
        return parser.fetchVacancies(linkEls, recentCreateDate);
    }

    private void addVacanciesToDB(final Set<Vacancy> vacancies) {
        for (Vacancy vacancy : vacancies) {
            boolean isDuplicate = saver.isVacancyPresent(vacancy.getId());
            if (!isDuplicate) {
                saver.add(vacancy);
            }
        }
    }

//    public static void main(String[] args) {
//        App app = new App();
//        app.init();
//        app.start();
//    }
}
