package by.imsha.domain;

import by.imsha.api.rest.serializers.CustomLocalDateTimeSerializer;
import by.imsha.utils.ServiceUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 */
public class Mass {

    @Id
    private String id;

    //    @ApiObjectField(description = "City ID.", required = true)
    @NotNull
    @NotEmpty
    private String cityId;


//    @ApiObjectField(description = "Language code of provided mass. Available codes are presented in ISO 639-2 Language Code List.", required = true)
    @NotNull
    @NotEmpty
    private String langCode;

//    @ApiObjectField(description = "Duration of mass in ms, default value = 3600 (1 hour)",  required = false)
//    @NotNull
    private long duration = 3600;

//    @ApiObjectField(description = "Time of regular mass, that is defined throw time and days.", required = false)
//    @JsonFormat(pattern = "KK:mm")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)

    @Pattern(regexp = "^[0-2][0-9]:[0-5][0-9]$")
    private String time;

    //    @ApiObjectField(description = "Array of days that is defined for regular mass. Days are presented via codes from 1 to 7:  Monday = 1 .. Saturday = 6, Sunday = 7", required = false)
    private int[] days;

//    @ApiObjectField(description =  "Parish ID for mass", required = true)
    @NotNull
    @NotEmpty
    private String parishId;

//    @ApiObjectField(description = "Flag defines whether mass is deleted by merchant-user", required = false)
    private boolean deleted = false;

//    @ApiObjectField(description = "Notes for mass created", required = false)
    private String notes;


//    @ApiObjectField(description = "Start time for non regular mass, that occurs and is defined only once", required = false)
    private long singleStartTimestamp;

    @LastModifiedDate
    @JsonSerialize(using= CustomLocalDateTimeSerializer.class)
    private LocalDateTime lastModifiedDate;



    public Mass() {
    }

    @AssertTrue(message="Only one of fields have to be populated: time or singleStartTimestamp")
    private boolean isValid() {
        boolean timeIsNotNull = StringUtils.isNotBlank(this.time) && singleStartTimestamp == 0;
        boolean singleTimestampIsNotNull = singleStartTimestamp != 0 && StringUtils.isBlank(this.time);
        return timeIsNotNull || singleTimestampIsNotNull;
    }

    @AssertTrue(message="Please specify days for scheduled mass (you already specified field 'time').")
    private boolean isValidScheduledMassEmptyDays() {
        boolean validScheduledMass = true;
        if(StringUtils.isNotBlank(this.time)){
            validScheduledMass = ArrayUtils.isNotEmpty(this.days);
        }
        return validScheduledMass;
    }

    @AssertTrue(message="Please specify 'time' for scheduled mass (you already specified field 'days').")
    private boolean isValidScheduledMAssEmptyDays(){
        boolean      validScheduledMass = true;
        if(ArrayUtils.isNotEmpty(this.days)){
            validScheduledMass = StringUtils.isNotBlank(this.time);
        }
        return validScheduledMass;
    }


    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }



    public Mass(String langCode,String cityId, long duration, String parishId, String time, long start, int[] days) {
        this.langCode = langCode;
        this.cityId = cityId;
        this.duration = duration;
        this.parishId = parishId;
        this.time = time;
        this.singleStartTimestamp = start;
        this.days = days;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mass)) return false;

        Mass mass = (Mass) o;

        if (duration != mass.duration) return false;
        if (singleStartTimestamp != mass.singleStartTimestamp) return false;
        if (!Arrays.equals(days, mass.days)) return false;
        if (!langCode.equals(mass.langCode)) return false;
        if (!parishId.equals(mass.parishId)) return false;
        if (time != null ? !time.equals(mass.time) : mass.time != null) return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = langCode != null ? langCode.hashCode() : 0;
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (days != null ? Arrays.hashCode(days) : 0);
        result = 31 * result + (parishId != null ? parishId.hashCode() : 0);
        result = 31 * result + (int)(singleStartTimestamp  ^ (singleStartTimestamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Mass{" +
                "id='" + id + '\'' +
                ", cityId='" + cityId + '\'' +
                ", langCode='" + langCode + '\'' +
                ", duration=" + duration +
                ", time='" + time + '\'' +
                ", days=" + Arrays.toString(days) +
                ", parishId='" + parishId + '\'' +
                ", deleted=" + deleted +
                ", notes='" + notes + '\'' +
                ", singleStartTimestamp=" + singleStartTimestamp +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }


    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getParishId() {
        return parishId;
    }

    public void setParishId(String parishId) {
        this.parishId = parishId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getSingleStartTimestamp() {
        return singleStartTimestamp;
    }

    public void setSingleStartTimestamp(long singleStartTimestamp) {
        this.singleStartTimestamp = singleStartTimestamp;
    }

    public int[] getDays() {
        return days;
    }

    public void setDays(int[] days) {
        this.days = days;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
