package server.controllers;

import server.database.ConnectionPool;
import server.restklasser.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Nora on 19.01.2018.
 */
public class GjoremalController {
    private static final String TABELLNAVN = "gjøremål";
    private static PreparedStatement ps;
    private static Statement s;

    /**
     * Henter alle gjøremål som er felles for en spesifik husholdning
     * @param husholdningsId int id som skiller husholdninger fra hverandre
     * @return ArrayList med gjøremål
     */

    public static ArrayList<Gjøremål> hentFellesGjøremål(int husholdningsId) {
        ArrayList<Gjøremål> gjøremål = new ArrayList<>();
        String getQuery ="SELECT beskrivelse FROM gjøremål WHERE husholdningId = " + husholdningsId +" AND utførerId IS NULL ORDER BY frist";

        try(Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            ResultSet rs = getStatement.executeQuery();

            while(rs.next()){
                Gjøremål gjøremålet = new Gjøremål();
                gjøremålet.setBeskrivelse(rs.getString("beskrivelse"));
                gjøremål.add(gjøremålet);
            }
            return gjøremål;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lager et nytt gjøremåls-objekt
     * @param gjøremål
     * @return true dersom alt gikk bra, false dersom noe gikk galt.
     */
    public static boolean ny(Gjøremål gjøremål) {
        String beskrivelse = gjøremål.getBeskrivelse();
        int utførerId = gjøremål.getHhBrukerId();
        int husholdningId = gjøremål.getHusholdningId();
        Date frist = gjøremål.getFrist();

        String insertGjoremal = "insert into " + TABELLNAVN + " (beskrivelse, utførerId, husholdningId, frist, fullført) values (?,?,?,?,0)"; /*"insert into gjøremål (beskrivelse, utførerId, husholdningId, frist, fullført) values (?,?,?,?,0)";*/

        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement prepInsertGjoremal = connection.prepareStatement(insertGjoremal);
            prepInsertGjoremal.setString(1, beskrivelse);
            prepInsertGjoremal.setInt(2, utførerId);
            prepInsertGjoremal.setInt(3, husholdningId);
            prepInsertGjoremal.setDate(4, frist);
            prepInsertGjoremal.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  false;
    }
}

