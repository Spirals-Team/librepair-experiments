package academia.business.appointment;

import academia.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Override
    public Appointment save(Appointment appointment) {
        this.validate(appointment);
        return repository.save(appointment);
    }

    private void validate(Appointment appointment) {
        if (appointment.getTrainer() == null) {
            throw new ValidationException("Aula deve ter treinador");
        }

        if (appointment.getStart() == null) {
            throw new ValidationException("Aula deve ter data de início");
        }

        if (appointment.getActivity() == null) {
            throw new ValidationException("Aula deve ter atividade");
        }
    }
}
