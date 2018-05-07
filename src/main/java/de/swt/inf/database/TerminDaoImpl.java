package de.swt.inf.database;


import de.swt.inf.model.Termin;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;


public class TerminDaoImpl implements TerminDao {



    private Connection connection;

    public TerminDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean updateTermin(Termin termin) {
        try{
            int allDay = termin.getAllDay() == true ? 1 : 0;
            int repeat = termin.getRepeat() == true ? 1 : 0;
            int cancel = termin.getCancel() == true ? 1 : 0;
            int reminder = termin.getReminder() == true ? 1 : 0;

            String query = "UPDATE termin SET Name = '" + termin.getName() +"', Start = '" + termin.getStart() +
                    "', StartZeit = '" + termin.getStartTime() + "', Ende = '" + termin.getEnd() +"', EndeZeit ='"+
                    termin.getEndTime() + "', allDay = '" + allDay + "', Ort = '" + termin.getOrt() +
                    "', RepeatBool = '" + repeat + "',RepeatTime = '" + termin.getRepeatTime() + "', Cancel = '" + cancel + "', Attachement = '" +
                    termin.getAttachment() + "', Note = '" + termin.getNote() + "', Priority = '" + termin.getPriority() +
                    "', Reminder = '" + reminder + "', VCard = '1', CancelMsg = '" +
                    termin.getCancelMsg() + "', ReminderDate = '" + termin.getReminderDate() + "', ReminderTime = '" + termin.getReminderTime() + "' WHERE TERMIN_ID = '" +
                    termin.getId() + "'";
            Statement statement  = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        }catch(SQLException ex){
            System.err.println(ex);
        }
        return false;
    }



    public boolean deleteTermin(Termin termin) {
        try{
            String query = "DELETE FROM termin WHERE TERMIN_ID = "+ termin.getId();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        }catch(SQLException ex){
            System.err.println(ex);
        }


        return false;
    }

    public boolean deleteTermin(int id) {
        try{
            String query = "DELETE FROM termin WHERE TERMIN_ID = "+ id;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        }catch(SQLException ex){
            System.err.println(ex);
        }


        return false;
    }


    public boolean addTermin(Termin termin) {
        try{
            int allDay = termin.getAllDay() == true ? 1 : 0;
            int repeat = termin.getRepeat() == true ? 1 : 0;
            int cancel = termin.getCancel() == true ? 1 : 0;
            int reminder = termin.getReminder() == true ? 1 : 0;

			String query = "INSERT INTO termin VALUES ('" + termin.getId() + "', '"+ termin.getName() + "', '" +
                    termin.getStart() + "', '" + termin.getStartTime() + "', '" + termin.getEnd() + "', '" +
                    termin.getEndTime() + "', '" + allDay + "', '" + termin.getOrt() + "', '" +
                    repeat + "', '" + termin.getRepeatTime() +"', '" + cancel + "', '" +
                    termin.getAttachment() + "', '" + termin.getNote() + "', '" + termin.getPriority() + "', '" +
                    reminder + "',' 1','" + termin.getCancelMsg() + "', '" + termin.getReminderDate() + "', '" + termin.getReminderTime() + "')";
			Statement statement = this.connection.createStatement();
			statement.executeQuery(query);
		}
		catch(SQLException ex){
            System.err.println(ex);
		}
        return false;
    }


    public Termin getTermin(Termin termin)
    {
		try{
			String query = "SELECT * FROM termin WHERE TERMIN_ID = " + termin.getId();
			Statement statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery(query);

			Termin tempTermin = new Termin();

            rs.next();
			tempTermin.setId(rs.getInt("TERMIN_ID"));
			tempTermin.setName(rs.getString("Name"));
			tempTermin.setStart(String.valueOf(rs.getDate("Start")));
			tempTermin.setStartTime(rs.getTime("StartZeit").toString().substring(0,5));
			tempTermin.setEnd(String.valueOf(rs.getDate("Ende")));
			tempTermin.setEndTime(rs.getTime("EndeZeit").toString().substring(0,5));
			tempTermin.setAllDay(rs.getBoolean("allDay"));
			tempTermin.setOrt(rs.getString("Location"));
            tempTermin.setRepeat(rs.getBoolean("RepeatBool"));
            tempTermin.setRepeatTime(rs.getString("RepeatTime"));
            tempTermin.setCancel(rs.getBoolean("Cancel"));
            //tempTermin.setAttachment(rs.getBlob("Attachment"));
            tempTermin.setNote(rs.getString("Note"));
            tempTermin.setPriority(rs.getInt("Priority"));
            tempTermin.setReminder(rs.getBoolean("Reminder"));
            //tempTermin.setVcard(rs.getInt("VCard"));
            tempTermin.setCancelMsg(rs.getString("CancelMsg"));
            tempTermin.setReminderDate(String.valueOf(rs.getDate("ReminderDate")));
            tempTermin.setReminderTime(rs.getTime("ReminderTime").toString().substring(0,5));

			return tempTermin;
		}
		catch(SQLException ex){
            System.err.println(ex);
		}
        return null;

    }

