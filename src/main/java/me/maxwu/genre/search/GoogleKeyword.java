package me.maxwu.genre.search;

import me.maxwu.genre.IGenreCmd;
import me.maxwu.genre.IGenreKeyword;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxwu on 3/21/17.
 */
public class GoogleKeyword implements IGenreKeyword{

    public String getDelimiter(){
        return "/";
    }

    public List<String> getKeyWordList(String song, String artist){
        return new ArrayList<String>(){{
            add(song + " song genre");
            add(song + " genre");
            add(artist + " - " + song + " genre");
        }};
    }
}
