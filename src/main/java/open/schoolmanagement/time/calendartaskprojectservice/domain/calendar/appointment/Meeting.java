package open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.appointment;

import static javax.persistence.CascadeType.ALL;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;

/**
 * The type Meeting.
 */
@Table(name = "meeting")

@Entity
public class Meeting {

  @Id
  @GeneratedValue
  @Column(name = "meeting_id", nullable = false)
  private Long meetingId;

  @Column(name = "meeting_start")
  private Date start;

  @Column(name = "meeting_end")
  private Date end;

  @Column(name = "duration")
  private long duration;

  @Column(name = "meeting_subject")
  private String subject;

  @Column(name = "location")
  private String location;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "organiser", nullable = false)
  private Person organiser;

  @OneToMany(cascade = ALL, mappedBy = "meeting")
  private Collection<MeetingInvitedPersonRelation> invitedPersons;

  private Meeting(MeetingBuilder builder) {
    this.start = builder.start;
    this.end = builder.end;
    this.duration = builder.duration;
    this.subject = builder.subject;
    this.location = builder.location;
    this.description = builder.description;
    this.organiser = builder.organiser;
    this.invitedPersons = builder.invitedPersons;
  }

  /**
   * Gets meeting id.
   *
   * @return the meeting id
   */
  public Long getMeetingId() {
    return meetingId;
  }

  /**
   * Sets meeting id.
   *
   * @param meetingId the meeting id
   */
  public void setMeetingId(Long meetingId) {
    this.meetingId = meetingId;
  }

  /**
   * Gets end.
   *
   * @return the end
   */
  public Date getEnd() {
    return end;
  }

  /**
   * Sets end.
   *
   * @param end the end
   */
  public void setEnd(Date end) {
    this.end = end;
  }

  /**
   * Gets duration.
   *
   * @return the duration
   */
  public long getDuration() {
    return duration;
  }

  /**
   * Sets duration.
   *
   * @param duration the duration
   */
  public void setDuration(long duration) {
    this.duration = duration;
  }

  /**
   * Gets subject.
   *
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Sets subject.
   *
   * @param subject the subject
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * Gets location.
   *
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets location.
   *
   * @param location the location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets organiser.
   *
   * @return the organiser
   */
  public Person getOrganiser() {
    return organiser;
  }


  /**
   * Sets organiser.
   *
   * @param organiser the organiser
   */
  public void setOrganiser(Person organiser) {
    this.organiser = organiser;
  }


  /**
   * Gets start.
   *
   * @return the start
   */
  public Date getStart() {
    return start;
  }

  /**
   * Sets start.
   *
   * @param start the start
   */
  public void setStart(Date start) {
    this.start = start;
  }

  /**
   * Gets invited persons.
   *
   * @return the invited persons
   */
  public Collection<MeetingInvitedPersonRelation> getInvitedPersons() {
    return invitedPersons;
  }

  /**
   * Sets invited persons.
   *
   * @param invitedPersons the invited persons
   */
  public void setInvitedPersons(Collection<MeetingInvitedPersonRelation> invitedPersons) {
    this.invitedPersons = invitedPersons;
  }

  /**
   * Add invited person.
   *
   * @param invitedPersonRelation the invited person relation
   */
  public void addInvitedPerson(MeetingInvitedPersonRelation invitedPersonRelation) {
    this.invitedPersons.add(invitedPersonRelation);
  }

  /**
   * Remove invited person.
   *
   * @param invitedPersonRelation the invited person relation
   */
  public void removeInvitedPerson(MeetingInvitedPersonRelation invitedPersonRelation) {
    this.invitedPersons.remove(invitedPersonRelation);
  }

  /**
   * Remove invited person.
   *
   * @param invitedPersonRelationId the invited person relation id
   */
  public void removeInvitedPerson(Long invitedPersonRelationId) {
    this.invitedPersons.stream().filter(invitedPersonRelation -> Objects.equals(
        invitedPersonRelation.getMeetingInvitedPersonRelationId(), invitedPersonRelationId))
        .findFirst().ifPresent(this::removeInvitedPerson);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Meeting)) {
      return false;
    }
    Meeting meeting = (Meeting) o;
    return getDuration() == meeting.getDuration()
        && Objects.equals(getMeetingId(), meeting.getMeetingId())
        && Objects.equals(start, meeting.start)
        && Objects.equals(getEnd(), meeting.getEnd())
        && Objects.equals(getSubject(), meeting.getSubject())
        && Objects.equals(getLocation(), meeting.getLocation())
        && Objects.equals(getDescription(), meeting.getDescription())
        && Objects.equals(getOrganiser(), meeting.getOrganiser())
        && Objects.equals(getInvitedPersons(), meeting.getInvitedPersons());
  }


  @Override
  public int hashCode() {

    return Objects.hash(getMeetingId(),
        getStart(),
        getEnd(),
        getDuration(),
        getSubject(),
        getLocation(),
        getDescription(),
        getOrganiser(),
        getInvitedPersons());
  }

  /**
   * The type Meeting builder.
   */
  public static final class MeetingBuilder {
    private Date start;
    private Date end;
    private long duration;
    private String subject;
    private String location;
    private String description;
    private Person organiser;
    private Collection<MeetingInvitedPersonRelation> invitedPersons;

    /**
     * Builder meeting builder.
     *
     * @return the meeting builder
     */
    public static MeetingBuilder builder() {
      return new MeetingBuilder();
    }

    /**
     * Sets start.
     *
     * @param start the start
     * @return the start
     */
    public MeetingBuilder setStart(Date start) {
      this.start = start;
      return this;
    }

    /**
     * Sets end.
     *
     * @param end the end
     * @return the end
     */
    public MeetingBuilder setEnd(Date end) {
      this.end = end;
      return this;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     * @return the duration
     */
    public MeetingBuilder setDuration(long duration) {
      this.duration = duration;
      return this;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     * @return the subject
     */
    public MeetingBuilder setSubject(String subject) {
      this.subject = subject;
      return this;
    }

    /**
     * Sets location.
     *
     * @param location the location
     * @return the location
     */
    public MeetingBuilder setLocation(String location) {
      this.location = location;
      return this;
    }

    /**
     * Sets description.
     *
     * @param description the description
     * @return the description
     */
    public MeetingBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets organiser.
     *
     * @param organiser the organiser
     * @return the organiser
     */
    public MeetingBuilder setOrganiser(Person organiser) {
      this.organiser = organiser;
      return this;
    }

    /**
     * Sets invited persons.
     *
     * @param invitedPersons the invited persons
     * @return the invited persons
     */
    public MeetingBuilder setInvitedPersons(
        Collection<MeetingInvitedPersonRelation> invitedPersons) {
      this.invitedPersons = invitedPersons;
      return this;
    }

    /**
     * Build meeting.
     *
     * @return the meeting
     */
    public Meeting build() {
      return new Meeting(this);
    }
  }
}
