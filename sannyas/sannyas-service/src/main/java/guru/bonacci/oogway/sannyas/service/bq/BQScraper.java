package guru.bonacci.oogway.sannyas.service.bq;


import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

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
 * Quote of the day today:
 * “I want to do to you what spring does with the cherry trees.” ― Pablo Neruda
 */
@RefreshScope
@Component
public class BQScraper extends WebScraper implements PageCache {

	private final Logger logger = getLogger(this.getClass());

    @Value("${web.url.bq:https://www.brainyquote.com/topics/#tag#_#page#?vm=l}")
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
		Integer pageNr = 1;
		try {
			Document doc = get(searchURL);
			Elements elements = doc.select("ul.pagination");
			elements = elements.first().getElementsByAttribute("href");
			for (int i=0; i<elements.size(); i++) {
				try {
					pageNr = Integer.parseInt(elements.get(i).text());
			    } catch (NumberFormatException ignore) {}
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
	protected Elements consultWeb(String searchURL) {
		try {
			logger.debug("Firing request " + searchURL);
			Document doc = get(searchURL);
			return doc.select("div[id='quotesList']").select("div.bqQt");
		} catch (IOException e) {
			logger.error("Something went wrong. No stress, it does not need to be serieus: " + e.getMessage());
		}
		return new Elements();
	}
	
	@Override
	public GemCarrier toGem(Element el) {
		String quote = el.select("a[title='view quote']").first().ownText();
		
		Elements els = el.select("a[title='view author']");
		String author = els.size() > 0 ? els.first().ownText() : null;

		return new GemCarrier(quote, author);
	}
}
