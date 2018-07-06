package ru.job4j.last.main;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ru.job4j.last.config.Config;
import ru.job4j.last.connection.NoSuchDbException;
import ru.job4j.last.connection.StoreFactory;
import ru.job4j.last.dao.Offer;
import ru.job4j.last.dao.OfferDao;
import ru.job4j.last.date.DateConverter;
import ru.job4j.last.date.IDateConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yury Matskevich
 */
public class Parse {
	private static final Logger LOG = Logger.getLogger(Parse.class);
	private String pagePath;
	private String regxSearch;
	private OfferDao offerDao;
	private IDateConverter dateConv = new DateConverter();

	/**
	 * Creates an object of {@link Parse} for parsing a section of
	 * vacancy on the website called SQL.ru
	 * @param config An object of {@link Config}  which contains
	 * settings for the current program
	 * @throws NoSuchDbException if there is no connection setting for
	 * the current db in {@link StoreFactory}
	 */
	public Parse(String config) throws NoSuchDbException {
		Config conf = new Config(config);
		pagePath = conf.getProperty("jdbc.url");
		regxSearch = conf.getProperty("regx.search");
		String dbName = conf.getProperty("jdbc.dbName");
		offerDao = new OfferDao(new StoreFactory(config).createConnection(dbName));
	}

	/**
	 * Adds all the suitable offers from a vacancy section of
	 * SQL.ru
	 */
	public void addFoundOffersToStore() {
		LOG.info("Start finding new offers");
		addToDb(
				searchOnAllPages(
						getNumberOfMaxPage(),
						offerDao.getTheNewestDate(),
						dateConv.getLongOfStartOfCurrentYear()
				)
		);
	}

	/**
	 * Searches suitable offers on all pages of section
	 * @param maxPage a max page in section
	 * @param lastDate a date the offer which was last added to
	 * the db
	 * @param dateStartYear the value of the current year
	 * @return {@link List} of suitable offers from vacancy section
	 */
	private List<Offer> searchOnAllPages(int maxPage, long lastDate, long dateStartYear) {
		List<Offer> offers = new ArrayList<>();
		for (int page = 1; page <= maxPage; page++) {
			Elements elems = getDocumentOfCurrentPage(page)
					.select("table.forumTable tbody tr");
			if (!searchOnCurrentPage(lastDate, dateStartYear, elems, offers)) {
				break;
			}
		}
		return offers;
	}

	/**
	 * Searches suitable offers on the current page of section
	 * @param lastDate a date the offer which was last added to
	 * the db
	 * @param dateStartYear the value of the current year
	 * @param elems set of information about offers in form of {@link Elements}
	 * @param offers a reference to the {@link List} where offers store
	 * @return true if it's necessary search for any offers on next page,
	 * otherwise - false
	 */
	private boolean searchOnCurrentPage(long lastDate, long dateStartYear, Elements elems, List<Offer> offers) {
		boolean result = true;
		int amountElems = elems.size();
		for (int i = 4; i < amountElems; i++) {
			if (stopSearching(lastDate, dateStartYear, elems, i)) {
				result = false;
				break;
			}
			Elements subElems = elems.get(i).select("td.postslisttopic a");
			String head = subElems.text();
			if (checkKeyWord(head)) {
				String id = subElems.attr("href");
				addToList(id, elems, i, head, offers);
			}
		}
		return result;
	}

	/**
	 * Adds an offer to the {@link List} called offer
	 * @param id an id of the current offer
	 * @param elems set of information about offers in form of {@link Elements}
	 * @param numberOffer - a number of current offer in set of elems
	 * @param head - a title of offer
	 * @param offers a reference to the {@link List} where offers store
	 */
	private void addToList(String id, Elements elems, int numberOffer, String head, List<Offer> offers) {
		try {
			String description = Jsoup
					.connect(id)
					.get()
					.select("td.msgBody")
					.get(1)
					.text();
			long create = dateConv.convertInLong(
					elems.get(numberOffer).select("td.altCol").get(1).text()
			);
			offers.add(new Offer(id, head, description, create));
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Adds offers to the db
	 * @param offers {@link List} of offer to the db
	 */
	private void addToDb(List<Offer> offers) {
		if (offers.isEmpty()) {
			LOG.info("There is no new offers for adding");
			return;
		}
		offerDao.add(offers);
	}

	/**
	 * A condition for stopping of searching
	 * @param lastDate a date the offer which was last added to
	 * the db
	 * @param dateStartYear the value of the current year
	 * @param elems set of information about offers in form of {@link Elements}
	 * @param numberOffer a number of current offer in set of elems
	 * @return true if it's necessary to stop searching, otherwise - false
	 */
	private boolean stopSearching(
			long lastDate, long dateStartYear, Elements elems, int numberOffer) {
		long dateCurrOffer = dateConv.convertInLong(
				elems.get(numberOffer).select("td.altCol").get(1).text()
		);
		return dateCurrOffer < dateStartYear | dateCurrOffer <= lastDate;
	}

	/**
	 * Gets numbers of a last page in the section
	 * @return a numbers of a last page
	 */
	private int getNumberOfMaxPage() {
		Document doc = null;
		int result = 1;
		try {
			doc = Jsoup.connect(
					String.format("%s/%d", pagePath, 1)
			).get();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		if (doc != null) {
			result = Integer.parseInt(
					doc.select("table.sort_options a").last().text()
			);
		}
		return result;
	}

	/**
	 * Gets a content of the current page like an object of
	 * {@link Document}
	 * @param page a number of page
	 * @return {@link Document} of a current page
	 */
	private Document getDocumentOfCurrentPage(int page) {
		Document doc = null;
		try {
			doc = Jsoup.connect(
					String.format("%s/%d", pagePath, page)
			).get();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return doc;
	}

	/**
	 * Checks if the string contains the search word.
	 *
	 * @param str A search word.
	 * @return true if string contains the search word,
	 * else - otherwise
	 */
	private boolean checkKeyWord(String str) {
		Pattern p = Pattern.compile(
				regxSearch,
				Pattern.CASE_INSENSITIVE
		);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
