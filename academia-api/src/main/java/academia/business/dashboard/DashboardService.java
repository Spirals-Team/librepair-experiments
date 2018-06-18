package academia.business.dashboard;

import java.util.Map;

public interface DashboardService {
    Map<String,Object> dashboardForAccount(Long accountId);
}
