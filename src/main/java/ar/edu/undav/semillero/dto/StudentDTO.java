package ar.edu.undav.semillero.dto;

import java.time.LocalDate;

public class StudentDTO extends BaseDTO {

    private final long id;
    private final String name;
    private final String lastName;
    private final String nodeName;
    private final boolean deleted;
    private final LocalDate courseDate;
    private final LocalDate graduationDate;
    private final String email;
    private final String phone;
    private final String resumeUrl;
    private final boolean lookingForWork;
    private final boolean working;
    private final String feedback;
    private final long interviews;

    public StudentDTO(long id, String name, String lastName, String nodeName, boolean deleted, LocalDate courseDate, LocalDate graduationDate, String email, String phone, String resumeUrl, boolean lookingForWork, boolean working, String feedback, long interviews) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.nodeName = nodeName;
        this.deleted = deleted;
        this.courseDate = courseDate;
        this.graduationDate = graduationDate;
        this.email = email;
        this.phone = phone;
        this.resumeUrl = resumeUrl;
        this.lookingForWork = lookingForWork;
        this.working = working;
        this.feedback = feedback;
        this.interviews = interviews;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDate getCourseDate() {
        return courseDate;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public boolean isLookingForWork() {
        return lookingForWork;
    }

    public boolean isWorking() {
        return working;
    }

    public String getFeedback() {
        return feedback;
    }

    public long getInterviews() {
        return interviews;
    }
}
