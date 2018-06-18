package de.swt.inf.model;


import de.swt.inf.database.TerminDao;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class Termin {

    private int TERMIN_ID; //read only

    private String name;

    private Location location;

    private String start;

    private String startTime;

    private String end;

    private String endTime;

    private boolean repeat;

    private String repeatTime;

    private boolean cancel;

    private String CancelMsg;

    private String ort;

    private File attachment;

    private boolean allDay;

    private String note;

    private int priority;

    private boolean reminder;

    private String reminderDate;

    private String reminderTime;

    private VCard vcard;

    private int vcard_id;

    private int calendar_id;

    private int category_id;

    private List<Calendar> calendars = new LinkedList<Calendar>();

    private String categories;

    private TerminDao daoTermin;


    public Termin(String name, String start, String end, boolean allDay, String startTime, String endTime) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.allDay = allDay;
        this.startTime = startTime;
        this.endTime = endTime;
        //this.calendars.add(Main.defaultCalendar);
        //Main.defaultCalendar.addTermin(this);  // habt ihr das bewust rausgenommen?????

    }

    public Termin() {

    }

    //All SETTERS
    public void setId(int id) {
        this.TERMIN_ID = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public void setRepeatTime(String repeatTime) {
        this.repeatTime = repeatTime;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public void setCancelMsg(String cancelMsg) {
        CancelMsg = cancelMsg;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setVcard(VCard vcard) {
        this.vcard = vcard;
    }

    public void setCategories(String categories) { this.categories = categories; }

    //ALL GETTER
    public int getId() {
        return TERMIN_ID;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getStart() {
        return start;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEnd() {
        return end;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean getRepeat() {
        return repeat;
    }

    public String getRepeatTime() {
        return repeatTime;
    }

    public boolean getCancel() {
        return cancel;
    }

    public String getCancelMsg() {
        return CancelMsg;
    }

    public String getOrt() {
        return ort;
    }

    public File getAttachment() {
        return attachment;
    }

    public boolean getAllDay() {
        return allDay;
    }

    public String getNote() {
        return note;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getReminder() {
        return reminder;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public VCard getVcard() {
        return vcard;
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }

    public String getCategories() {
        return categories;
    }


    //ALL METHODS


    public void delete() {

    }


    /**
     * Fügt einen Termin zu allen Usern aus users hinzu
     *
     * @param users Liste an Usern
     * @return true
     */
    public boolean share(List<User> users) {
        for (User u : users) {
            u.getCalendar().addTermin(this);
        }
        return true;
    }


    /**
     * Prüft einen Termin auf seine Gültigkeit
     * 1. Von-Datum muss vor dem Bis-Datum liegen
     * 2. Falls Von- und Bis-Datum gleich sind muss Von-Zeit vor Bis-Zeit liegen
     *
     * @return true if valid - false if not valid
     */
    public static boolean isValid(String startString, String endString, String startTime, String endTime) {
        String[] s = startString.split("-");
        String[] e = endString.split("-");
        Integer[] i1 = new Integer[3];
        Integer[] i2 = new Integer[3];
        for (int i = 0; i < 3; i++) {
            i1[i] = Integer.parseInt(s[i]);
            i2[i] = Integer.parseInt(e[i]);
            //System.err.println(i1[i] + " " + i2[i]);
        }

        for (int i = 0; i < 3; i++) {
            if (i1[i] < i2[i]) {
                return true;
            }
        }
        if (startString.equalsIgnoreCase(endString)) {

            String[] sT = startTime.split(":");
            String[] eT = endTime.split(":");
            for (int i = 0; i < 2; i++) {
                i1[i] = Integer.parseInt(sT[i]);
                i2[i] = Integer.parseInt(eT[i]);
                //System.err.println(i1[i] + " " + i2[i]);
            }

            for (int i = 0; i < 2; i++) {
                if (i1[i] < i2[i]) {
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * Gibt alle Kategorien in Form einer Liste zurück!
     * @return
     */
    public List<String> getAllCategories() {
        List<String> all = new LinkedList<String>();
        String[] allCategories = this.categories.split(",");
        for(int i = 0; i < allCategories.length; i++){
            all.add(allCategories[i]);
        }
        return all;
    }

    /**
     * Fügt einen Calendar der Liste calendars hinzu
     *
     * @param calendar Calendar der hinzugefügt werden soll
     */
    public void addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
    }
}
