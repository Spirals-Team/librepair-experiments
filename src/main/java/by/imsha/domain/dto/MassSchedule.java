package by.imsha.domain.dto;

import by.imsha.api.rest.serializers.LocalDateSerializer;
import by.imsha.domain.Mass;
import by.imsha.domain.dto.mapper.MassInfoMapper;
import by.imsha.utils.ServiceUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.*;
import java.util.*;

/**
 * @author Alena Misan
 */
public class MassSchedule implements Serializable {

    @JsonIgnore
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @JsonSerialize(using=LocalDateSerializer.class)
    private LocalDate startWeekDate;

    public MassSchedule(LocalDate startDate) {
        this.startWeekDate = startDate;
        weekMasses = new HashMap<WeekDayTimeKey, List<Mass>>();
        massesByDay = new HashMap<DayOfWeek, Map<LocalTime, List<MassInfo>>>();
        schedule = new ArrayList<MassDay>();
    }

    @JsonIgnore
    private Map<WeekDayTimeKey, List<Mass>> weekMasses;


    private List<MassDay> schedule;

    public List<MassDay> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<MassDay> schedule) {
        this.schedule = schedule;
    }

    private Map<DayOfWeek, Map<LocalTime, List<MassInfo>>> massesByDay;

    public Map<DayOfWeek, Map<LocalTime, List<MassInfo>>> getMassesByDay() {
        return massesByDay;
    }

    public LocalDate getStartWeekDate() {
        return startWeekDate;
    }

    public Map<WeekDayTimeKey, List<Mass>> getWeekMasses() {
        return weekMasses;
    }

    /**
     * Build schedule based on week-day, filters masses that is configured not in week from provided startWeekDay
     *
     * */
    public MassSchedule build(List<Mass> masses) {
        LocalDate startDate = getStartWeekDate();
        LocalDate endDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth()).plusDays(7);
        build(masses, startDate, endDate);
        return this;
    }

    private void build(List<Mass> masses, LocalDate startDate, LocalDate endDate) {
        for (Mass mass : masses) {
            long singleStartTimestamp = mass.getSingleStartTimestamp();
            // TODO get ZONE from parish : to support
            LocalDateTime singleStartTime = singleStartTimestamp > 0 ? ServiceUtils.timestampToLocalDate(singleStartTimestamp, ZoneId.of("Europe/Minsk")) : null;

            if (singleStartTime != null) {
                if (mass.getTime() != null) {
                    log.error(String.format("Mass with ID: %s is configured incorrectly - time and singleStartTime cannot be not null at the same time!", mass.getId()));
                    continue;
                }
                LocalDate singleStartDate = singleStartTime.toLocalDate();

                if (singleStartDate.isAfter(LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth()).minusDays(1)) && singleStartDate.isBefore(endDate)) {
                    populateContainers(mass, singleStartTime.getDayOfWeek(), singleStartTime.toLocalTime());
                }
            } else {
                if (mass.getTime() == null) {
                    log.error(String.format("Mass with ID: %s is configured incorrectly - time and singleStartTime cannot be null at the same time!", mass.getId()));
                    continue;
                }
                int[] days = mass.getDays();
                for (int day : days) {
                    populateContainers(mass, DayOfWeek.of(day), LocalTime.parse(mass.getTime()));
                }
            }
        }
    }

    private void populateContainers(Mass mass, DayOfWeek dayOfWeek, LocalTime time) {
        addToWeekMasses(mass, dayOfWeek, time);
        addToMassesByDay(mass, dayOfWeek, time);
    }

    private void addToWeekMasses(Mass mass, DayOfWeek dayOfWeek, LocalTime time) {
        WeekDayTimeKey weekTimeKey = new WeekDayTimeKey(dayOfWeek, time);
        weekMasses.computeIfAbsent(weekTimeKey, v -> new ArrayList<Mass>()).add(mass);
    }

    private void addToMassesByDay(Mass mass, DayOfWeek dayOfWeek, LocalTime time) {

        massesByDay.computeIfAbsent(dayOfWeek, v -> new HashMap<LocalTime, List<MassInfo>>()).
                computeIfAbsent(time, v -> new ArrayList<MassInfo>()).add(MassInfoMapper.MAPPER.toMassInfo(mass));
    }
    /**
     * Build schedule for week from provided startWeekDate;
     *
     * */
    public MassSchedule buildSchedule() {
//        LocalDate startDate = LocalDate.of(getStartWeekDate().getYear(), getStartWeekDate().getMonth(), getStartWeekDate().getDayOfMonth()).minusDays(getStartWeekDate().getDayOfWeek().getValue() - 1)
//                .minusDays(getStartWeekDate().getDayOfWeek().getValue() - 1);

        buildSchedule(getStartWeekDate());
        return this;
    }

    private void buildSchedule(LocalDate startDate) {
        int counter = 0;
        while (counter < 7) {
            MassDay massDay = null;
            Map<LocalTime, List<MassInfo>> massHours = getMassesByDay().get(startDate.getDayOfWeek());
            if (massHours != null && massHours.size() > 0) {
                massDay = new MassDay(startDate);
                massDay.setMassHours(new ArrayList<MassDay.MassHour>(massHours.size()));
                for (Map.Entry<LocalTime, List<MassInfo>> massHourEntry : massHours.entrySet()) {
                    LocalTime hour = massHourEntry.getKey();
                    List<MassInfo> data = massHourEntry.getValue();
                    MassDay.MassHour massHour = new MassDay.MassHour(hour, data);
                    massDay.getMassHours().add(massHour);
                }
                massDay.getMassHours().sort((h1, h2) -> h1.getHour().compareTo(h2.getHour()));
                schedule.add(massDay);
            }

            startDate = startDate.plusDays(1);
            counter++;
        }
    }


    class WeekDayTimeKey {
        private DayOfWeek weekDay;
        private LocalTime time;

        WeekDayTimeKey(DayOfWeek weekDay, LocalTime time) {
            this.weekDay = weekDay;
            this.time = time;
        }

        DayOfWeek getWeekDay() {
            return weekDay;
        }

        void setWeekDay(DayOfWeek weekDay) {
            this.weekDay = weekDay;
        }

        LocalTime getTime() {
            return time;
        }

        void setTime(LocalTime time) {
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WeekDayTimeKey)) return false;

            WeekDayTimeKey that = (WeekDayTimeKey) o;

            if (weekDay != that.weekDay) return false;
            if (!time.equals(that.time)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = weekDay.getValue();
            result = 31 * result + time.hashCode();
            return result;
        }

    }

}
