package ru.job4j.sql;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ConsoleUtilSQL {
    public void work(String url, String login, String password, int value) {
        long timeStar;
        long timeStop;
        Print date = new Print();
        SQLConnect connect = new SQLConnect(url, login, password, false);
        SQLRequest request = new SQLRequest(connect.getConnection());
        date.printDate("Connect start");
        ParsingWork parsingWork;
        timeStar = System.currentTimeMillis();
        request.createTable();
        date.printDate(String.format("Create table and add (1..%d) values", value));
        request.addData(value);
        date.printDate("Done");
        date.printDate("Create XML");
        JAXBWorker jaxbWorker = new JAXBWorker();
        jaxbWorker.work(request.selectItems(), "chapter_006/1.xml");
        date.printDate("Done");
        date.printDate("Convert XML");
        XSLTWorker xsltWorker = new XSLTWorker();
        xsltWorker.work("chapter_006/1.xml", "chapter_006/2.xml", "chapter_006/style.xsl");
        date.printDate("Done");
        date.printDate("Parsing XML");
        parsingWork = new ParsingWork();
        date.printDate(String.format("Done (sum = %d)", parsingWork.work("chapter_006/2.xml")));
        connect.close();
        date.printDate("Connect end");
        timeStop = System.currentTimeMillis();
        long totalTime = (timeStop - timeStar) / 1000;
        int min = (int) totalTime / 60;
        int sec = (int) totalTime - (min * 60);
        System.out.println(String.format("Total time work: %d min %d sec", min, sec));
    }
}
