package guru.bonacci.oogway.sannyas.service.qd;


import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.service.general.PageCache;
import guru.bonacci.oogway.sannyas.service.general.WebScraper;
import guru.bonacci.oogway.shareddomain.GemCarrier;

/**
 * Most popular quote:
 * “Don't cry because it's over, smile because it happened.” ― Dr. Seuss
 */
@RefreshScope
@Component
public class QDScraper extends WebScraper implements PageCache {

	private final Logger logger = getLogger(this.getClass());

	@Value("${web.url.qd:https://www.quotesdaddy.com/find/tag/#tag#/#page#}")
	private String url;

	@Override
	protected String determineURL(String searchStr) {
		String searchURL = url.replace("#tag#", searchStr);
		Integer nrOfPages = getNrOfPages(searchURL.replace("#page#", "1"));
		return searchURL.replace("#page#", String.valueOf(nextInt(1, nrOfPages + 1)));
	}

	@Override
    @Cacheable("pages")
	public Integer getNrOfPages(String searchURL) {
		int pageNr = 1;
		try {
			Document doc = get(searchURL);
			Element element = doc.select("div.topPager").select("a").first();
			while (element != null) {
				try {
					String href = element.attr("href");
					pageNr = Integer.parseInt(StringUtils.substringAfterLast(href, "/"));
			    } catch (Exception ignore) {}

				element = element.nextElementSibling();
			}
		} catch (Exception e) { 
			// Taking the easy way, catching all exceptions.
			// No results or one result: page 1
			// Not enough results for a gap between the pagination numbers: page 1
			// More than two results: page x
			logger.error("Not too many pages, return 1: " + e.getMessage());
		}
		return pageNr;
    }

	@Override
	public Elements consultWeb(String searchURL) {
		try {
			logger.debug("Firing request " + searchURL);
			Document doc = get(searchURL);
			return doc.select("div.quoteObject");
		} catch (IOException e) {
			logger.error("Something went wrong. No stress, it does not need to be serieus: " + e.getMessage());
		}
		return new Elements();
	}

	@Override
	public GemCarrier toGem(Element el) {
		String quote = stripText(el.select("div.quoteText").select("a").first().ownText());
		String author = stripText(el.select("p.authorName").select("a").first().ownText());
		return new GemCarrier(quote, author);
	}

	private String stripText(String str) {
		return str.replace("“", "").replace("”", "");
	}
}
