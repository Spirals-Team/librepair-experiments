package academia.controller;

import academia.business.account.AccountRepository;
import academia.business.account.AccountService;
import academia.business.appointment.AppointmentRepository;
import academia.business.restriction.RestrictionRepository;
import academia.model.Account;
import academia.model.Activity;
import academia.model.Appointment;
import academia.model.Restriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/private/activites/schedule")
public class ScheduleActivityController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @RequestMapping(method = RequestMethod.POST, path = "")
    public boolean create(@RequestBody ActivitySchedule schedule) throws ValidationException {

        validateRestrictions(schedule.getAccount(), schedule.getActivity());

        Appointment appointment = appointmentRepository.findByDateAndActivity(schedule.getActivity(), schedule.getDate());

        if (appointment == null) {
            throw new ValidationException("Não existe nada na agenda para a data requisitada");
        }

        if (appointment.getVagas() == 0) {
            throw new ValidationException("Não existem mais vagas disponíveis para a atividade");
        }

        appointment.setVagas(appointment.getVagas() - 1);

        appointmentRepository.save(appointment);

        return true;
    }

    private void validateRestrictions(Account account, Activity activity) throws ValidationException {
        List<Restriction> accountRestrictions = account.getRestrictions();

        List<Restriction> activityRestrictions = activity.getRestrictions();

        for (Restriction accountRestriction : accountRestrictions) {
            for (Restriction activityRestriction : activityRestrictions) {
                if (accountRestriction.getId().equals(activityRestriction.getId())) {
                    throw new ValidationException("Usuário possui restrição " + accountRestriction.getName());
                }
            }
        }
    }

    public class ActivitySchedule {
        private Activity activity;
        private Account account;
        private Date date;

        public Account getAccount() {
            return account;
        }

        public Activity getActivity() {
            return activity;
        }

        public Date getDate() {
            return date;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
