package me.maxwu.genre.htmlUnit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import me.maxwu.genre.IGenreKeyword;
import me.maxwu.genre.GenreTerm;
import me.maxwu.genre.IGenreCmd;
import me.maxwu.genre.search.GoogleKeyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.maxwu.genre.GenreTerm.getDefaultGenreList;
import static me.maxwu.genre.GenreTerm.isDefaultGenreList;
import static me.maxwu.genre.selenide.SelenideBase.keywordArtistGenre;
import static me.maxwu.genre.selenide.SelenideBase.keywordGenre;
import static me.maxwu.genre.selenide.SelenideBase.keywordSongGenre;

/**
 * Created by maxwu on 3/19/17.
 */
public class HtmlUnitBase implements IGenreCmd{
    static Logger logger = LoggerFactory.getLogger(HtmlUnitBase.class.getName());
    private WebClient webClient;

    public HtmlUnitBase(){
        webClient = new WebClient(BrowserVersion.CHROME);
        // inhibit the tons of error messages with HtmlUnit and its JavaScript engine.
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        webClient.setJavaScriptTimeout(4000);
    }

    @Override
    public List<String> getSongGenres(String song, String artist) {
        for( String keyword : new GoogleKeyword().getKeyWordList(song, artist)){
            List<String> genres = searchGenreFor(keyword);
            if (!isDefaultGenreList(genres)){
                return genres;
            }
        }
        return getDefaultGenreList();
    }

    private WebClient getWebClient(){
        return this.webClient;
    }

    private List<String> searchGenreFor(String text) {
        String req = "https://www.google.com/search?q=" + text;
        List<String> genres;
        WebClient webClient = getWebClient();

        try {
            final HtmlPage page = webClient.getPage(req);
            String result;
            List<HtmlDivision> divs = page
                .getByXPath("//div[@class=\"_XWk\"]");
                //"//*[@id=\"rso\"]/div[1]/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div/div[1]");
            if ((divs == null) || (divs.isEmpty())){
                // As List: //div[@class="title"]
                divs = page.getByXPath("//div[@class=\"_Mjf\"]/div[@class=\"title\"]");
                if ((divs == null) || (divs.isEmpty())){
                    throw new RuntimeException("0 div found for genres");
                }
                result = divs.stream().map(d -> d.getTextContent().trim())
                        .collect(Collectors.joining(new GoogleKeyword().getDelimiter()));
            }else{
                result = divs.get(0).getTextContent();
            }

            logger.debug("Query: [" + text + "] , return: [" + result + "]");

            genres = Arrays.asList(result.split("/"));
        }catch (IOException e){
            logger.warn("Failed to query with [" + text + "]: " + e.getMessage());
            genres = GenreTerm.getDefaultGenreList();
        }catch (Exception e){
            logger.info("Failed to query with [" + text + "]: " + e.getMessage());
            genres = GenreTerm.getDefaultGenreList();
        }
        finally {
            webClient.close();
        }
        return genres;
    }

    @Override
    public Map<Integer, Map<String, Object>> getBillboardTop100Map(){
        return getBillboardTop100Map(100);
    }

    @Override
    public Map<Integer, Map<String, Object>> getBillboardTop100Map(int size) {
        Map<Integer, Map<String, Object>> map = new HashMap<>(size);
        String req = "http://www.billboard.com/charts/hot-100";
        WebClient webClient = getWebClient();
        try {
            HtmlPage page = webClient.getPage(req);
            List<HtmlSpan> spans = page.getByXPath("//span[@class=\"chart-row__current-week\"]");
            List<HtmlHeading2> h2s = page.getByXPath("//h2[@class=\"chart-row__song\"]");
            // Mixed 3 "a" and 97 "h3" elements
            List<HtmlElement> elements = page.getByXPath("//div[@class=\"chart-row__title\"]/*[@class=\"chart-row__artist\"]");

            IntStream.rangeClosed(0, size-1).limit(100).forEach(i -> {
                map.put(Integer.valueOf(spans.get(i).getTextContent().trim()),
                        new HashMap<String, Object>() {{
                            put("song", h2s.get(i).getTextContent().trim());
                            put("artist", elements.get(i).getTextContent().trim());
                        }});
            });
        }catch (IOException | IndexOutOfBoundsException e){
            logger.warn("Failed to query top100: " + e.getMessage());
        }finally {
            webClient.close();
        }

        return map;
    }

    public static void main(String[] args){
        String song = "That's What I Like";
        String artist = "Bruno Mars";
        String genres = new HtmlUnitBase().getSongGenres(song, artist).stream().collect(Collectors.joining(", "));
    }
}
