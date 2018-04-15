package com.epam.brest.course.rest;

import com.epam.brest.course.model.Player;
import com.epam.brest.course.model.Team;
import com.epam.brest.course.service.PlayerService;
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
public class PlayerControllerMockTest {

    private Player player1;
    private Player player2;
    private Player playerForAdding;
    private Player playerExpected;
    private Team team;

    @Autowired
    private PlayerRestController playerRestController;

    private MockMvc mockMvc;

    @Autowired
    private PlayerService playerService;

    @Before
    public void setUp(){

        /*
         * Initialization of objects
         */
        player1 = new Player();
        player2 = new Player();
        playerForAdding = new Player();
        playerExpected = new Player();
        team = new Team();

        team.setTeam_id(1);
        team.setTeam_name("team 1");
        team.setTeam_country("country 1");

        player1.setPlayer_id(1);
        player1.setPlayer_name("player 1");
        player1.setPlayer_age(22);
        player1.setPlayer_number(1111);
        player1.setPlayer_cost(12000);
        player1.setPlayer_team_id(1);

        player2.setPlayer_id(2);
        player2.setPlayer_name("player 2");
        player2.setPlayer_age(12);
        player2.setPlayer_number(2222);
        player2.setPlayer_cost(10000);
        player2.setPlayer_team_id(1);

        playerForAdding.setPlayer_name("player for adding");
        playerForAdding.setPlayer_age(30);
        playerForAdding.setPlayer_number(2);
        playerForAdding.setPlayer_cost(20000);
        playerForAdding.setPlayer_team_id(1);

        playerExpected.setPlayer_id(3);
        playerExpected.setPlayer_name("player for adding");
        playerExpected.setPlayer_age(30);
        playerExpected.setPlayer_number(2);
        playerExpected.setPlayer_cost(20000);
        playerExpected.setPlayer_team_id(1);

        /*
         * Build mock instance
        */
        mockMvc = MockMvcBuilders.standaloneSetup(playerRestController)
                .setMessageConverters
                        (new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown(){

        verify(playerService);
        reset(playerService);
    }

    @Test
    public void getPlayersMock() throws Exception{

        expect(playerService.getAllPlayers()).andReturn(Arrays.asList(player1, player2));
        /*
         * activate the mock
         */
        replay(playerService);

        mockMvc.perform(get("/players")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].player_id", is(1)))
                .andExpect(jsonPath("$[0].player_name", is("player 1")))
                .andExpect(jsonPath("$[0].player_number", is(1111)))
                .andExpect(jsonPath("$[0].player_age", is(22)))
                .andExpect(jsonPath("$[0].player_cost", is(12000)))
                .andExpect(jsonPath("$[0].player_team_id", is(1)))
                .andExpect(jsonPath("$[1].player_id", is(2)))
                .andExpect(jsonPath("$[1].player_name", is("player 2")))
                .andExpect(jsonPath("$[1].player_number", is(2222)))
                .andExpect(jsonPath("$[1].player_age", is(12)))
                .andExpect(jsonPath("$[1].player_cost", is(10000)))
                .andExpect(jsonPath("$[1].player_team_id", is(1)));
    }

    @Test
    public void getPlayerByIdMock() throws Exception{

        expect(playerService.getPlayerById(1)).andReturn(player1);
        replay(playerService);

        mockMvc.perform(get("/players/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("player_id", is(1)))
                .andExpect(jsonPath("player_name", is("player 1")))
                .andExpect(jsonPath("player_number", is(1111)))
                .andExpect(jsonPath("player_age", is(22)))
                .andExpect(jsonPath("player_cost", is(12000)))
                .andExpect(jsonPath("player_team_id", is(1)));
    }

    @Test
    public void deletePlayer() throws Exception{

        playerService.deletePlayerById(2);
        expect(playerService.getAllPlayers()).andReturn(Arrays.asList(player1));
        replay(playerService);

        mockMvc.perform(delete("/players/{id}",2))
                .andExpect(status().isFound());
        mockMvc.perform(get("/players")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].player_id", is(1)))
                .andExpect(jsonPath("$[0].player_name", is("player 1")))
                .andExpect(jsonPath("$[0].player_number", is(1111)))
                .andExpect(jsonPath("$[0].player_age", is(22)))
                .andExpect(jsonPath("$[0].player_cost", is(12000)))
                .andExpect(jsonPath("$[0].player_team_id", is(1)));
    }

//    @Test
//    public void addPlayerMock() throws Exception{
//
//       expect(playerService.addPlayer(playerForAdding)).andReturn(playerExpected);
//       replay(playerService);
//
//        Gson gson = new Gson();
//
//        mockMvc.perform(post("/players")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(gson.toJson(playerForAdding))
//                .accept(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(jsonPath("player_id", is(3)))
//                .andExpect(jsonPath("player_name", is("player for adding")))
//                .andExpect(jsonPath("player_number", is(2)))
//                .andExpect(jsonPath("player_age", is(30)))
//                .andExpect(jsonPath("player_cost", is(20000)))
//                .andExpect(jsonPath("player_team_id", is(1)));
//    }
}
