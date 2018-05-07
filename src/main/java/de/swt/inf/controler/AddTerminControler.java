package de.swt.inf.controler;

import de.swt.inf.database.*;
import de.swt.inf.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AddTerminControler {

    @RequestMapping(value = "/termin", method = RequestMethod.GET)
    public String termin(Model model) {
        CategoryDao categoryDao = DaoFactory.getCategoryDao();
        VCardDao vCardDao = DaoFactory.getVCardDao();
        List<Category> categories = categoryDao.getAllCategories();
        List<VCard> vCards = vCardDao.getAllVCards();
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
        String cat = request.getParameter("category");
        Boolean terminRepeat = request.getParameter("TerminRepeat") != null ? true : false;
        String endT = request.getParameter("endT");
        String place = request.getParameter("ort");
        Boolean allDay = request.getParameter("allDay") != null ? true : false;
        //fakultativ
        int priority = Integer.parseInt(request.getParameter("priority"));
        boolean reminder = request.getParameter("reminder") != null ? true : false;
        String prof = request.getParameter("prof");
        String reminderD = " ";
        String reminderT = " ";
        if (reminder) {
            reminderD = request.getParameter("reminderD");
            reminderT = request.getParameter("reminderT");
        }
        String repeatTime = request.getParameter("repeatTime");
        String share = request.getParameter("share");
        //File file = (File) request.getParameter("file"); TODO:
        String notice = request.getParameter("notice");
        Termin t;

        t = new Termin(name, start, end, allDay, startT, endT);

        t.addCategory(new Category());
        t.setPriority(priority);
        t.setRepeat(terminRepeat);
        t.setRepeatTime(repeatTime);


        if (reminder) {
            t.setReminder(true);
            t.setReminderDate(reminderD);
            t.setReminderTime(reminderT);
        }

        /*if (intervall != null) {
            if (intervall.equalsIgnoreCase("monatlich")) t.setRepeatTime(RepeatTimes.monthly);
            if (intervall.equalsIgnoreCase("jährlich")) t.setRepeatTime(RepeatTimes.yearly);
            if (intervall.equalsIgnoreCase("stündlich")) t.setRepeatTime(RepeatTimes.hourly);
            if (intervall.equalsIgnoreCase("täglich")) t.setRepeatTime(RepeatTimes.dayly);
            if (intervall.equalsIgnoreCase("wöchentlich")) t.setRepeatTime(RepeatTimes.weekly);
        }*/

        if (false) {
            User u = User.getUserByEmail(share);
            List<User> l = new ArrayList<User>();
            l.add(u);
            t.share(l);
        }
        if (place != null) {
            t.setOrt(place);
        }

        if (notice != null) {
            t.setNote(notice);
        }

        //if (file != null) {
        //}
            //if valid returns true means the date is correct
        if (!(validateDates(start, end, startT, endT))) {
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
        terminDao.addTermin(t);
        return "redictDashboard";
    }


    @RequestMapping(value = "/termin", method = RequestMethod.DELETE)
    public String delTermin(HttpServletRequest request, Model model) {
        return "/termin/ViewTermin";
    }


    static boolean validateDates(String start, String end, String startT, String endT) {
        String[] s = start.split("-");
        String[] e = end.split("-");
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
        } if (start.equalsIgnoreCase(end)) {

        String[] sT = startT.split(":");
        String[] eT = endT.split(":");
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
}
