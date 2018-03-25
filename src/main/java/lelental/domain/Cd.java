package lelental.domain;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
public class Cd {

    private String title;
    private List<Song> songList;
    private List<Author> authors;

    public Cd(String title, List<Song> songList, List<Author> authors) {
        this.title = title;
        this.songList = songList;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
