package com.codesquad.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {

    @Transient
    private final static String TIME_FORMAT = "yyyy.MM.dd";

    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdTime;

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime modifiedDate;

    @JsonIgnore
    public String getFormattedCreatedTime() {
        return formatDateToString(createdTime, TIME_FORMAT);
    }

    private String formatDateToString(LocalDateTime time, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return time.format(formatter);
    }
}