    public Termin getTermin(int id)
    {
        try{
            String query = "SELECT * FROM termin WHERE TERMIN_ID = " + id;
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            Termin tempTermin = new Termin();

            rs.next();
            tempTermin.setId(rs.getInt("TERMIN_ID"));
            tempTermin.setName(rs.getString("Name"));
            tempTermin.setStart(String.valueOf(rs.getDate("Start")));
            tempTermin.setStartTime(rs.getTime("StartZeit").toString().substring(0,5));
            tempTermin.setEnd(String.valueOf(rs.getDate("Ende")));
            tempTermin.setEndTime(rs.getTime("EndeZeit").toString().substring(0,5));
            tempTermin.setAllDay(rs.getBoolean("allDay"));
            tempTermin.setOrt(rs.getString("Ort"));
            tempTermin.setRepeat(rs.getBoolean("RepeatBool"));
            tempTermin.setRepeatTime(rs.getString("RepeatTime"));
            tempTermin.setCancel(rs.getBoolean("Cancel"));
            //tempTermin.setAttachment(rs.getBlob("Attachment"));
            tempTermin.setNote(rs.getString("Note"));
            tempTermin.setPriority(rs.getInt("Priority"));
            tempTermin.setReminder(rs.getBoolean("Reminder"));
            //tempTermin.setVcard(rs.getInt("VCard"));
            tempTermin.setCancelMsg(rs.getString("CancelMsg"));
            tempTermin.setReminderDate(String.valueOf(rs.getDate("ReminderDate")));
            tempTermin.setReminderTime(rs.getTime("ReminderTime").toString().substring(0,5));

            return tempTermin;
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        return null;

    }

    public List<Termin> getAllTermine()
    {
        List<Termin> alleTermine = new ArrayList<Termin>();
        try{
            String query = "SELECT * FROM termin";
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);


            while( rs.next() == true) {
                Termin tempTermin = new Termin();
                tempTermin.setId(rs.getInt("TERMIN_ID"));
                tempTermin.setName(rs.getString("Name"));
                tempTermin.setStart(String.valueOf(rs.getDate("Start")));
                tempTermin.setStartTime(rs.getTime("StartZeit").toString());
                tempTermin.setEnd(String.valueOf(rs.getDate("Ende")));
                tempTermin.setEndTime(rs.getTime("EndeZeit").toString());
                tempTermin.setAllDay(rs.getBoolean("allDay"));
                tempTermin.setOrt(rs.getString("Ort"));
                tempTermin.setRepeat(rs.getBoolean("RepeatBool"));
                tempTermin.setRepeatTime(rs.getString("RepeatTime"));
                tempTermin.setCancel(rs.getBoolean("Cancel"));
                //tempTermin.setAttachment(rs.getBlob("Attachment"));
                tempTermin.setNote(rs.getString("Note"));
                tempTermin.setPriority(rs.getInt("Priority"));
                tempTermin.setReminder(rs.getBoolean("Reminder"));
                //tempTermin.setVcard(rs.getInt("VCard"));
                tempTermin.setCancelMsg(rs.getString("CancelMsg"));
                tempTermin.setReminderDate(String.valueOf(rs.getDate("ReminderDate")));
                tempTermin.setReminderTime(rs.getTime("ReminderTime").toString().substring(0,5));
                alleTermine.add(tempTermin);
            }
            return alleTermine;
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        return alleTermine;
    }

}
