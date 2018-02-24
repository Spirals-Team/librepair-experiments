package me.maxwu.genre.selenide.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$$;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by maxwu on 3/14/17.
 */
public class BillboardTop100Page {
    static Logger logger = LoggerFactory.getLogger(BillboardTop100Page.class.getName());

    private ElementsCollection getTop100Rows() {
        return $$(By.cssSelector("div.chart-row__main-display"));
    }

    public Map<Integer, Map<String, Object>> getTop100Map(){
        return getTop100Map(100);
    }

    // We don't check size here for casual case.
    public Map<Integer, Map<String, Object>> getTop100Map(int size){
        Map<Integer, Map<String, Object>> top100Map = new HashMap<>(100);
        for(WebElement rowDiv: getTop100Rows().stream().limit(size).collect(Collectors.toList())){
            Map<String, Object> rowMap = new HashMap<>(3);
            String rank = rowDiv.findElement(By.cssSelector("span.chart-row__current-week")).getText().trim();
            logger.debug("rank : " + rank);
            String song = rowDiv.findElement(By.cssSelector("h2.chart-row__song")).getText().trim();
            logger.debug("song : " + song);
            // Mixed 3 "h3" elements and 97 "a" elements.
            String artist = rowDiv.findElement(By.cssSelector(".chart-row__artist")).getText().trim();
            logger.debug("artist : " + artist);

            rowMap.put("song", song);
            rowMap.put("artist", artist);
            top100Map.put(Integer.valueOf(rank), rowMap);
        }
        logger.debug("total size is " + top100Map.size());
        return top100Map;
    }

    /**
    private ElementsCollection getTop100SongH2() {
        return $$(By.cssSelector("h2.chart-row__song"));
    }

    public List<String> getTop100SongNames() {
        return getTop100SongH2().stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    private ElementsCollection getTop100SongArtistsA() {
        return $$(By.cssSelector(".chart-row__artist"));
    }

    public List<String> getTop100ArtistNames() {
        return getTop100SongArtistsA().stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }
     **/
}
