package py.org.fundacionparaguaya.pspserver.system.entities;

import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "activity", schema = "system")
public class ActivityEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system.activity_activity_id_seq")
    @SequenceGenerator(name="system.activity_activity_id_seq", sequenceName="system.activity_activity_id_seq", allocationSize=1)
    @Column(name = "activity_id")

    private Long activityId;
    private Long userId;
    private String activityType;
    private String description;
    private Long organizationId;
    private Long applicationId;
    private String createAt;

    public Long getActivityId () { return activityId; }
    public void setActivityId (Long activityId) { this.activityId = activityId; }

    public Long getUserId() {return userId;}
    public void setUserId(Long userId) { this.userId = userId; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getDescription(){ return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getOrganizationId() { return organizationId; }
    public void setOrganizationId(Long organizationId) { this.organizationId = organizationId; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getCreateAt() { return createAt; }
    public void setCreateAt(String createAt) { this.createAt = createAt; }

    @Override
    public String toString() {
        return "Activity [activityId=" + activityId + ", userId=" + userId + ", activityType=" + activityType + ", description=" + description +
                ", organizationId=" + organizationId + ", applicationId=" + applicationId + ", createAt=" + createAt + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (activityId == null || obj == null || getClass() != obj.getClass())
            return false;
        ActivityEntity toCompare = (ActivityEntity) obj;
        return activityId.equals(toCompare.activityId);
    }

    @Override
    public int hashCode() {
        return activityId == null ? 0 : activityId.hashCode();
    }
}
