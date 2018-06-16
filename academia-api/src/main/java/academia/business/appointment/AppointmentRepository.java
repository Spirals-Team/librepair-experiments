package academia.business.appointment;

import academia.model.Activity;
import academia.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByDateAndActivity(Activity activity, Date date);
}
