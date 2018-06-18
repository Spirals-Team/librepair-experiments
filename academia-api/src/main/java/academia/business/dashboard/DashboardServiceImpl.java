package academia.business.dashboard;

import academia.business.account.AccountRepository;
import academia.business.plan.PlanService;
import academia.domain.AccountType;
import academia.model.Account;
import academia.model.Appointment;
import academia.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PlanService planService;

    @Override
    public Map<String, Object> dashboardForAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).get();

        if (account.getType() == AccountType.ADMIN) {
            return this.getAdminDashboard(account);
        }

        if (account.getType() == AccountType.CUSTOMER) {
            return this.getCustomerDashboard(account);
        }

        if (account.getType() == AccountType.TRAINER) {
            return this.getTrainerDashboard(account);
        }

        return new HashMap<>();
    }

    private Map<String,Object> getAdminDashboard(Account account) {
        return null;
    }

    private Map<String,Object> getCustomerDashboard(Account account) {
        Map<String, Object> map = new HashMap<>();

        List<Plan> plans = planService.all(account.getId());
        List<Appointment> appointments = plans.stream().map(p -> p.getAppointments()).flatMap(List::stream).collect(Collectors.toList());
        map.put("appointments", appointments);

        return map;
    }

    private Map<String, Object> getTrainerDashboard(Account account) {
        Map<String, Object> map = new HashMap<>();

        List<Plan> plans = planService.all(account.getId());
        List<TrainerAppointmentDto> trainerAppointments = plans.stream().map(p -> p.getAppointments().stream().filter(a -> a.getTrainer().getId().equals(account.getId())).map(a -> new TrainerAppointmentDto(a, p.getCustomer())).collect(Collectors.toList())).flatMap(List::stream).collect(Collectors.toList());
        map.put("trainerAppointments", trainerAppointments);

        return map;
    }
}
