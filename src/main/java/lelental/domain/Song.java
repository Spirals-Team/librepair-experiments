package lelental.domain;

/**
 * @author Paweł Lelental
 **/
public class Song {

    private String ellapsedTime;
    private String name;

    public Song(String ellapsedTime, String name) {
        this.ellapsedTime = ellapsedTime;
        this.name = name;
    }

    public String getEllapsedTime() {
        return ellapsedTime;
    }

    public void setEllapsedTime(String ellapsedTime) {
        this.ellapsedTime = ellapsedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
