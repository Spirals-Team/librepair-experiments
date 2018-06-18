package academia.model;

import academia.domain.AccountType;

import javax.persistence.*;
import javax.validation.ValidationException;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    public Appointment() {
        // hibernate needs the default constructor
    }

    public Appointment(Activity activity, Account trainer, LocalDateTime start) {
        this.validate(trainer);

        this.activity = activity;
        this.trainer = trainer;
        this.start = start;
    }

    private void validate(Account trainer) {
        if (trainer.getType() != AccountType.TRAINER) {
            throw new ValidationException("Appointment deve ter um treinador com o tipo de conta de treinador");
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Activity activity;

    @ManyToOne
    private Account trainer;

    private LocalDateTime start;

    public Account getTrainer() {
        return trainer;
    }

    public void setTrainer(Account trainer) {
        this.trainer = trainer;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
