package academia.business.dashboard;

import academia.model.Account;
import academia.model.Appointment;

public class TrainerAppointmentDto {
    public TrainerAppointmentDto() {}

    public TrainerAppointmentDto(Appointment appointment, Account customer) {
        this.appointment = appointment;
        this.customer = customer;
    }

    private Appointment appointment;

    private Account customer;

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }
}
