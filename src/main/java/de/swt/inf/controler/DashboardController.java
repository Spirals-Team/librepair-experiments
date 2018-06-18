package de.swt.inf.controler;

import de.swt.inf.database.DaoFactory;
import de.swt.inf.database.TerminDao;
import de.swt.inf.model.Termin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.rmi.runtime.Log;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class DashboardController {

    TerminDao terminDao = DaoFactory.getTerminDao();
    boolean test = LoginController.test;
    boolean success = false;

    private DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    private static Calendar calendarTag;
    private static Calendar calendarWoche;
    private static Calendar calendarMonat;
    private static Calendar calendarSemster;
    private static Calendar calendarJahr;


    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
        return "dashboard";
    }

    @RequestMapping(value = "/dashboard/monat/dec", method = RequestMethod.GET)
    public String decMonat(HttpServletRequest request,HttpServletResponse response, Model model){
        if (LoginController.isUserLoggedIn(request.getCookies())) {
            String calendar = request.getParameter("kalender");
            TerminDao terminDao = DaoFactory.getTerminDao();
            if (calendar != null) {
                //use this Calendar // not implemented in Prototype
            } else {  //use default calendar

                calendarMonat.set(Calendar.MONTH, calendarMonat.get(Calendar.MONTH) - 1);

                DateFormat dateFormat = new SimpleDateFormat("MMMMMMMM");
                model.addAttribute("Monat",dateFormat.format(calendarMonat.getTime()));

                int maxDay = calendarMonat.getActualMaximum(Calendar.DAY_OF_MONTH);

                if(maxDay == 31) return "monat31";
                if(maxDay == 30) return "monat30";
                if(maxDay == 29) return "monat29";
                if(maxDay == 28) return "monat29";

            }
            return "monat31";
        }
        return "monat";
    }

    @RequestMapping(value = "/dashboard/monat/add", method = RequestMethod.GET)
    public String addMonat(HttpServletRequest request,HttpServletResponse response, Model model){
        if (LoginController.isUserLoggedIn(request.getCookies())) {
            String calendar = request.getParameter("kalender");
            TerminDao terminDao = DaoFactory.getTerminDao();
            if (calendar != null) {
                //use this Calendar // not implemented in Prototype
            } else {  //use default calendar

                calendarMonat.set(Calendar.MONTH, calendarMonat.get(Calendar.MONTH) + 1);

                DateFormat dateFormat = new SimpleDateFormat("MMMMMMMM");
                model.addAttribute("Monat",dateFormat.format(calendarMonat.getTime()));

                int maxDay = calendarMonat.getActualMaximum(Calendar.DAY_OF_MONTH);

                if(maxDay == 31) return "monat31";
                if(maxDay == 30) return "monat30";
                if(maxDay == 29) return "monat29";
                if(maxDay == 28) return "monat28";


            }

            return "monat";
        }
        return "monat";
    }

    @RequestMapping(value = "/dashboard/monat", method = RequestMethod.GET)
    public String viewMonat(HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
        if (LoginController.isUserLoggedIn(request.getCookies())) {
            String calendar = request.getParameter("kalender");
            TerminDao terminDao = DaoFactory.getTerminDao();
            if (calendar != null) {
                //use this Calendar // not implemented in Prototype
            } else {  //use default calendar

                calendarTag = getCurrentCalendar();
                calendarMonat = getCurrentCalendar();

                String monat = request.getParameter("monat");

                if(monat == null){
                    DateFormat dateFormat = new SimpleDateFormat("MMMMMMMM");

                    model.addAttribute("Monat",dateFormat.format(calendarMonat.getTime()));

                    int maxDay = calendarMonat.getActualMaximum(Calendar.DAY_OF_MONTH);

                    if(maxDay == 31) return "monat31";
                    if(maxDay == 30) return "monat30";
                    if(maxDay == 29) return "monat29";
                    if(maxDay == 28) return "monat28";
                } else {
                    calendarMonat.set(Calendar.MONTH, Integer.parseInt(monat) - 1);

                    DateFormat dateFormat = new SimpleDateFormat("MMMMMMMM");

                    model.addAttribute("Monat",dateFormat.format(calendarMonat.getTime()));

                    int maxDay = calendarMonat.getActualMaximum(Calendar.DAY_OF_MONTH);

                    if(maxDay == 31) return "monat31";
                    if(maxDay == 30) return "monat30";
                    if(maxDay == 29) return "monat29";
                    if(maxDay == 28) return "monat28";

                }
            }

            return "monat31";

        } else {

            try {
                response.sendRedirect("/login");
                return "login";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "monat31";
    }

    @RequestMapping(value = "/dashboard/jahr", method = RequestMethod.GET)
    public String viewJahr (HttpServletRequest request, HttpServletResponse response, Model model){
        return "jahr";
    }

    @RequestMapping(value = "/dashboard/tag/dec", method = RequestMethod.GET)
    public String decTag(HttpServletRequest request, HttpServletResponse response, Model model){
        if(LoginController.isUserLoggedIn(request.getCookies())){
            String calendar = request.getParameter("kalender");
            TerminDao terminDao = DaoFactory.getTerminDao();

            if (calendar != null) {
                //use this Calendar // not implemented in Prototype
            } else {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                List<Termin> terminList;
                String date = request.getParameter("datum");

                if(date == null){
                    calendarTag = getCurrentCalendar();
                    model.addAttribute("datum", dateFormat.format(calendarTag.getTime()));
                    terminList = terminDao.getDateTermine(dateFormat.format(calendarTag.getTime()));
                } else {
                    calendarTag.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date));
                    model.addAttribute("datum", dateFormat.format(calendarTag.getTime()));
                    terminList = terminDao.getDateTermine(dateFormat.format(calendarTag.getTime()));
                }

                model.addAttribute("Tag", terminList);

                return "tag";
            }
            return "tag";
        }
        return "tag";
    }


    @RequestMapping(value = "/dashboard/tag/add", method = RequestMethod.GET)
    public String addTag(HttpServletRequest request, HttpServletResponse response, Model model){
        if(LoginController.isUserLoggedIn(request.getCookies())){
            String calendar = request.getParameter("kalender");
            TerminDao terminDao = DaoFactory.getTerminDao();

            if (calendar != null) {
                //use this Calendar // not implemented in Prototype
            } else {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                calendarTag.add(Calendar.DAY_OF_MONTH,-1);

                model.addAttribute("datum", dateFormat.format(calendarTag.getTime()));
                List<Termin> terminList = terminDao.getDateTermine(dateFormat.format(calendarTag.getTime()));

                model.addAttribute("Tag", terminList);

                return "tag";
            }
            return "tag";
        }
        return "tag";
    }

    @RequestMapping(value = "/dashboard/tag", method = RequestMethod.GET)
    public String viewTag (HttpServletRequest request, HttpServletResponse response, Model model){

        if(LoginController.isUserLoggedIn(request.getCookies())){
            String calendar = request.getParameter("kalender");
            TerminDao terminDao = DaoFactory.getTerminDao();

            if (calendar != null) {
                //use this Calendar // not implemented in Prototype
            } else {
                String day = request.getParameter("datum");

                if(day == null){
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    calendarTag.add(Calendar.DAY_OF_MONTH,1);

                    model.addAttribute("datum", dateFormat.format(calendarTag.getTime()));
                    List<Termin> terminList = terminDao.getDateTermine(dateFormat.format(calendarTag.getTime()));

                    model.addAttribute("Tag", terminList);

                    return "tag";
                } else {

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    calendarTag.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));

                    model.addAttribute("datum", dateFormat.format(calendarTag.getTime()));
                    List<Termin> terminList = terminDao.getDateTermine(dateFormat.format(calendarTag.getTime()));

                    model.addAttribute("Tag", terminList);

                    return "tag";
                }
            }
            return "tag";
        }
        return "tag";
    }

    @RequestMapping(value = "dashboard/woche/add", method = RequestMethod.GET)
    public String addWoche (HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {

        if (LoginController.isUserLoggedIn(request.getCookies())) {
            String calendar = request.getParameter("kalender");
            TerminDao terminDao = DaoFactory.getTerminDao();

            if (calendar != null) {
                //use this Calendar // not implemented in Prototype
            } else {  //use default calendar

                calendarWoche.add(Calendar.DAY_OF_MONTH, 7);

                List<List<Termin>> termine = sortByCurrentWeekDay(calendarWoche, terminDao.getAllTermine(), "week");

                model.addAttribute("Montag", termine.get(1));

                model.addAttribute("Dienstag", termine.get(2));

                model.addAttribute("Mittwoch", termine.get(3));

                model.addAttribute("Donnerstag", termine.get(4));

                model.addAttribute("Freitag", termine.get(5));

                model.addAttribute("Samstag", termine.get(6));

                model.addAttribute("Sonntag", termine.get(7));

            }
            return "woche";
        } else {
            try {
                response.sendRedirect("/login");
                return "login";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "woche";
    }

    @RequestMapping(value = "dashboard/woche/dec", method = RequestMethod.GET)
    public String decWoche (HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
        if (LoginController.isUserLoggedIn(request.getCookies())){
            String calendar = request.getParameter("kalender");
            TerminDao terminDao = DaoFactory.getTerminDao();

            if (calendar != null) {
                //use this Calendar // not implemented in Prototype
            } else {  //use default calendar

                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                calendarWoche.add(Calendar.DAY_OF_MONTH, -7);
                System.out.println(df.format(calendarWoche.getTime()));

                List<List<Termin>> termine = sortByCurrentWeekDay(calendarWoche, terminDao.getAllTermine(), "week");

                model.addAttribute("Montag", termine.get(1));

                model.addAttribute("Dienstag", termine.get(2));

                model.addAttribute("Mittwoch", termine.get(3));

                model.addAttribute("Donnerstag", termine.get(4));

                model.addAttribute("Freitag", termine.get(5));

                model.addAttribute("Samstag", termine.get(6));

                model.addAttribute("Sonntag", termine.get(7));

            }
            return "woche";
        }
        return "woche";
    }

    @RequestMapping(value = "/dashboard/woche", method = RequestMethod.GET)
    public String viewWoche (HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {

        if (LoginController.isUserLoggedIn(request.getCookies())) {
            String calendar = request.getParameter("kalender");
            TerminDao terminDao = DaoFactory.getTerminDao();

            if (calendar != null) {
                //use this Calendar // not implemented in Prototype
            } else {  //use default calendar
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                calendarWoche = getCurrentCalendar();

                List<List<Termin>> termine = sortByCurrentWeekDay(calendarWoche, terminDao.getAllTermine(), "week");

                model.addAttribute("Montag", termine.get(1));

                model.addAttribute("Dienstag", termine.get(2));

                model.addAttribute("Mittwoch", termine.get(3));

                model.addAttribute("Donnerstag", termine.get(4));

                model.addAttribute("Freitag", termine.get(5));

                model.addAttribute("Samstag", termine.get(6));

                model.addAttribute("Sonntag", termine.get(7));

            }
            return "woche";
        } else {
            try {
                response.sendRedirect("/login");
                return "login";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "woche";
    }

    private List<List<Termin>> sortByCurrentWeekDay (Calendar c, List<Termin> listTermin, String time) throws ParseException {
        List<List<Termin>> termine = new LinkedList<List<Termin>>();

        for(int i = 0;i <= 7;i++){
            termine.add(new LinkedList<Termin>());
        }

        for(Termin t: listTermin){

            if(checkInTime(c, t.getStart())){

                String[] dateFormat = t.getStart().split("-");
                String date = dateFormat[2] + "/" + dateFormat[1] + "/" + dateFormat[0];

                Date dt1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                String finalDay = new SimpleDateFormat("EEEE").format(dt1);

                if (finalDay.equals("Montag")) termine.get(1).add(t);
                if (finalDay.equals("Dienstag")) termine.get(2).add(t);
                if (finalDay.equals("Mittwoch")) termine.get(3).add(t);
                if (finalDay.equals("Donnerstag")) termine.get(4).add(t);
                if (finalDay.equals("Freitag")) termine.get(5).add(t);
                if (finalDay.equals("Samstag")) termine.get(6).add(t);
                if (finalDay.equals("Sonntag")) termine.get(7).add(t);
            }
        }

        return termine;
    }


    private boolean checkInTime (Calendar c, String inputDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = getCurrentCalendar();
        calendar.setTime(c.getTime());

        inputDate = inputDate.replaceAll("-","/");
        Date dateToCheck = df.parse(inputDate);

        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

        Date startDay = calendar.getTime();

        calendar.add(Calendar.DATE,6);

        Date endDay = calendar.getTime();

        return (dateToCheck.after(startDay) || df.format(dateToCheck).equals(df.format(startDay)))&&
                (dateToCheck.before(endDay) || df.format(dateToCheck).equals(df.format(endDay)));
    }

    private GregorianCalendar getCurrentCalendar(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-kk-mm");
        Date date = new Date();

        String[] currentDate = dateFormat.format(date).split("-");

        return new GregorianCalendar(Integer.parseInt(currentDate[0]),Integer.parseInt(currentDate[1]) - 1,
                Integer.parseInt(currentDate[2]),Integer.parseInt(currentDate[3]),
                Integer.parseInt(currentDate[4]));
    }
}