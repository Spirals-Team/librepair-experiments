package de.swt.inf.controler;

import de.swt.inf.database.DaoFactory;
import de.swt.inf.database.TerminDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@Controller
public class DashboardController {

    TerminDao terminDao = DaoFactory.getTerminDao();

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpServletRequest request, Model model) throws ParseException {
        String calendar = request.getParameter("kalender");
        TerminDao terminDao = DaoFactory.getTerminDao();
        if (calendar != null) {
            //use this Calendar // not implemented in Prototype
        } else {  //use default calendar
            model.addAttribute("termine", terminDao.getAllTermine());
            //model.addAttribute("t1", "Test Termin");
        }



        return "dashboard";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public String viewTermin(HttpServletRequest request, Model model) {

        return "termin/ViewTermin";
    }


}
