package ru.job4j.interview;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static ru.job4j.interview.Dates.DATE_TIME_FORMATTER;
import static ru.job4j.interview.Dates.TIME_FORMATTER;
import static ru.job4j.interview.TimeAdverb.TODAY;
import static ru.job4j.interview.TimeAdverb.YESTERDAY;

/**
 * Class VacancyParser
 */
public class VacancyParser {

    private static final Logger LOG = Logger.getLogger(VacancyParser.class);

    private static final String SCRIPT_MATCHER = "script";
    private static final String JAVA_MATCHER = "java";

    private PageGrabber pageGrabber = new PageGrabber();
    private Config config = new Config();
    private StringParser parser = new StringParser();
    private Dates dates = new Dates();

    /**
     * Grab vacancy from HTML row
     *
     * @param els              list of elements
     * @param recentUpdateDate date in millis
     * @return vacancies
     */
    public Set<Vacancy> fetchVacancies(final Elements els, final long recentUpdateDate) {
        Set<Vacancy> vacancies = new HashSet<>();
        for (Element row : els) {
            Vacancy vacancy = createVacancy(row);
            Timestamp createDate = vacancy.getLastUpdate();
            boolean validVacancy = isVacancyValid(recentUpdateDate, vacancy, createDate);
            if (validVacancy) {
                vacancies.add(vacancy);
            }
        }
        LOG.info(String.format("Create %d vacancies", vacancies.size()));
        return vacancies;
    }

    private boolean isVacancyValid(final long recentUpdateDate,
                                   final Vacancy vacancy,
                                   final Timestamp createDate) {
        return isJavaVacancy(vacancy.getTitle())
            && createDate.getTime() >= recentUpdateDate;
    }

    private boolean isJavaVacancy(final String title) {
        return !title.toLowerCase().contains(SCRIPT_MATCHER)
            && title.toLowerCase().contains(JAVA_MATCHER);
    }

    /**
     * Go through found pages
     *
     * @param url              url
     * @param recentCreateDate date
     * @return elements
     */
    public Elements scrollPages(final String url, final long recentCreateDate) {
        Document doc = pageGrabber.capturePage(url);
        Elements linkEls =
            doc.select("table.forumtable_results a[href^='actual']");
        int amount = Integer.parseInt(linkEls.last().text());
        Elements rowsLinks = new Elements();
        int counter = 1;
        if (counter <= amount) {
            do {
                doc = pageGrabber.capturePage(config.getPageURLTemplate() + counter);
                Elements els = doc.select("tr:has(.postslisttopic)");
                Timestamp t =
                    convertToDate(els.first().select("td:last-of-type").text());
                if (t.getTime() < recentCreateDate) {
                    break;
                }
                rowsLinks.addAll(els);
                LOG.info(String.format("Parse page #%d", counter));
                counter++;
            } while (counter <= amount);
        }
        return rowsLinks;
    }

    private Vacancy createVacancy(final Element row) {
        Vacancy vacancy = new Vacancy();
        String href = row.select("td.postslisttopic a").attr("href");
        vacancy.setId(Integer.valueOf(parser.getVacancyID(href)));
        vacancy.setLink(href);
        vacancy.setTitle(row.select("td.postslisttopic").text());
        String dateText = row.select("td:last-of-type").text();
        vacancy.setLastUpdate(convertToDate(dateText));
        vacancy.setDescription(fetchVacancyDescription(href));
        LOG.info(String.format("Create %s", vacancy.toString()));
        return vacancy;
    }

    private String fetchVacancyDescription(final String href) {
        Document doc = pageGrabber.capturePage(href);
        Element el = doc.select("table.msgTable").get(0);
        return el.select("td.msgBody").get(1).text();
    }

    private Timestamp convertToDate(final String dateText) {
        LocalDateTime localDateTime = null;
        if (isContainsAdverb(dateText)) {
            localDateTime = adjustLocalDateTime(dateText);
        } else {
            // looks like a bug in DateTimeFormatter,
            localDateTime = LocalDateTime.parse(dateText.replace("май", "мая"),
                DATE_TIME_FORMATTER);
        }
        return Timestamp.valueOf(localDateTime);
    }

    private boolean isContainsAdverb(final String dateText) {
        return dateText.toLowerCase().contains(YESTERDAY.getValue())
            || dateText.toLowerCase().contains(TODAY.getValue());
    }

    private LocalDateTime adjustLocalDateTime(final String dateText) {
        LocalDate targetDate = LocalDate.now();
        LocalTime localTime =
            dates.parseTime(parser.getTimeText(dateText), TIME_FORMATTER);
        LocalDateTime localDateTime = LocalDateTime.of(targetDate, localTime);
        if (dateText.contains(YESTERDAY.getValue())) {
            localDateTime = localDateTime.minus(1, ChronoUnit.DAYS);
        }
        return localDateTime;
    }
}
