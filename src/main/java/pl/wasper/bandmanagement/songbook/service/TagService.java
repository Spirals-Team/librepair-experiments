package pl.wasper.bandmanagement.songbook.service;

import pl.wasper.bandmanagement.songbook.dto.TagDto;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;

import java.util.List;

public interface TagService {
    List<TagDto> getTags();
    TagDto findOneById(Long id) throws ElementNotFoundException;
    TagDto save(TagDto tag);
    TagDto update(TagDto tag) throws ElementNotFoundException;
    void delete(Long id) throws ElementNotFoundException;
}
