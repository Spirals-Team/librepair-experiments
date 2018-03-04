package pl.wasper.bandmanagement.songbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wasper.bandmanagement.songbook.dto.TagDto;
import pl.wasper.bandmanagement.songbook.dto.mapper.TagMapper;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.songbook.model.Tag;
import pl.wasper.bandmanagement.songbook.repository.TagRepository;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private TagMapper mapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TagDto> getTags() {
        return mapper.mapAll(tagRepository.findAll());
    }

    @Override
    public TagDto findOneById(Long id) throws ElementNotFoundException {
        Tag tag = tagRepository.findOne(id);

        if (tag == null) {
            throw new ElementNotFoundException(String.format("Unable to find tag with id %d", id));
        }

        return mapper.map(tag);
    }

    @Override
    public TagDto save(TagDto tag) {
        Tag savedTag = tagRepository.save(mapper.map(tag));

        return mapper.map(savedTag);
    }

    @Override
    public TagDto update(TagDto tag) throws ElementNotFoundException {
        if (tagRepository.findOne(tag.getId()) == null) {
            throw new ElementNotFoundException(String.format("Unable to find tag with id %d", tag.getId()));
        }

        Tag savedTag = tagRepository.save(mapper.map(tag));

        return mapper.map(savedTag);
    }

    @Override
    public void delete(Long id) throws ElementNotFoundException {
        if (tagRepository.findOne(id) == null) {
            throw new ElementNotFoundException(String.format("Unable to find tag with id %d", id));
        }

        tagRepository.delete(id);
    }
}
