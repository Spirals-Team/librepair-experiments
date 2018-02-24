package me.maxwu.genre;

import java.util.List;
import java.util.Map;

/**
 * Created by maxwu on 3/19/17.
 */

// TODO: Cache with invoked proxy
public interface IGenreCmd {
    <T> List<T> getSongGenres(String song, String artist);
    <K, O, V> Map<K, Map<O, V>> getBillboardTop100Map();
    <K, O, V> Map<K, Map<O, V>> getBillboardTop100Map(int size);
}
