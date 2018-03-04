package pl.wasper.bandmanagement.songbook.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.wasper.bandmanagement.songbook.dto.SongDto;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.helper.FakeFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SongController.class)
public class SongControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongController controller;

    private ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Test
    public void itShouldReturnListOfSongs() throws Exception {
        List<SongDto> songs = new ArrayList<>();
        songs.add(FakeFactory.prepareSongDtoWithId(1L, "Example 1", 100));
        songs.add(FakeFactory.prepareSongDtoWithId(2L, "Example 2", 110));

        given(controller.songList()).willReturn(songs);

        mockMvc.perform(get("/songs").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Example 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Example 2")));
    }

    @Test
    public void itShouldReturnOneSongById() throws Exception {
        SongDto song = FakeFactory.prepareSongDtoWithId(1L, "Example 1", 100);

        given(controller.findSong(1L)).willReturn(song);

        mockMvc.perform(get("/songs/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Example 1")));
    }

    @Test
    public void itShouldReturnErrorInfoWhenThereIsNoSearchedElement() throws Exception {
        given(controller.findSong(1L)).willThrow(new ElementNotFoundException("Unable to find song with id 1"));

        mockMvc.perform(get("/songs/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.method", is("GET")))
                .andExpect(jsonPath("$.message", is("Unable to find song with id 1")))
                .andExpect(jsonPath("$.url", is("http://localhost/songs/1")));
    }

    @Test
    public void itShouldAddSong() throws Exception {
        SongDto song = FakeFactory.prepareSongDto("Example 1", 100);
        String jsonString = mapper.writeValueAsString(song);

        SongDto songAdded = FakeFactory.prepareSongDtoWithId(1L, "Example 1", 100);

        given(controller.addSong(any(SongDto.class))).willReturn(songAdded);

        mockMvc.perform(post("/songs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonString))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(songAdded.getId().intValue())))
                .andExpect(jsonPath("$.title", is(songAdded.getTitle())));
    }

    @Test
    public void itShouldUpdateExistingSong() throws Exception {
        SongDto song = FakeFactory.prepareSongDtoWithId(1L, "Example 1", 100);

        String jsonString = mapper.writeValueAsString(song);

        given(controller.updateSong(any(SongDto.class))).willReturn(song);

        mockMvc.perform(put("/songs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(song.getId().intValue())))
                .andExpect(jsonPath("$.title", is(song.getTitle())));
    }

    @Test
    public void itShouldReturnErrorInfoWhenThereIsNoUpdatedElement() throws Exception {
        SongDto song = FakeFactory.prepareSongDtoWithId(1L, "Example 1", 100);
        String jsonString = mapper.writeValueAsString(song);

        given(controller.updateSong(any(SongDto.class)))
                .willThrow(new ElementNotFoundException(String.format("Unable to find song with id %d", song.getId())));

        mockMvc.perform(put("/songs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonString))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.method", is("PUT")))
                .andExpect(jsonPath("$.message", is("Unable to find song with id 1")))
                .andExpect(jsonPath("$.url", is("http://localhost/songs")));
    }

    @Test
    public void itShouldRemoveElement() throws Exception {
        doNothing().when(controller).removeSong(1L);

        mockMvc.perform(delete("/songs/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturnErrorWhenRemovedElementDoesNotExists() throws Exception {
        doThrow(new ElementNotFoundException("Unable to find song with id 1")).when(controller).removeSong(1L);

        mockMvc.perform(delete("/songs/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.method", is("DELETE")))
                .andExpect(jsonPath("$.message", is("Unable to find song with id 1")))
                .andExpect(jsonPath("$.url", is("http://localhost/songs/1")));
    }
}
