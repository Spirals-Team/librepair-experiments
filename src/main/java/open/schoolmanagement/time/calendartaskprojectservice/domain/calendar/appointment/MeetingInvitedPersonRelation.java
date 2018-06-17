package open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.appointment;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;

/**
 * The type Meeting invited person relation.
 */
@Table(name = "meeting_invitedperson_relation")
@Entity
public class MeetingInvitedPersonRelation {

  @Id
  @GeneratedValue
  @Column(name = "meeting_invitedperson_relation_id", nullable = false)
  private Long meetingInvitedPersonRelationId;

  @OneToOne(mappedBy = "meeting_id", fetch = FetchType.LAZY)
  private Meeting meeting;


  @OneToOne(mappedBy = "person_id", fetch = FetchType.LAZY)
  private Person person;

  /**
   * Instantiates a new Meeting invited person relation.
   *
   * @param builder the builder
   */
  public MeetingInvitedPersonRelation(MeetingInvitedPersonRelationBuilder builder) {
    this.meeting = builder.meeting;
    this.person = builder.person;
  }

  /**
   * Gets meeting invited person relation id.
   *
   * @return the meeting invited person relation id
   */
  public Long getMeetingInvitedPersonRelationId() {
    return meetingInvitedPersonRelationId;
  }

  /**
   * Sets meeting invited person relation id.
   *
   * @param meetingInvitedPersonRelationId the meeting invited person relation id
   */
  public void setMeetingInvitedPersonRelationId(Long meetingInvitedPersonRelationId) {
    this.meetingInvitedPersonRelationId = meetingInvitedPersonRelationId;
  }

  /**
   * Gets meeting.
   *
   * @return the meeting
   */
  public Meeting getMeeting() {
    return meeting;
  }

  /**
   * Sets meeting.
   *
   * @param meeting the meeting
   */
  public void setMeeting(Meeting meeting) {
    this.meeting = meeting;
  }

  /**
   * Gets person.
   *
   * @return the person
   */
  public Person getPerson() {
    return person;
  }

  /**
   * Sets person.
   *
   * @param person the person
   */
  public void setPerson(Person person) {
    this.person = person;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MeetingInvitedPersonRelation)) {
      return false;
    }
    MeetingInvitedPersonRelation that = (MeetingInvitedPersonRelation) o;
    return Objects.equals(getMeetingInvitedPersonRelationId(),
        that.getMeetingInvitedPersonRelationId())
        && Objects.equals(getMeeting(), that.getMeeting())
        && Objects.equals(getPerson(), that.getPerson());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getMeetingInvitedPersonRelationId(), getMeeting(), getPerson());
  }


  /**
   * The type Meeting invited person relation builder.
   */
  public static final class MeetingInvitedPersonRelationBuilder {

    private Meeting meeting;
    private Person person;

    /**
     * Builder meeting invited person relation builder.
     *
     * @return the meeting invited person relation builder
     */
    public static MeetingInvitedPersonRelationBuilder builder() {
      return new MeetingInvitedPersonRelationBuilder();
    }



    /**
     * Sets meeting.
     *
     * @param meeting the meeting
     * @return the meeting
     */
    public MeetingInvitedPersonRelationBuilder setMeeting(Meeting meeting) {
      this.meeting = meeting;
      return this;
    }

    /**
     * Sets person.
     *
     * @param person the person
     * @return the person
     */
    public MeetingInvitedPersonRelationBuilder setPerson(Person person) {
      this.person = person;
      return this;
    }

    /**
     * Build meeting invited person relation.
     *
     * @return the meeting invited person relation
     */
    public MeetingInvitedPersonRelation build() {
      return new MeetingInvitedPersonRelation(this);
    }
  }
}
