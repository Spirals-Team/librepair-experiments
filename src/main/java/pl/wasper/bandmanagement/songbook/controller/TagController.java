package pl.wasper.bandmanagement.songbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.wasper.bandmanagement.songbook.dto.TagDto;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.songbook.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> tagList() {
        return tagService.getTags();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody TagDto tag) {
        return tagService.save(tag);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TagDto findTag(@PathVariable Long id) throws ElementNotFoundException {
        return tagService.findOneById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TagDto updateTag(@RequestBody TagDto tag) throws ElementNotFoundException {
        return tagService.update(tag);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeTag(@PathVariable Long id) throws ElementNotFoundException {
        tagService.delete(id);
    }
}
