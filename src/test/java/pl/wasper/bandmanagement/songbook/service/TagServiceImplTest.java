package pl.wasper.bandmanagement.songbook.service;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.wasper.bandmanagement.songbook.dto.TagDto;
import pl.wasper.bandmanagement.songbook.dto.mapper.TagMapper;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.helper.FakeFactory;
import pl.wasper.bandmanagement.songbook.model.Tag;
import pl.wasper.bandmanagement.songbook.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TagRepository repository;

    @Mock
    private TagMapper mapper;

    @Test
    public void ItShouldReturnTagsList() {
        List<Tag> tags = new ArrayList<>();
        tags.add(FakeFactory.prepareTagWithId(1L, "Example tag 1"));
        tags.add(FakeFactory.prepareTagWithId(2L, "Example tag 2"));

        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(FakeFactory.prepareTagDtoWithId(1L, "Example tag 1"));
        tagsDto.add(FakeFactory.prepareTagDtoWithId(2L, "Example tag 2"));

        TagServiceImpl service = new TagServiceImpl(repository, mapper);

        when(repository.findAll()).thenReturn(tags);
        when(mapper.mapAll(tags)).thenReturn(tagsDto);

        assertEquals(service.getTags(), tagsDto);
    }

    @Test
    public void itShouldReturnTagById() throws ElementNotFoundException {
        Long id = 1L;
        Tag exampleTag = FakeFactory.prepareTagWithId(id, "Example tag");
        TagDto exampleTagDto = FakeFactory.prepareTagDtoWithId(id, "Example tag");

        TagServiceImpl service = new TagServiceImpl(repository, mapper);

        when(repository.findOne(id)).thenReturn(exampleTag);
        when(mapper.map(exampleTag)).thenReturn(exampleTagDto);

        assertEquals(service.findOneById(id), exampleTagDto);
    }

    @Test(expected = ElementNotFoundException.class)
    public void itShouldThrowExceptionWhereTagDoesntExists() throws ElementNotFoundException {
        Long id = 1L;

        TagServiceImpl service = new TagServiceImpl(repository, mapper);

        when(repository.findOne(id)).thenReturn(null);

        service.findOneById(id);
    }

    @Test
    public void itShouldReturnAddedTagObject() {
        Tag exampleTag = FakeFactory.prepareTag("Example tag");
        TagDto exampleTagDto = FakeFactory.prepareTagDto("Example tag");

        Tag exampleTagAdded = FakeFactory.prepareTagWithId(1L, "Example tag");
        TagDto exampleTagDtoAdded = FakeFactory.prepareTagDtoWithId(1L, "Example tag");

        TagServiceImpl service = new TagServiceImpl(repository, mapper);

        when(mapper.map(exampleTagDto)).thenReturn(exampleTag);
        when(repository.save(exampleTag)).thenReturn(exampleTagAdded);
        when(mapper.map(exampleTagAdded)).thenReturn(exampleTagDtoAdded);

        assertEquals(service.save(exampleTagDto), exampleTagDtoAdded);
    }

    @Test
    public void itShouldUpdateExistingTag() throws ElementNotFoundException {
        Tag exampleTag = FakeFactory.prepareTagWithId(1L, "Example tag before");
        TagDto exampleTagDto = FakeFactory.prepareTagDtoWithId(1L, "Example tag before");
        TagDto exampleTagDtoAfter = FakeFactory.prepareTagDtoWithId(1L, "Example tag after");

        TagServiceImpl service = new TagServiceImpl(repository, mapper);

        when(mapper.map(exampleTagDto)).thenReturn(exampleTag);
        when(repository.findOne(exampleTag.getId())).thenReturn(exampleTag);

        exampleTag.setName("Example tag after");

        when(repository.save(exampleTag)).thenReturn(exampleTag);
        when(mapper.map(exampleTag)).thenReturn(exampleTagDtoAfter);

        assertEquals(service.update(exampleTagDto), exampleTagDtoAfter);
    }

    @Test(expected = ElementNotFoundException.class)
    public void itShouldThrowExceptionWhenTagWithSpecificIdDoesNotExists() throws ElementNotFoundException {
        TagDto exampleTagDto = FakeFactory.prepareTagDtoWithId(1L, "Example tag");

        TagServiceImpl service = new TagServiceImpl(repository, mapper);

        when(repository.findOne(exampleTagDto.getId())).thenReturn(null);

        service.update(exampleTagDto);
    }

    @Test(expected = ElementNotFoundException.class)
    public void itShouldThrowExceptionDuringRemoveWhenTagWithIdDoesNotExists() throws ElementNotFoundException {
        TagDto exampleTagDto = FakeFactory.prepareTagDtoWithId(1L, "Example tag");

        TagServiceImpl service = new TagServiceImpl(repository, mapper);

        when(repository.findOne(exampleTagDto.getId())).thenReturn(null);

        service.delete(exampleTagDto.getId());
    }
}
