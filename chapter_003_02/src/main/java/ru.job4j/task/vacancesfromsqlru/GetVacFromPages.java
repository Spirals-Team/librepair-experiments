package ru.job4j.task.vacancesfromsqlru;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

class GetVacFromPages {

    private static final Logger LOGGG = LoggerFactory.getLogger(GetVacFromPages.class);

    HashSet<String> getPagesUrls(WorkWithDataBase workWithDataBase) {
        HashSet<String> pagesUrls = new LinkedHashSet<>();
        String initialPartOfUrlString = "http://www.sql.ru/forum/job-offers/";
        for (int i = 1; i < 100; i++) {
            String currUrl = initialPartOfUrlString + String.valueOf(i);
            pagesUrls.add(currUrl);
        }
        if (workWithDataBase.itIsFirsLoad()) {
            for (int i = 101; i < 645; i++) {
                String currUrl = initialPartOfUrlString + String.valueOf(i);
                if (!workWithDataBase.thisUrlExist(currUrl, "new")) {
                    pagesUrls.add(currUrl);
                }
            }
        }
        return pagesUrls;
    }

    void getVacFromPages(WorkWithDataBase workWithDataBase) {
        workWithDataBase.addCurrDateToTable();
        HashSet<String> pagesUrl = this.getPagesUrls(workWithDataBase);
        Iterator iterator = pagesUrl.iterator();
        while (iterator.hasNext()) {
            String tableOfVacUrl = (String) iterator.next();
            getVacFromPage(tableOfVacUrl, workWithDataBase);
        }
    }

    private HashSet<String> getUrlsFromPageWhereUrlContainsWordForum(String pageUrl) {
        HashSet<String> urlsFromPage = new LinkedHashSet<>();
        Elements allElelementsFromPage = this.getElementsFromUrl(pageUrl);
        for (Element elFromPage : allElelementsFromPage) {
            if (elFromPage.absUrl("href").split("http://www.sql.ru/forum/").length > 1) {
                urlsFromPage.add(elFromPage.absUrl("href"));
            }
        }
        return urlsFromPage;
    }

    private HashSet<String> filterUrlsWhereIdIsGraterThen10000(HashSet<String> urlsFromPage) {
        HashSet<String> urlsVacFromPage = new LinkedHashSet<>();
        for (String urlFromPage : urlsFromPage) {
            try {
                if (urlFromPage.split("/").length > 4
                        && urlFromPage.split("/")[4].split("=").length > 0
                        &&
                        Integer.parseInt(urlFromPage.split("/")[4].split("=")[0]) > 100000
                        ) {
                    urlsVacFromPage.add(urlFromPage);
                }
            } catch (Exception e) {
            }
        }
        return urlsVacFromPage;
    }

    private HashSet<Vacancy> filterUrlsWhereContainsJavaVacancy(HashSet<String> urlsVacFromPage, String pageUrl) {
        HashSet<Vacancy> onlyJavaVacancies = new LinkedHashSet<>();
        for (String urlVacFromPage : urlsVacFromPage) {
            Elements elementsVacancy = this.getElementsFromUrl(urlVacFromPage); //docVacancy.getAllElements();
            for (Element elVacancy : elementsVacancy) {
                if (this.isThatElementisVacancy(elVacancy)) {
                    Node nameOfVacancyNode;
                    nameOfVacancyNode = elVacancy.childNodes().get(2).childNodes().get(0).childNodes().get(1).childNodes().get(0);
                    String nameOfVac = nameOfVacancyNode.toString();
                    if (this.itIsOnlyJavaVacanciesByName(nameOfVac)) {
                        Vacancy vacancy = new Vacancy(nameOfVac, urlVacFromPage, pageUrl);
                        onlyJavaVacancies.add(vacancy);
                    }
                }
            }
        }
        return onlyJavaVacancies;
    }

    private boolean isThatElementisVacancy(Element elVacancy) {
        boolean thatElementisVacancy = false;
        thatElementisVacancy = !(
                elVacancy.childNodes().isEmpty()
                        || (elVacancy.childNodes().size() <= 2)
                        || elVacancy.childNodes().get(2).attributes().size() == 1
                        || (elVacancy.childNodes().get(2).childNodes().size() < 1)
                        || elVacancy.childNodes().get(2).childNodes().isEmpty()
                        || elVacancy.childNodes().get(2).childNodes().get(0).attributes().size() == 1
                        || elVacancy.childNodes().get(2).childNodes().get(0).childNodes().isEmpty()
                        || (elVacancy.childNodes().get(2).childNodes().get(0).childNodes().size() < 2)
                        || elVacancy.childNodes().get(2).childNodes().get(0).childNodes().get(1).attributes().size() == 1
                        || (elVacancy.childNodes().get(2).childNodes().get(0).childNodes().get(1).childNodes().size() < 1)
        );
        return thatElementisVacancy;
    }

    private Elements getElementsFromUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGG.error(e.getMessage(), e);
        }
        Elements elements = doc.getAllElements();
        return elements;
    }

    private boolean itIsOnlyJavaVacanciesByName(String nameOfVac) {
        boolean itIsOnlyJavaVacancy = false;
        itIsOnlyJavaVacancy = (nameOfVac.toLowerCase().split("java").length > 1)
                && !((nameOfVac.toLowerCase().split("javascript").length > 1))
                && !((nameOfVac.toLowerCase().split("java script").length > 1));
        return itIsOnlyJavaVacancy;
    }

    private HashSet<Vacancy> getfilteredOnlyJavaVacancies(String pageUrl) {
        HashSet<String> urlsFromPage = this.getUrlsFromPageWhereUrlContainsWordForum(pageUrl);
        HashSet<String> urlsVacFromPage = this.filterUrlsWhereIdIsGraterThen10000(urlsFromPage);
        HashSet<Vacancy> filteredOnlyJavaVacancies = this.filterUrlsWhereContainsJavaVacancy(urlsVacFromPage, pageUrl);
        return filteredOnlyJavaVacancies;
    }

    private void addJavaVacanciesToDataBase(HashSet<Vacancy> filteredOnlyJavaVacancies, WorkWithDataBase workWithDataBase) {
        for (Vacancy vacancy : filteredOnlyJavaVacancies) {
            workWithDataBase.addVacancyToDataBase(vacancy);
        }
    }

    private void getVacFromPage(String pageUrl, WorkWithDataBase workWithDataBase) {
        HashSet<Vacancy> filteredOnlyJavaVacancies = this.getfilteredOnlyJavaVacancies(pageUrl);
        this.addJavaVacanciesToDataBase(filteredOnlyJavaVacancies, workWithDataBase);
    }
}