package ru.job4j.tracker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Setup {
    private String url;
    private String login;
    private String password;
    private List<String> executeSQLList = new LinkedList<>();

    public Setup(Tracker tracker, String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String stLine;
            while ((stLine = bufferedReader.readLine()) != null) {
                if (stLine.contains("#url=")) {
                    url = stLine.replace("#url=", "");
                } else if (stLine.contains("#login=")) {
                    login = stLine.replace("#login=", "");
                } else if (stLine.contains("#password=")) {
                    password = stLine.replace("#password=", "");
                } else if (stLine.contains("#sql=")) {
                    executeSQLList.add(stLine.replace("#sql=", ""));
                }
            }
            if (url == null || login == null || password == null) {
                tracker.setProblem(true);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getExecuteSQLList() {
        return executeSQLList;
    }
}
