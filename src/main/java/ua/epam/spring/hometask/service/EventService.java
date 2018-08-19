package ua.epam.spring.hometask.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.util.exception.NotFoundException;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * @author Yuriy_Tkach
 */
public interface EventService extends AbstractDomainObjectService<Event> {


    void setClock(Clock clock);

    /**
     * Finding event by name
     *
     * @param name
     *            Name of the event
     * @return found event or <code>null</code>
     */
    @Nullable
    Event getByName(@Nonnull String name) throws NotFoundException;

    @Override
    Event save(@Nonnull Event object) throws NotFoundException;

    @Override
    void remove(@Nonnull Event object) throws NotFoundException;

    @Override
    Event getById(@Nonnull Long id) throws NotFoundException;

    @Nonnull
    @Override
    Collection<Event> getAll();

    /*
     * Finding all events that air on specified date range
     * 
     * @param from Start date
     * 
     * @param to End date inclusive
     * 
     * @return Set of events
     */
     @Nonnull
     Set<Event> getForDateRange(@Nonnull LocalDate from,
                                @Nonnull LocalDate to);

    /*
     * Return events from 'now' till the the specified date time
     * 
     * @param to End date time inclusive
     * s
     * @return Set of events
     */
     @Nonnull
     Set<Event> getNextEvents(@Nonnull LocalDateTime to);

}
