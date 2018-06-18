package academia.model;

import academia.domain.AccountType;

import javax.persistence.*;
import javax.validation.ValidationException;
import java.util.List;

@Entity
public class Plan {
    public Plan() {
        // hibernate needs the default constructor
    }

    public Plan(Account customer, List<Appointment> appointments) {
        this.customer = customer;
        this.appointments = appointments;
    }

    private void validate(Account trainer) {
        if (trainer.getType() != AccountType.CUSTOMER) {
            throw new ValidationException("Plan deve ter um customer com o tipo de conta de cliente");
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account customer;

    @OneToMany
    private List<Appointment> appointments;

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
