package com.viadee.sonarQuest.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.controllers.ParticipationController;
import com.viadee.sonarQuest.controllers.TaskController;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.TaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;
import com.viadee.sonarQuest.services.AdventureService;
import com.viadee.sonarQuest.services.ExternalRessourceService;
import com.viadee.sonarQuest.services.StandardTaskService;
import com.viadee.sonarQuest.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "simulateSonarServer=true")
public class SonarQuestApplicationIT {

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskController taskController;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ParticipationController participationController;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private StandardTaskService standardTaskService;

    @Autowired
    private UserService userService;

    @Test(timeout = 1000) // There is hardly any data to fetch - this should be quick
    public void testWorldStructure() {
        final World sonarDungeon = worldRepository.findOne(1L);

        assertNotNull("Demo data not loaded properly", sonarDungeon);
        assertEquals("Demo data not loaded properly", Long.valueOf(1), sonarDungeon.getId());
        assertEquals("This is not the expected world data set", "World of Sonar Quest", sonarDungeon.getName());
    }

    /**
     * Walk through the participation on the backend with a developer perspective. This test assumes a spring
     * environment including a simulated sonar server and database access.
     */
    @Transactional
    @Test(timeout = 1000000) // There is hardly any data to fetch - this should be quick, altough there are
                             // write operations included
    public void developersCanParticipateInQuestsAndIssues() {

        // Join in on a quest.
        // Add Participation sonarWarrior, Quest1
        participationController.createParticipation(() -> "dev", 1L);

        Quest epicQuest = questRepository.findOne(1L);
        final List<Participation> participations = epicQuest.getParticipations();

        assertEquals("createParticipation does not work (Quest)", "Hidden danger in the woods!",
                participations.get(1).getQuest().getTitle());
        assertEquals("createParticipation does not work (Developer)", "Mike Magician",
                participations.get(1).getUser().getUsername());

        // Get to work on issue 1
        Task issue1 = taskRepository.findOne(5L);
        assertNull("addParticipationToTask does not work (Quest)", issue1.getParticipation());
        taskController.addParticipation(() -> "dev", 1L, 1L);
        issue1 = taskRepository.findOne(1L);

        assertEquals("addParticipation does not work (Quest)", "Hidden danger in the woods!",
                issue1.getParticipation().getQuest().getTitle());
        assertEquals("addParticipation does not work (Developer)", "Mike Magician",
                issue1.getParticipation().getUser().getUsername());
        assertEquals("addParticipation does not work (Status)", SonarQuestStatus.PROCESSED.getText(),
                issue1.getStatus());

        adventureService.updateAdventures(); // no effect expected
        User sonarWarrior = userService.findByUsername("dev");
        assertEquals("Gratification during updateAdventures does not work (Gold)", Long.valueOf(18),
                sonarWarrior.getGold());
        assertEquals("Gratification during updateAdventures does not work (XP)", Long.valueOf(15),
                sonarWarrior.getXp());

        // Solve task (close issue 1)
        final List<SonarQubeIssue> issueList = externalRessourceService.getIssuesForSonarQubeProject("15054");
        issueList.get(0).setStatus("CLOSED");

        final World sonarDungeon = worldRepository.findOne(1L);
        standardTaskService.updateStandardTasks(sonarDungeon);
        issue1 = taskRepository.findOne((long) 1);
        assertEquals("updateStandardTasks does not work (Title)",
                "NullPointerException might be thrown as 'toTypeArg' is nullable here",
                issue1.getTitle());
        assertEquals("updateStandardTasks does not work (Status)", SonarQuestStatus.SOLVED.getText(),
                issue1.getStatus());

        // Check Gratification
        // Gold = 18 (initial value) + 1 (Task) + 2(Magician) + 0 (no Artefacts)= 21
        // XP = 22 (inital value) + 2 (Task ) = 20
        sonarWarrior = userService.findByUsername("dev");
        assertEquals("Gratification during updateStandardTasks does not work (Gold)", Long.valueOf(21),
                sonarWarrior.getGold());
        assertEquals("Gratification during updateStandardTasks does not work (XP)", Long.valueOf(20),
                sonarWarrior.getXp());

        // Check Status of epicQuest
        epicQuest = questRepository.findOne(1L);
        assertEquals("QuestStatus not correct", QuestStates.OPEN, epicQuest.getStatus());
    }
}
