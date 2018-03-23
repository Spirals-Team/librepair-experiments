package com.s14222.tau;

import com.s14222.tau.dbdemo.service.UndeadManagerImpl;
import com.s14222.tau.domain.Undead;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );

        UndeadManagerImpl undeadManager;

        String url = "jdbc:mysql://localhost/aga";
        try {
            Class.forName("com.mysql.jdbc.Driver");  //STEP 2: Register JDBC driver

            undeadManager = new UndeadManagerImpl(DriverManager.getConnection(url,"root", ""));
            Undead undead = new Undead(0, "Aga", 100, 100, 100);
            undeadManager.addUndead(undead);

            List<Undead> undeads = undeadManager.getAllUndeads();

            for(Undead u : undeads ) {
                if(u.getNazwa().contains("Aga")){
                    undeadManager.deleteUndead(u);
                    System.out.println("Usunieto Undead: " + u.nazwa);
                }
                System.out.println("Undead: " + u.nazwa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
