package ru.iac.domain;

import ru.iac.utils.TimeFormat;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Entity(name = "paths")
public class PathTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "timecreated")
    private Timestamp time;

    private String path;

    private int countDir;

    private int countFile;

    private String sumFile;

    public PathTable() {
    }

    public PathTable(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return TimeFormat.getTime(time);
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCountDir() {
        return countDir;
    }

    public void setCountDir(int countDir) {
        this.countDir = countDir;
    }

    public int getCountFile() {
        return countFile;
    }

    public void setCountFile(int countFile) {
        this.countFile = countFile;
    }

    public String getSumFile() {
        return sumFile;
    }

    public void setSumFile(String sumFile) {
        this.sumFile = sumFile;
    }
}
