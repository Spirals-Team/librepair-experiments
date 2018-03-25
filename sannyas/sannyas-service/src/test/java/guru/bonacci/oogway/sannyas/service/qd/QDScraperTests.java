package guru.bonacci.oogway.sannyas.service.qd;

import static guru.bonacci.oogway.utils.MyFileUtils.readToList;
import static guru.bonacci.oogway.utils.MyFileUtils.readToString;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.jsoup.Jsoup.parse;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannyas.service.SannyasTestApp;
import guru.bonacci.oogway.sannyas.service.qd.QDScraper;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SannyasTestApp.class, webEnvironment=NONE)
public class QDScraperTests {

	@Spy
	QDScraper finder;
	
	@Test
	public void shouldRetrieveQuotes() throws IOException {
		Document doc = parse(readToString("qd/qd-mock-baby.txt"));
		doReturn("does not matter").when(finder).determineURL(anyString());
		doReturn(doc).when(finder).get(anyString());

		List<GemCarrier> found = finder.find("baby");
		List<String> quotes = found.stream().map(GemCarrier::getSaying).collect(toList());
		List<String> expected = readToList("qd/qd-quotes-baby.txt");

		//TODO assertEquals(expected, quotes);
	}

	@Test
	public void shouldRetrieveAuthors() throws IOException {
		Document doc = parse(readToString("qd/qd-mock-baby.txt"));
		doReturn("does not matter").when(finder).determineURL(anyString());
		doReturn(doc).when(finder).get(anyString());

		List<GemCarrier> found = finder.find("baby");
		assertThat(found.get(0).getAuthor(), is(equalTo("Amy Heckerling")));
	}

	@Test
	public void shouldFindNumerOfPagesWhenMany() throws IOException {
		Document doc = parse(readToString("qd/qd-mock-baby.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the baby url");
		assertThat(nrOfPages, is(equalTo(109)));
	}

	@Test
	public void shouldFindNumberWhenFew() throws IOException {
		Document doc = parse(readToString("qd/qd-mock-unimaginable.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the baby url");
		assertThat(nrOfPages, is(equalTo(2)));
	}
	
	@Test
	public void shouldReturnOneWhenNoPages() throws IOException {
		Document doc = parse(readToString("qd/qd-mock-aaaaa.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the aaaaa url");
		assertThat(nrOfPages, is(equalTo(1)));
	}
}
