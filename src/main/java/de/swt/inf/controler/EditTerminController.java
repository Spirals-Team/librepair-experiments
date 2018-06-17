package de.swt.inf.controler;


import de.swt.inf.database.CategoryDao;
import de.swt.inf.database.DaoFactory;
import de.swt.inf.database.TerminDao;
import de.swt.inf.database.VCardDao;
import de.swt.inf.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EditTerminController {

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editTermin(Model model, HttpServletRequest request) {
        CategoryDao categoryDao = DaoFactory.getCategoryDao();
        VCardDao vCardDao = DaoFactory.getVCardDao();
        Map<Category, Boolean> categories = new HashMap<Category, Boolean>();
        List<VCard> vCards = vCardDao.getAllVCards();
        List<String> repeats = new ArrayList<String>();
        repeats.add("stündlich");
        repeats.add("täglich");
        repeats.add("wöchentlich");
        repeats.add("monatlich");
        repeats.add("jährlich");

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


        //Kategorien zur Map hinzufügen
        for(Category c : categoryDao.getAllCategories()){
            categories.put(c, false);
        }



        //Termin einlesen
        int id = Integer.parseInt(request.getParameter("id"));
        TerminDao terminDao = DaoFactory.getTerminDao();
        Termin termin = terminDao.getTermin(id);

        String name = termin.getName();
        String start = termin.getStart();
        String startT = termin.getStartTime();
        String end = termin.getEnd();
        String endT = termin.getEndTime();
        String place = termin.getOrt();
        boolean allDay = termin.getAllDay();
        boolean terminRepeat = termin.getRepeat();
        String repeatTime = termin.getRepeatTime();
        boolean reminder = termin.getReminder();
        String cat = termin.getCategories();
        String reminderD = " ";
        String reminderT = " ";

        if (reminder) {
            reminderD = termin.getReminderDate();
            reminderT = termin.getReminderTime();
        }
        //System.err.println(termin.getReminderTime());
        String notice = termin.getNote();
        int prio = termin.getPriority();



        //Vorausgewählte Kategorien auf "true" setzen
        if(cat != null){
            categories = preselectCategories(categories, cat);
        }



        //for delete
        model.addAttribute("id", id);
        model.addAttribute("colors", false);
        model.addAttribute("name", name);
        model.addAttribute("start", start);
        model.addAttribute("startT", startT);
        model.addAttribute("end", end);
        model.addAttribute("endT", endT);
        model.addAttribute("ort", place);
        model.addAttribute("allDay", allDay ? "checked" : null);
        model.addAttribute("repeat", terminRepeat ? "checked" : null);
        model.addAttribute("reminder", reminder ? "checked" : null);
        if (reminder) {
            model.addAttribute("reminderD", reminderD);
            model.addAttribute("reminderT", reminderT);
        }
        //model.addAttribute("share", share);
        //model.addAttribute("anhang", )
        model.addAttribute("notice", notice);
        model.addAttribute("priority", prio);
        model.addAttribute("repeatTime", repeatTime);

        model.addAttribute("prios", prios);
        model.addAttribute("repeats", repeats);
        model.addAttribute("categories", categories);
        model.addAttribute("vcards", vCards);







        return "terminEdit";

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    private String saveTermin(Model model, HttpServletRequest request) {
        TerminDao terminDao = DaoFactory.getTerminDao();

        //get Values from the Parameter
        String name = request.getParameter("name");
        String start = request.getParameter("start");
        String startT = request.getParameter("startT");
        String end = request.getParameter("end");
        model.addAttribute("color", false);
        Boolean terminRepeat = request.getParameter("TerminRepeat") != null ? true : false;
        String repeatTime = request.getParameter("repeatTime");
        String endT = request.getParameter("endT");
        String place = request.getParameter("ort");
        Boolean allDay = request.getParameter("allDay") != null ? true : false;
        //fakultativ
        int priority = Integer.parseInt(request.getParameter("priority"));
        Boolean reminder = request.getParameter("reminder") != null ? true : false;
        String reminderD = " ";
        String reminderT = " ";
        if (reminder) {
            reminderD = request.getParameter("reminderD");
            reminderT = request.getParameter("reminderT");
        }
        String intervall = request.getParameter("repeat");
        String share = request.getParameter("share");
        //File file = (File) request.getParameter("file");
        String notice = request.getParameter("notice");
        Termin t;

        t = new Termin(name, start, end, allDay, startT, endT);
        t.setPriority(priority);
        t.setRepeatTime(repeatTime);

        if (reminder) {
            t.setReminder(true);
            t.setReminderDate(reminderD);
            t.setReminderTime(reminderT);
        }



        if (false) {
            User u = User.getUserByEmail(share);
            List<User> l = new ArrayList<User>();
            l.add(u);
            t.share(l);
        }

        if (notice != null) {
            t.setNote(notice);
        }

        if (place != null) {
            t.setOrt(place);
        }



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
            List<Category> categories = categoryDao.getAllCategories();
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


            //model.addAttribute("share", share);
            //model.addAttribute("anhang", )
            model.addAttribute("notice", notice);
            return "terminEdit";

        }



        String id = request.getParameter("id");
        t.setId(Integer.parseInt(id));
        terminDao.updateTermin(t);
        return "redictDashboard";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteTermin(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        TerminDao termin = DaoFactory.getTerminDao();
        termin.deleteTermin(id);
        return "redictDashboard";
    }

    /**
     * Setzt bei allen Kategorien das Value auf "true", wenn diese im Termin vorhanden sind.
     * Somit werden die im Termin vorhanden Kategorien vorausgewählt.
     * @param categories die Liste an Kategorien
     * @param selectedCategories alle im Termin vorhanden Kategorien.
     * @return
     */
    private Map<Category, Boolean> preselectCategories(Map<Category, Boolean> categories, String selectedCategories){
        String[] selected = selectedCategories.split(",");

        if(selected.length > 0){
            for(int i = 0; i < selected.length; i++){
                for(Map.Entry e : categories.entrySet()){
                    if(selected[i].equals(e.getKey().toString())){
                        e.setValue(true);
                    }
                }
            }
        }



        return categories;
    }

}
