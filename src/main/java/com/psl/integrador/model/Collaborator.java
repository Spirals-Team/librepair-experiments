package com.psl.integrador.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psl.integrador.model.enums.Expertise;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "collaborators")
public class Collaborator {

    @Id
    private String id;

    @NotEmpty(message = "The name is required")
    private String name;

    @NotEmpty(message = "The email is required")
    private String email;

    private List<Detail> topicsToTeach;

    private List<Detail> topicsToLearn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d h:m:s")
    private LocalDateTime createdAt;

    public Collaborator() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Detail> getTopicsToTeach() {
        return topicsToTeach;
    }

    public void setTopicsToTeach(List<Detail> topicsToTeach) {
        this.topicsToTeach = topicsToTeach;
    }

    public List<Detail> getTopicsToLearn() {
        return topicsToLearn;
    }

    public void setTopicsToLearn(List<Detail> topicsToLearn) {
        this.topicsToLearn = topicsToLearn;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean addTopicToTeach(Topic topic, Expertise expertise) {
        if (topicsToTeach == null)
            topicsToTeach = new ArrayList<>();

        return topicsToTeach.add(new Detail(topic, expertise));
    }

    public boolean addTopicToLearn(Topic topic, Expertise expertise) {
        if (topicsToLearn == null)
            topicsToLearn = new ArrayList<>();

        return topicsToLearn.add(new Detail(topic, expertise));
    }
}
