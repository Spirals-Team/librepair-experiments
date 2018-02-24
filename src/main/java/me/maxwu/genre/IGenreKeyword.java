package me.maxwu.genre;

import java.util.List;

/**
 * Created by maxwu on 3/21/17.
 */
public interface IGenreKeyword {
    // A series of keywords to query in turn
    <T> List<T> getKeyWordList(String song, String artist);

    String getDelimiter();
}
