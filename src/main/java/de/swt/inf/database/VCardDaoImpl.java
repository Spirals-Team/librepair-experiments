package de.swt.inf.database;

import de.swt.inf.model.VCard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VCardDaoImpl implements VCardDao {

    private Connection connection;

    public VCardDaoImpl(Connection connection) {this.connection = connection;}


    public VCard getVCard(VCard vCard) {
        try{
            String query = "SELECT * FROM vcard WHERE VCARD_ID = " + vCard.getId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            VCard tempVCard = new VCard();
            rs.next();
            tempVCard.setId(rs.getInt("VCARD_ID"));
            tempVCard.setFirstname(rs.getString("Firstname"));
            tempVCard.setLastname(rs.getString("Lastname"));
            tempVCard.setTelNr(rs.getString("TelNr"));
            tempVCard.setOffice(rs.getString("Office"));
            tempVCard.setTitle(rs.getString("Title"));
            tempVCard.setEmail(rs.getString("Email"));
            tempVCard.setNote(rs.getString("Note"));
            return tempVCard;
        }catch (SQLException e){
            System.err.println(e);
        }
        return null;
    }

    public VCard getVCard(int id) {
        try{
            String query = "SELECT * FROM vcard WHERE VCARD_ID = " + id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            VCard tempVCard = new VCard();
            rs.next();
            tempVCard.setId(rs.getInt("VCARD_ID"));
            tempVCard.setFirstname(rs.getString("Firstname"));
            tempVCard.setLastname(rs.getString("Lastname"));
            tempVCard.setTelNr(rs.getString("TelNr"));
            tempVCard.setOffice(rs.getString("Office"));
            tempVCard.setTitle(rs.getString("Title"));
            tempVCard.setEmail(rs.getString("Email"));
            tempVCard.setNote(rs.getString("Note"));
            return tempVCard;
        }catch (SQLException e){
            System.err.println(e);
        }
        return null;
    }

    public List<VCard> getAllVCards() {
        List<VCard> alleVCards = new ArrayList<VCard>();
        try{
            String query = "SELECT * FROM vcard";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next() == true){
                VCard tempVCard = new VCard();
                tempVCard.setId(rs.getInt("VCARD_ID"));
                tempVCard.setFirstname(rs.getString("Firstname"));
                tempVCard.setLastname(rs.getString("Lastname"));
                tempVCard.setTelNr(rs.getString("TelNr"));
                tempVCard.setOffice(rs.getString("Office"));
                tempVCard.setTitle(rs.getString("Title"));
                tempVCard.setEmail(rs.getString("Email"));
                tempVCard.setNote(rs.getString("Note"));
                alleVCards.add(tempVCard);
            }
            return alleVCards;
        }catch (SQLException e){
            System.err.println(e);
        }
        return alleVCards;
    }

    public boolean updateVCard(VCard vCard) {
        try{
            String query = "UPDATE vcard SET Firstname = '" + vCard.getFirstname() + "', Lastname = '" + vCard.getLastname()
                    + "', TelNr ='" + vCard.getTelNr() + "', Office ='" + vCard.getOffice() + "', Title ='" + vCard.getTitle()
                    + "', Email ='" + vCard.getEmail() + "', Note ='" + vCard.getNote() + "' WHERE VCARD_ID ='" + vCard.getId() +"'";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        }catch (SQLException e){
            System.err.println(e);
        }
        return false;
    }

    public boolean deleteVCard(VCard vCard){
        try{
            String query = "DELETE FROM vcard WHERE VCARD_ID = " + vCard.getId();
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        }catch (SQLException e){
            System.err.println(e);
        }
        return false;
    }

    public boolean deleteVCard(int id) {
        try{
            String query = "DELETE FROM VCard WHERE VCARD_ID = " + id;
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        }catch (SQLException e){
            System.err.println(e);
        }
        return false;
    }

    public boolean addVCard(VCard vCard) {
        try{
            String query = "INSERT INTO vcard VALUES ('" + vCard.getId() + "', '" + vCard.getFirstname() + "', '" +
                    vCard.getLastname() + "', '" + vCard.getTelNr() + "', '" + vCard.getOffice() + "', '" +
                    vCard.getTitle() + "', '" + vCard.getEmail() + "', '" + vCard.getNote() + "')";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        }catch (SQLException e){
            System.err.println(e);
        }
        return false;
    }
}
