package com.psl.integrador.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psl.integrador.model.enums.Expertise;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;

public class Detail {

    @NotEmpty(message = "Topic cannot be empty")
    private Topic topic;

    @NotEmpty(message = "Expertise cannot be empty")
    private Expertise expertise;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d H:m:s")
    private LocalDateTime addedAt;

    public Detail() {
        super();
    }

    private Detail(Topic topic, Expertise expertise, LocalDateTime addedAt) {
        this.topic = topic;
        this.expertise = expertise;
        this.addedAt = addedAt;
    }

    Detail(Topic topic, Expertise expertise) {
        this(topic, expertise, LocalDateTime.now());
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Expertise getExpertise() {
        return expertise;
    }

    public void setExpertise(Expertise expertise) {
        this.expertise = expertise;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}
