package com.epam.brest.course.rest;

import com.epam.brest.course.model.Team;
import com.epam.brest.course.service.TeamService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
        {"classpath:rest-service-mock-configuration.xml"})
public class TeamControllerMockTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRestController teamRestController;

    private MockMvc mockMvc;

    private Team team1;
    private Team team2;

    @Before
    public void setUp(){

        team1 = new Team(1, "team 1", "country 1");
        team2 = new Team(2, "team 2", "country 2");

        mockMvc = MockMvcBuilders.standaloneSetup(teamRestController)
                .setMessageConverters
                        (new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown(){

        verify(teamService);
        reset(teamService);
    }

    @Test
    public void getAllTeamsMock() throws Exception{

        expect(teamService.getAllTeams()).andReturn(Arrays.asList(team1, team2));
        replay(teamService);

        mockMvc.perform(get("/teams")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].team_id", is(1)))
                .andExpect(jsonPath("$[0].team_name", is("team 1")))
                .andExpect(jsonPath("$[0].team_country", is("country 1")))
                .andExpect(jsonPath("$[1].team_id", is(2)))
                .andExpect(jsonPath("$[1].team_name", is("team 2")))
                .andExpect(jsonPath("$[1].team_country", is("country 2")));
    }

    @Test
    public void deleteTeamMock() throws Exception{

        teamService.deleteTeamById(1);
        replay(teamService);

        mockMvc.perform(delete("/teams/{id}", 1)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isFound());
    }


}
