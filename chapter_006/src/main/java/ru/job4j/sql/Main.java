package ru.job4j.sql;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Main {
    public static void main(String[] args) {
        ConsoleUtilSQL sql = new ConsoleUtilSQL();
        String url;
        String login;
        String password;
        int n;
        if (args.length == 0) {
            url = "jdbc:postgresql://localhost:5432/test";
            login = "postgres";
            password = "Qwerty123";
            n = 10;
        } else if (args.length == 4) {
            url = args[0];
            login = args[1];
            password = args[2];
            n = Integer.parseInt(args[3]);
        } else {
            System.out.println("Error input..");
            return;
        }
        sql.work(url, login, password, n);
    }
}
