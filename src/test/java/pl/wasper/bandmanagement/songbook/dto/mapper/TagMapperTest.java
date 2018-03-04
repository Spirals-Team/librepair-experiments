package pl.wasper.bandmanagement.songbook.dto.mapper;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import pl.wasper.bandmanagement.songbook.dto.SongDto;
import pl.wasper.bandmanagement.songbook.dto.TagDto;
import pl.wasper.bandmanagement.songbook.model.Song;
import pl.wasper.bandmanagement.songbook.model.Tag;
import pl.wasper.bandmanagement.songbook.service.SongService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TagMapperTest {
    private ModelMapper mapper = new ModelMapper();

    private TagMapper tagMapper;

    @Before
    public void before() {
        tagMapper = new TagMapper(mapper);
    }

    @Test
    public void itShouldMapTagToTagDto() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Tag");

        Song song = new Song();
        song.setId(1L);

        tag.setSongs(Collections.singletonList(song));

        TagDto tagDto = tagMapper.map(tag);

        assertEquals(tagDto.getId(), tag.getId());
        assertEquals(tagDto.getName(), tag.getName());

        assertEquals(tagDto.getSongs().size(), tag.getSongs().size());

        SongDto songDto = tagDto.getSongs().get(0);

        assertEquals(songDto.getId(), song.getId());
    }

    @Test
    public void itShouldMapTagDtoToTag() {
        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setName("Tag dto");

        SongDto songDto = new SongDto();
        songDto.setId(1L);

        tagDto.setSongs(Collections.singletonList(songDto));

        Tag tag = tagMapper.map(tagDto);

        assertEquals(tag.getId(), tagDto.getId());
        assertEquals(tag.getName(), tagDto.getName());

        assertEquals(tag.getSongs().size(), tagDto.getSongs().size());

        Song song = tag.getSongs().get(0);

        assertEquals(song.getId(), songDto.getId());
    }

    @Test
    public void itShouldMapTagsListToTagDtosList() {
        Tag tagOne = new Tag();
        tagOne.setId(1L);
        tagOne.setName("Tag one");

        Tag tagTwo = new Tag();
        tagTwo.setId(2L);
        tagTwo.setName("Tag two");

        Song songOne = new Song();
        songOne.setId(1L);

        Song songTwo = new Song();
        songTwo.setId(2L);

        tagOne.setSongs(Collections.singletonList(songOne));
        tagTwo.setSongs(Collections.singletonList(songTwo));

        List<Tag> tags = Arrays.asList(tagOne, tagTwo);
        List<TagDto> tagDtos = tagMapper.mapAll(tags);

        assertEquals(tagDtos.size(), tags.size());
        assertEquals(tagDtos.get(0).getId(), tags.get(0).getId());
        assertEquals(tagDtos.get(0).getName(), tags.get(0).getName());
        assertEquals(tagDtos.get(0).getSongs().size(), tags.get(0).getSongs().size());
        assertEquals(tagDtos.get(0).getSongs().get(0).getId(), tags.get(0).getSongs().get(0).getId());

        assertEquals(tagDtos.get(1).getId(), tags.get(1).getId());
        assertEquals(tagDtos.get(1).getName(), tags.get(1).getName());
        assertEquals(tagDtos.get(1).getSongs().size(), tags.get(1).getSongs().size());
        assertEquals(tagDtos.get(1).getSongs().get(0).getId(), tags.get(1).getSongs().get(0).getId());
    }

    @Test
    public void itShouldMapTagDtosListToTagsList() {
        TagDto tagDtoOne = new TagDto();
        tagDtoOne.setId(1L);
        tagDtoOne.setName("Tag one");

        TagDto tagDtoTwo = new TagDto();
        tagDtoTwo.setId(2L);
        tagDtoTwo.setName("Tag two");

        SongDto songDtoOne = new SongDto();
        songDtoOne.setId(1L);

        SongDto songDtoTwo = new SongDto();
        songDtoTwo.setId(2L);

        tagDtoOne.setSongs(Collections.singletonList(songDtoOne));
        tagDtoTwo.setSongs(Collections.singletonList(songDtoTwo));

        List<TagDto> tagDtos = Arrays.asList(tagDtoOne, tagDtoTwo);
        List<Tag> tags = tagMapper.mapAllDto(tagDtos);

        assertEquals(tags.size(), tagDtos.size());
        assertEquals(tags.get(0).getId(), tagDtos.get(0).getId());
        assertEquals(tags.get(0).getName(), tagDtos.get(0).getName());
        assertEquals(tags.get(0).getSongs().size(), tagDtos.get(0).getSongs().size());
        assertEquals(tags.get(0).getSongs().get(0).getId(), tagDtos.get(0).getSongs().get(0).getId());

        assertEquals(tags.get(1).getId(), tagDtos.get(1).getId());
        assertEquals(tags.get(1).getName(), tagDtos.get(1).getName());
        assertEquals(tags.get(1).getSongs().size(), tagDtos.get(1).getSongs().size());
        assertEquals(tags.get(1).getSongs().get(0).getId(), tagDtos.get(1).getSongs().get(0).getId());
    }
}
