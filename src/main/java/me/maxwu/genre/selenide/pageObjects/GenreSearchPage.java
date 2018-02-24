package me.maxwu.genre.selenide.pageObjects;

import com.codeborne.selenide.ex.ElementNotFound;
import me.maxwu.genre.GenreTerm;
import me.maxwu.genre.search.GoogleKeyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by maxwu on 3/15/17.
 */
public class GenreSearchPage {
    static Logger logger = LoggerFactory.getLogger(GenreSearchPage.class.getName());

    public List<String> getGenres(){
        List<String> genres;
        try{
            genres = Arrays.asList($("#rso div._uX div._XWk")
                    .getText()
                    .split(new GoogleKeyword().getDelimiter()));
        }catch (ElementNotFound e){
            logger.info("Mark genre to NA due to error \n" + e.toString());
            genres = GenreTerm.getGenreList();
        }
        logger.debug(" >>> Genres: " + genres.stream().collect(Collectors.joining(", ")));
        return genres;
    }
}
