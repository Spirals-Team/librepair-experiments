package ru.job4j.jsoup;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Setup {
    private static Logger log = Logger.getLogger(Parser.class);
    private String url;
    private List<String> jobUrls = new ArrayList<>();
    private String login;
    private String password;
    private int hour = 24;
    private boolean flagProvlem = false;

    Setup(String fileName) {
        log.info(String.format("read setting (%s)", fileName));
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String stLine;
            while ((stLine = bufferedReader.readLine()) != null) {
                if (stLine.contains("#url=")) {
                    this.url = stLine.replace("#url=", "");
                } else if (stLine.contains("#joburl=")) {
                    this.jobUrls.add(stLine.replace("#joburl=", ""));
                } else if (stLine.contains("#login=")) {
                    this.login = stLine.replace("#login=", "");
                } else if (stLine.contains("#password=")) {
                    this.password = stLine.replace("#password=", "");
                } else if (stLine.contains("#hr=")) {
                    this.hour = Integer.valueOf(stLine.replace("#hr=", ""));
                }
            }
            if (url == null) {
                throw new NullPointerException("url is null");
            } else if (login == null) {
                throw new NullPointerException("login is null");
            } else if (password == null) {
                throw new NullPointerException("password is null");
            } else if (jobUrls.isEmpty()) {
                throw new NullPointerException("job urls is null");
            }
        } catch (Exception e) {
            flagProvlem = true;
            log.error(e);
        }
    }

    public String getUrl() {
        return this.url;
    }

    public List<String> getJobUrls() {
        return this.jobUrls;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public int getHour() {
        return this.hour;
    }

    public boolean isFlagProvlem() {
        return this.flagProvlem;
    }
}
