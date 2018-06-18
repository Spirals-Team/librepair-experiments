package academia.controller;

import academia.business.dashboard.DashboardService;
import academia.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/private/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public Map<String, Object> dashboard() {
        LoggedUser principal = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return dashboardService.dashboardForAccount(principal.getAccountId());
    }
}
