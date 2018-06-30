package pl.sda.jira.calendar.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.jira.calendar.domain.model.Calendar;

@Repository
public interface CrudJpaCalendarRepository extends CrudRepository<Calendar, String>{

}
