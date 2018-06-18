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
                    "', StartZeit = '" + termin.getStartTime() + ":00', Ende = '" + termin.getEnd() +"', EndeZeit ='"+
                    termin.getEndTime() + ":00', allDay = '" + allDay + "', Ort = '" + termin.getOrt() +
                    "', RepeatBool = '" + repeat + "',RepeatTime = '" + termin.getRepeatTime() + "', Cancel = '" + cancel + "', Attachement = '" +
                    termin.getAttachment() + "', Note = '" + termin.getNote() + "', Priority = '" + termin.getPriority() +
                    "', Reminder = '" + reminder + "', VCard = '1', CancelMsg = '" +
                    termin.getCancelMsg() + "', ReminderDate = '" + termin.getReminderDate() + "', ReminderTime = '" + termin.getReminderTime() + "', Categories = '"+ termin.getCategories() +"' WHERE TERMIN_ID = '" +
                    termin.getId() + "'";
            query = query.replaceAll("'null'", "NULL");
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
            statement.execute(query);
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
            statement.execute(query);
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


			String query = "INSERT INTO termin VALUES ('null', '"+ termin.getName() + "', '" +
                    termin.getStart() + "', '" + termin.getStartTime() + ":00', '" + termin.getEnd() + "', '" +
                    termin.getEndTime() + ":00', '" + allDay + "', '" + termin.getOrt() + "', '" +
                    repeat + "', '" + termin.getRepeatTime() +"', '" + cancel + "', '" +
                    termin.getAttachment() + "', '" + termin.getNote() + "', '" + termin.getPriority() + "', '" +
                    reminder + "',' 1','" + termin.getCancelMsg() + "', '" + termin.getReminderDate() + "', '" + termin.getReminderTime() + "', '" + termin.getCategories() + "')";
			query = query.replaceAll("'null'", "NULL");
			Statement statement = this.connection.createStatement();
			statement.execute(query);
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
            tempTermin.setReminderTime(rs.getString("ReminderTime"));
            tempTermin.setCategories(rs.getString("Categories"));

            /*String rtime = "";
            try {
                rtime = rs.getTime("ReminderTime").toString().substring(0, 5);
            }
            catch (NullPointerException ex) {
                rtime = "NULL";
            }
            tempTermin.setReminderTime(rtime);*/

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
            tempTermin.setReminderTime(rs.getString("ReminderTime"));
            tempTermin.setCategories(rs.getString("Categories"));


            /*String rtime = "";
            try {
                rtime = rs.getTime("ReminderTime").toString().substring(0, 5);
            }
            catch (NullPointerException ex) {
                rtime = "NULL";
            }
            tempTermin.setReminderTime(rtime);*/

            return tempTermin;
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        return null;

    }


    public List<Termin> getDateTermine(String start) {
        List<Termin> dateTermine = new ArrayList<Termin>();
        try{
            String query = "SELECT * FROM termin WHERE start='" + start+"'";
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return resultSetToList(rs);
        }
        catch (SQLException ex){
            System.err.println(ex);
        }
        return dateTermine;
    }

    public List<Termin> getAllTermine()
    {
        List<Termin> alleTermine = new ArrayList<Termin>();
        try{
            String query = "SELECT * FROM termin";
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            alleTermine = resultSetToList(rs);

            return alleTermine;
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        return alleTermine;
    }


    private List<Termin> resultSetToList(ResultSet rs){
        List<Termin> listTermin = new ArrayList<Termin>();

        try{
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
                tempTermin.setReminderTime(rs.getString("ReminderTime"));
                tempTermin.setCategories(rs.getString("Categories"));

                /*String rtime = "";
                try {
                    rtime = rs.getTime("ReminderTime").toString().substring(0, 5);
                }
                catch (NullPointerException ex) {
                    rtime = "NULL";
                }

                tempTermin.setReminderTime(rtime);*/

                listTermin.add(tempTermin);
            }
        }
        catch (SQLException ex){
            System.err.println(ex);
        }


        return listTermin;
    }

}
