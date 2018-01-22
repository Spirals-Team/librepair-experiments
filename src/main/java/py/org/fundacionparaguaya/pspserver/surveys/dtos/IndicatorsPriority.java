package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author mgonzalez
 *
 */
public class IndicatorsPriority {
    
    @JsonProperty("snapshot_indicator_id")
    private Long snapshotIndicatorId;
    
    @JsonProperty("priorities")
    private List<SnapshotIndicatorPriority> priorities = null;
    
    /*@JsonProperty("snapshot_indicator_priority_id")
    private Long snapshotIndicatorPriorityId;*/
    
   /* @JsonProperty("indicator")
    private String indicator;
    
    @JsonProperty("priority")
    private Integer priority;
    
    @JsonProperty("description")
    private String description;*/
    
    @ApiModelProperty(value = "Snapshot Indicator's id")
    public Long getSnapshotIndicatorId() {
        return snapshotIndicatorId;
    }

    public void setSnapshotIndicatorId(Long snapshotIndicatorId) {
        this.snapshotIndicatorId = snapshotIndicatorId;
    }
    
    @ApiModelProperty(value = "Snapshot Indicator's priorities")
    public List<SnapshotIndicatorPriority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<SnapshotIndicatorPriority> priorities) {
        this.priorities = priorities;
    }
    
   /* @ApiModelProperty(value = "Snapshot Indicator Priority's id")
    public Long getSnapshotIndicatorPriorityId() {
        return snapshotIndicatorPriorityId;
    }

    public void setSnapshotIndicatorPriorityId(Long snapshotIndicatorPriorityId) {
        this.snapshotIndicatorPriorityId = snapshotIndicatorPriorityId;
    }

    @ApiModelProperty(value = "The indicator that was prioritized")
    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    @ApiModelProperty(value = "Indicator priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @ApiModelProperty(value = "Priority description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }*/

    public IndicatorsPriority snapshotIndicatorId(Long snapshotIndicatorId) {
        this.snapshotIndicatorId = snapshotIndicatorId;
        return this;
    }
    
    public IndicatorsPriority priorities(List<SnapshotIndicatorPriority> priorities) {
        this.priorities = priorities;
        return this;
    }
    
   /* public SnapshotIndicatorPriority snapshotIndicatorPriorityId(Long snapshotIndicatorPriorityId) {
        this.snapshotIndicatorPriorityId = snapshotIndicatorPriorityId;
        return this;
    }
    
    public SnapshotIndicatorPriority indicator(String indicator) {
        this.indicator = indicator;
        return this;
    }
    
    public SnapshotIndicatorPriority priority(Integer priority) {
        this.priority = priority;
        return this;
    }
    
    public SnapshotIndicatorPriority description(String description) {
        this.description = description;
        return this;
    }*/
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Snapshot Indicator Priority {\n");
        sb.append("    snapshotIndicatorId: ").append(toIndentedString(snapshotIndicatorId)).append("\n");
        sb.append("    priorities: ").append(toIndentedString(priorities)).append("\n");
        /*sb.append("    snapshotIndicatorPriorityId: ").append(toIndentedString(snapshotIndicatorPriorityId)).append("\n");
        sb.append("    indicator: ").append(toIndentedString(indicator)).append("\n");
        sb.append("    priority:   ").append(toIndentedString(priority)).append("\n");
        sb.append("    description:   ").append(toIndentedString(description)).append("\n");*/
        sb.append("}");
        return sb.toString();
    }
    
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndicatorsPriority that = (IndicatorsPriority) o;

        return com.google.common.base.Objects.equal(this.snapshotIndicatorId, that.snapshotIndicatorId) &&
                com.google.common.base.Objects.equal(this.priorities, that.priorities); //&&
               /* com.google.common.base.Objects.equal(this.indicator, that.indicator) &&
                com.google.common.base.Objects.equal(this.priority, that.priority) &&
                com.google.common.base.Objects.equal(this.description, that.description);*/
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(snapshotIndicatorId, priorities);
    }
    

}
