package ru.curriculum.domain.admin.domain.stateSchedule;

import boot.IntegrationBoot;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.admin.user.repository.UserRepository;
import ru.curriculum.domain.helper.UserTestFactory;
import ru.curriculum.domain.stateSchedule.entity.StateProgram;
import ru.curriculum.domain.stateSchedule.repository.ImplementationFormRepository;
import ru.curriculum.domain.stateSchedule.repository.StateProgramRepository;
import ru.curriculum.domain.stateSchedule.repository.StudyModeRepository;
import ru.curriculum.domain.teacher.repository.TeacherRepository;

import java.util.Date;

public class StateProgramTest extends IntegrationBoot {

    @Autowired
    private StudyModeRepository studyModeRepository;

    @Autowired
    private ImplementationFormRepository implementationFormRepository;

    @Autowired
    private UserTestFactory userTestFactory;

    @Autowired
    private StateProgramRepository stateProgramRepository;

    @Test
    public void afterSavingAndGettingStateProgramsAreEquals() throws Exception {
        StateProgram stateProgram = StateProgram.builder()
            .targetAudience("English teachers")
            .name("ABC")
            .mode(studyModeRepository.findOne("fulltime"))
            .implementationForm(implementationFormRepository.findOne("modular"))
            .lernerCount(20)
            .groupCount(1)
            .countOfHoursPerLerner(100)
            .curator(userTestFactory.createAndSaveRandomUser())
            .dateStart(new Date())
            .dateFinish(new Date())
            .address("Kazan, main street, 1")
            .responsibleDepartment("Main department")
            .build();

        stateProgramRepository.save(stateProgram);

        StateProgram storedStateProgram = stateProgramRepository.findOne(stateProgram.id());
        Assert.assertEquals(stateProgram, storedStateProgram);
    }
}