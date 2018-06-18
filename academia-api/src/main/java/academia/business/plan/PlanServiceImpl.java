package academia.business.plan;

import academia.business.account.AccountRepository;
import academia.business.appointment.AppointmentService;
import academia.domain.AccountType;
import academia.model.Account;
import academia.model.Appointment;
import academia.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PlanRepository repository;

    @Autowired
    private AppointmentService appointmentService;

    private Plan save(Plan plan) {
        this.validate(plan);
        for (Appointment appointment : plan.getAppointments()) {
            appointmentService.save(appointment);
        }

        return repository.save(plan);
    }

    private void validate(Plan plan) {
        if (plan.getAppointments() == null || plan.getAppointments().size() == 0) {
            throw new ValidationException("Plano deve ter pelo menos uma aula");
        }
    }

    @Override
    public Plan create(Plan plan) {
        return this.save(plan);
    }

    @Override
    public Plan update(Plan plan) {
        return this.save(plan);
    }

    @Override
    public List<Plan> all(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        if (account.getType() == AccountType.CUSTOMER) {
            // para cliente, só mostra os próprios planos
            return repository.findByCustomer(account);
        }
        return repository.findAll();
    }
}
