package guru.bonacci.oogway.sannyas.service.general;

import java.util.List;

import guru.bonacci.oogway.shareddomain.GemCarrier;

/**
 * Scraper:
 * a tool or device used for scraping, especially for removing dirt, paint, 
 * or other unwanted matter from a surface.
 */
public interface Scraper {

	List<GemCarrier> find(String... tags);
}
