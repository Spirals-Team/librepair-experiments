package de.swt.inf.controler;

import de.swt.inf.database.*;
import de.swt.inf.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

@Controller
public class AddTerminControler {

    private final int MAX_NEW_TERMINE = 5;

    @RequestMapping(value = "/termin", method = RequestMethod.GET)
    public String termin(Model model) {
        CategoryDao categoryDao = DaoFactory.getCategoryDao();
        VCardDao vCardDao = DaoFactory.getVCardDao();
        List<Category> categories = categoryDao.getAllCategories();
        List<VCard> vCards = vCardDao.getAllVCards();
        List<Integer> prios = new ArrayList<Integer>();

        //Prioritäten hinzufügen
        prios.add(0);
        prios.add(1);
        prios.add(2);
        prios.add(3);
        prios.add(4);
        prios.add(5);
        prios.add(6);
        prios.add(7);
        prios.add(8);
        prios.add(9);

        model.addAttribute("prios", prios);
        model.addAttribute("categories", categories);
        model.addAttribute("vcards", vCards);

        return "termin";
    }

    @RequestMapping(value = "/termin", method = RequestMethod.POST)
    public String addTermin(HttpServletRequest request, Model model) throws ParseException {
        TerminDao terminDao = DaoFactory.getTerminDao();


        //get Values from the Parameter
        String name = request.getParameter("name");
        String start = request.getParameter("start");
        String startT = request.getParameter("startT");
        String end = request.getParameter("end");
        String[] cat = request.getParameterValues("category");
        boolean terminRepeat = request.getParameter("repeat") != null ? true : false;
        String endT = request.getParameter("endT");
        String place = request.getParameter("ort");
        boolean allDay = request.getParameter("allDay") != null ? true : false;
        //fakultativ
        int priority = Integer.parseInt(request.getParameter("priority"));
        boolean reminder = request.getParameter("reminder") != null ? true : false;
        String prof = request.getParameter("prof");
        String reminderD = "";
        String reminderT = "";
        StringBuilder selectedCategories = new StringBuilder();
        String repeatTime = request.getParameter("repeatTime");
        String share = request.getParameter("share");
        //File file = (File) request.getParameter("file");
        String notice = request.getParameter("notice");
        Termin t;

        t = new Termin(name, start, end, allDay, startT, endT);



        if (!(Termin.isValid(start, end, startT, endT))) {
            //date is incorect
            System.err.println("validate fail");
            model.addAttribute("colors", true);
            model.addAttribute("name", name);
            model.addAttribute("start", start);
            model.addAttribute("startT", startT);
            model.addAttribute("end", end);
            model.addAttribute("endT", endT);
            model.addAttribute("ort", place);
            model.addAttribute("allDay", allDay ? "checked" : null);
            model.addAttribute("allDay", terminRepeat ? "checked" : null);
            model.addAttribute("reminder", reminder ? "checked" : null);
            if (reminder) {
                model.addAttribute("reminderD", reminderD);
                model.addAttribute("reminderT", reminderT);
            }
            List<Integer> prios = new ArrayList<Integer>();
            prios.add(0);
            prios.add(1);
            prios.add(2);
            prios.add(3);
            prios.add(4);
            prios.add(5);
            prios.add(6);
            prios.add(7);
            prios.add(8);
            prios.add(9);
            model.addAttribute("prios", prios);
            CategoryDao categoryDao = DaoFactory.getCategoryDao();
            VCardDao vCardDao = DaoFactory.getVCardDao();
            Map<Category, Boolean> categories = new HashMap<Category, Boolean>();

            //Kategorien zur Map hinzufügen
            for(Category c : categoryDao.getAllCategories()){
                categories.put(c, false);
            }
            List<VCard> vCards = vCardDao.getAllVCards();
            model.addAttribute("categories", categories);
            model.addAttribute("vcards", vCards);
            List<String> repeats = new ArrayList<String>();
            repeats.add("stündlich");
            repeats.add("täglich");
            repeats.add("wöchentlich");
            repeats.add("monatlich");
            repeats.add("jährlich");
            model.addAttribute("repeats", repeats);
            model.addAttribute("notice", notice);
            return "terminEdit";

        } else {

            t.setPriority(priority);
            t.setRepeat(terminRepeat);
            t.setRepeatTime(repeatTime);



            //Kategorien werden zu einem String zusammengefasst (z.B.: "Kategorie1,Kategorie2,Kategorie3")

            if(cat != null){
                for(int i = 0; i < cat.length; i++){
                    if(i == (cat.length - 1)){
                        selectedCategories.append(cat[i]);
                    } else {
                        selectedCategories.append(cat[i]).append(",");
                    }
                }
                t.setCategories(selectedCategories.toString());
            }else{
                t.setCategories("");
            }


            if (reminder) {
                reminderD = request.getParameter("reminderD");
                reminderT = request.getParameter("reminderT");
                t.setReminder(true);
                t.setReminderDate(reminderD);
                t.setReminderTime(reminderT);
            }


            if (place != null) {
                t.setOrt(place);
            }

            if (notice != null) {
                t.setNote(notice);
            }


            terminDao.addTermin(t);

            if(terminRepeat){
                Termin temp = t;
                int stundeAnf = Integer.parseInt(t.getStartTime().substring(0,2));
                int minuteAnf = Integer.parseInt(t.getStartTime().substring(3));
                int tagAnf = Integer.parseInt(t.getStart().substring(8));
                int monatAnf = Integer.parseInt(t.getStart().substring(5,7)) - 1; //Java.util.Calendar rechnet beim Monat von 0 bis 11
                int jahrAnf = Integer.parseInt(t.getStart().substring(0,4));
                int stundeEnde = Integer.parseInt(t.getEndTime().substring(0,2));
                int minuteEnde = Integer.parseInt(t.getEndTime().substring(3));
                int tagEnde = Integer.parseInt(t.getEnd().substring(8));
                int monatEnde = Integer.parseInt(t.getEnd().substring(5,7)) - 1; //Java.util.Calendar rechnet beim Monat von 0 bis 11
                int jahrEnde = Integer.parseInt(t.getEnd().substring(0,4));





                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd - kk:mm");
                Calendar calendarAnfang = new GregorianCalendar(jahrAnf, monatAnf, tagAnf, stundeAnf, minuteAnf);
                Calendar calendarEnde = new GregorianCalendar(jahrEnde, monatEnde, tagEnde, stundeEnde, minuteEnde);






                if(repeatTime.equals("stündlich")){
                    for(int i = 0; i < MAX_NEW_TERMINE; i++){

                        calendarAnfang.add(Calendar.HOUR_OF_DAY, 1);
                        calendarEnde.add(Calendar.HOUR_OF_DAY, 1);
                        temp.setStart(df.format(calendarAnfang.getTime()).substring(0,10));
                        temp.setStartTime(df.format(calendarAnfang.getTime()).substring(13));
                        temp.setEnd(df.format(calendarEnde.getTime()).substring(0,10));
                        temp.setEndTime(df.format(calendarEnde.getTime()).substring(13));
                        if(temp.getStartTime().substring(0,2).equals("24")){
                            temp.setStartTime("00:00");
                        }
                        if(temp.getEndTime().substring(0,2).equals("24")){
                            temp.setEndTime("00:00");
                        }
                        terminDao.addTermin(temp);
                    }

                } else if(repeatTime.equals("täglich")){
                    for(int i = 0; i < MAX_NEW_TERMINE; i++){
                        calendarAnfang.add(Calendar.DAY_OF_MONTH, 1);
                        calendarAnfang.get(Calendar.WEEK_OF_MONTH);
                        calendarEnde.add(Calendar.DAY_OF_MONTH, 1);
                        temp.setStart(df.format(calendarAnfang.getTime()).substring(0,10));
                        temp.setStartTime(df.format(calendarAnfang.getTime()).substring(13));
                        temp.setEnd(df.format(calendarEnde.getTime()).substring(0,10));
                        temp.setEndTime(df.format(calendarEnde.getTime()).substring(13));
                        terminDao.addTermin(temp);
                    }

                } else if(repeatTime.equals("wöchentlich")){
                    for(int i = 0; i < MAX_NEW_TERMINE; i++){
                        calendarAnfang.add(Calendar.DAY_OF_MONTH, 7);
                        calendarEnde.add(Calendar.DAY_OF_MONTH, 7);
                        temp.setStart(df.format(calendarAnfang.getTime()).substring(0,10));
                        temp.setStartTime(df.format(calendarAnfang.getTime()).substring(13));
                        temp.setEnd(df.format(calendarEnde.getTime()).substring(0,10));
                        temp.setEndTime(df.format(calendarEnde.getTime()).substring(13));
                        terminDao.addTermin(temp);
                    }

                } else if(repeatTime.equals("jährlich")){
                    for(int i = 0; i < MAX_NEW_TERMINE; i++){
                        calendarAnfang.add(Calendar.YEAR, 1);
                        calendarEnde.add(Calendar.YEAR, 1);
                        temp.setStart(df.format(calendarAnfang.getTime()).substring(0,10));
                        temp.setStartTime(df.format(calendarAnfang.getTime()).substring(13));
                        temp.setEnd(df.format(calendarEnde.getTime()).substring(0,10));
                        temp.setEndTime(df.format(calendarEnde.getTime()).substring(13));
                        terminDao.addTermin(temp);
                    }

                } else if(repeatTime.equals("monatlich")){
                    for(int i = 0; i < MAX_NEW_TERMINE; i++){
                        calendarAnfang.add(Calendar.MONTH, 1);
                        calendarEnde.add(Calendar.MONTH, 1);
                        temp.setStart(df.format(calendarAnfang.getTime()).substring(0,10));
                        temp.setStartTime(df.format(calendarAnfang.getTime()).substring(13));
                        temp.setEnd(df.format(calendarEnde.getTime()).substring(0,10));
                        temp.setEndTime(df.format(calendarEnde.getTime()).substring(13));
                        terminDao.addTermin(temp);
                    }

                }
            }

            return "redictDashboard";
        }

    }


    @RequestMapping(value = "/termin", method = RequestMethod.DELETE)
    public String delTermin(HttpServletRequest request, Model model) {
        return "/termin/ViewTermin";
    }



}
