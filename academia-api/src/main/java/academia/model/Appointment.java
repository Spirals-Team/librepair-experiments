package academia.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Activity activity;

    private Date date;

    private int vagas;

    public Appointment(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getVagas() {
        return vagas;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }
}
