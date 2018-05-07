package de.swt.inf.controler;

import de.swt.inf.database.Connector;
import de.swt.inf.model.VCard;
import de.swt.inf.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


@RestController
@EnableAutoConfiguration
@ComponentScan()
public class Main {

    public static List<Category> categories = new ArrayList<Category>();
    public static List<VCard> vCards = new ArrayList<VCard>();


    public static Calendar defaultCalendar = new Calendar(new User("default", "123456778",
            "email", "max", "musterman"));

    public static void main(String[] args) {


        /**
        try{
            JdbcConnectionSource conn = new JdbcConnectionSource();
            conn.setDatabaseType(new MariaDbDatabaseType());
            conn.setUrl("jdbc:mysql://localhost:3306/infswt");
            conn.setUsername("root");
            conn.setPassword("");
            conn.initialize();
            Dao<Termin,Integer> TerminDao = DaoManager.createDao(conn, Termin.class);

            TerminDao terminDao = DaoFactory.getTerminDao();



            Termin termin = new Termin();
            termin.setName("Komisch");
            termin.setStart("2018-01-16");
            termin.setEnd("2018-01-18");
            termin.setAllDay(true);
            termin.setStartTime("12:00:00");
            termin.setEndTime("14:00:00");
            termin.setRepeat(RepeatTimes.weekly);
            termin.setId(8);


            Termin temp = terminDao.getTermin(termin);
            temp.setName("Get Termin");
            temp.setId(9);
            terminDao.addTermin(temp);
            //TerminDao.update(termin);
        }
        catch(SQLException ex){
            System.out.println(ex);
        }**/
        SpringApplication.run(Main.class, args);
    }

}
