package guru.bonacci.oogway.sannyas.service.gr;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;
import static guru.bonacci.oogway.utilities.CustomFileUtils.readToString;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jsoup.Jsoup.parse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@ExtendWith(SpringExtension.class)
public class GRScraperTests {

	@Spy
	GRScraper scraper;
	
	@Test
	public void shouldRetrieveQuotes() throws IOException {
		Document doc = parse(readToString("gr/gr-mock-romance.txt"));
		doReturn(doc).when(scraper).get(anyString());

		List<GemCarrier> found = scraper.find("romance");
		List<String> quotes = found.stream().map(GemCarrier::getSaying).collect(toList());

		List<String> expected = readToList("gr/gr-quotes-romance.txt");

		//TODO assertEquals(expected, quotes);
	}

	@Test
	public void shouldRetrieveAuthors() throws IOException {
		Document doc = parse(readToString("gr/gr-mock-romance.txt"));
		doReturn(doc).when(scraper).get(anyString());

		List<GemCarrier> found = scraper.find("romance");
		assertThat(found.get(0).getAuthor(), is(equalTo("Stephenie Meyer")));
	}

	@Test
	public void shouldFindNumerOfPagesWhenMany() throws IOException {
		Document doc = parse(readToString("gr/gr-mock-romance.txt"));
		doReturn(doc).when(scraper).get(anyString());

		Integer nrOfPages = scraper.getNrOfPages("the romance url");
		assertThat(nrOfPages, is(equalTo(100)));
	}

	@Test
	public void shouldFindNumberWhenFew() throws IOException {
		Document doc = parse(readToString("gr/gr-mock-some.txt"));
		doReturn(doc).when(scraper).get(anyString());

		Integer nrOfPages = scraper.getNrOfPages("the romance url");
		assertThat(nrOfPages, is(equalTo(2)));
	}
	
	@Test
	public void shouldReturnOneWhenNoPages() throws IOException {
		Document doc = parse(readToString("gr/gr-mock-aaaaa.txt"));
		doReturn(doc).when(scraper).get(anyString());

		Integer nrOfPages = scraper.getNrOfPages("the aaaaa url");
		assertThat(nrOfPages, is(equalTo(1)));
	}
}
