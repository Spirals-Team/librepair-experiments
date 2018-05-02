package net.posesor;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SubjectEntry {
    @Id
    private String subjectId;
    private String principalName;
    private String name;
}
