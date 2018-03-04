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
import pl.wasper.bandmanagement.songbook.dto.TagDto;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.helper.FakeFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TagController.class)
public class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagController controller;

    private ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Test
    public void itShouldReturnAllTags() throws Exception {
        List<TagDto> tags = new ArrayList<>();
        tags.add(FakeFactory.prepareTagDtoWithId(1L, "Example 1"));
        tags.add(FakeFactory.prepareTagDtoWithId(2L, "Example 2"));

        given(controller.tagList()).willReturn(tags);

        mockMvc.perform(get("/tags").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Example 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Example 2")));

    }

    @Test
    public void itShouldReturnOneTagById() throws Exception {
        Long id = 1L;
        TagDto tag = FakeFactory.prepareTagDtoWithId(id, "example");

        given(controller.findTag(id)).willReturn(tag);

        mockMvc.perform(get("/tags/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is("example")));
    }

    @Test
    public void itShouldReturnErrorInfoWhenSearchedElementDoesNotExists() throws Exception {
        given(controller.findTag(1L)).willThrow(new ElementNotFoundException("Unable to find tag with id 1"));

        mockMvc.perform(get("/tags/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.method", is("GET")))
                .andExpect(jsonPath("$.message", is("Unable to find tag with id 1")))
                .andExpect(jsonPath("$.url", is("http://localhost/tags/1")));
    }

    @Test
    public void itShouldAddTag() throws Exception {
        TagDto tagBefore = FakeFactory.prepareTagDto("Example");
        String jsonString = mapper.writeValueAsString(tagBefore);

        TagDto tagAfter = FakeFactory.prepareTagDtoWithId(1L, "Example");

        given(controller.addTag(any(TagDto.class))).willReturn(tagAfter);

        mockMvc.perform(post("/tags")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(tagAfter.getId().intValue())))
                .andExpect(jsonPath("$.name", is(tagAfter.getName())));
    }

    @Test
    public void itShouldUpdateExistingTag() throws Exception {
        TagDto tag = FakeFactory.prepareTagDtoWithId(1L, "Example");
        String jsonString = mapper.writeValueAsString(tag);

        given(controller.updateTag(any(TagDto.class))).willReturn(tag);

        mockMvc.perform(put("/tags")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(tag.getId().intValue())))
                .andExpect(jsonPath("$.name", is(tag.getName())));
    }

    @Test
    public void itShouldReturnErrorInfoWhenUpdatedTagDoesNotExists() throws Exception {
        TagDto tag = FakeFactory.prepareTagDtoWithId(1L, "Example");
        String jsonString = mapper.writeValueAsString(tag);

        given(controller.updateTag(any(TagDto.class)))
                .willThrow(new ElementNotFoundException("Unable to find tag with id 2"));

        mockMvc.perform(put("/tags")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.method", is("PUT")))
                .andExpect(jsonPath("$.message", is("Unable to find tag with id 2")))
                .andExpect(jsonPath("$.url", is("http://localhost/tags")));
    }

    @Test
    public void itShouldRemoveTag() throws Exception {
        doNothing().when(controller).removeTag(1L);

        mockMvc.perform(delete("/tags/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturnErrorWhenRemovedTagDoesNotExists() throws Exception {
        doThrow(new ElementNotFoundException("Unable to find tag with id 1")).when(controller).removeTag(1L);

        mockMvc.perform(delete("/tags/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.method", is("DELETE")))
                .andExpect(jsonPath("$.message", is("Unable to find tag with id 1")))
                .andExpect(jsonPath("$.url", is("http://localhost/tags/1")));
    }
}
