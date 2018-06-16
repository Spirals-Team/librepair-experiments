package academia.business.appointment;

import academia.model.Appointment;


public interface AppointmentService {

    Appointment create(Appointment appointment);

    Appointment update(Appointment appointment);


}
