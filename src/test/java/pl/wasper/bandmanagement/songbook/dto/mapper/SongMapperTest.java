package pl.wasper.bandmanagement.songbook.dto.mapper;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import pl.wasper.bandmanagement.songbook.dto.SongDto;
import pl.wasper.bandmanagement.songbook.dto.TagDto;
import pl.wasper.bandmanagement.songbook.model.Song;
import pl.wasper.bandmanagement.songbook.model.Tag;

import java.util.*;

import static org.junit.Assert.*;

public class SongMapperTest {
    private ModelMapper mapper = new ModelMapper();

    private SongMapper songMapper;

    @Before
    public void before() {
        songMapper = new SongMapper(mapper);
    }

    @Test
    public void itShouldMapSongToSongDto() {
        Song song = createSong(1L, "Title", 120, "Content", "Info", "G");

        Tag tagOne = new Tag();
        tagOne.setId(1L);
        tagOne.setName("Tag one");
        Tag tagTwo = new Tag();
        tagTwo.setId(2L);
        tagTwo.setName("Tag two");
        List<Tag> tags = new ArrayList<>(Arrays.asList(tagOne, tagTwo));

        song.setTags(tags);

        SongDto songDto = songMapper.map(song);

        assertEquals(songDto.getId(), song.getId());
        assertEquals(songDto.getTitle(), song.getTitle());
        assertEquals(songDto.getTempo(), song.getTempo());
        assertEquals(songDto.getContent(), song.getContent());
        assertEquals(songDto.getInfo(), song.getInfo());
        assertEquals(songDto.getTone(), song.getTone());

        assertEquals(songDto.getTags().size(), song.getTags().size());

        Iterator<TagDto> iterator = songDto.getTags().iterator();

        TagDto tagDtoOne = iterator.next();
        assertEquals(tagDtoOne.getId(), tagOne.getId());
        assertEquals(tagDtoOne.getName(), tagOne.getName());

        TagDto tagDtoTwo = iterator.next();
        assertEquals(tagDtoTwo.getId(), tagTwo.getId());
        assertEquals(tagDtoTwo.getName(), tagTwo.getName());
    }

    @Test
    public void itShouldMapSongDtoToSong() {
        SongDto songDto = createSongDto(1L, "Title", 120, "Content", "Info", "G");

        TagDto tagDtoOne = new TagDto();
        tagDtoOne.setId(1L);
        tagDtoOne.setName("Tag dto one");

        TagDto tagDtoTwo = new TagDto();
        tagDtoTwo.setId(2L);
        tagDtoTwo.setName("Tag dto two");

        songDto.setTags(new ArrayList<>(Arrays.asList(tagDtoOne, tagDtoTwo)));

        Song song = songMapper.map(songDto);

        assertEquals(song.getId(), songDto.getId());
        assertEquals(song.getTitle(), songDto.getTitle());
        assertEquals(song.getTempo(), songDto.getTempo());
        assertEquals(song.getContent(), songDto.getContent());
        assertEquals(song.getInfo(), songDto.getInfo());
        assertEquals(song.getTone(), songDto.getTone());

        assertEquals(song.getTags().size(), songDto.getTags().size());

        Iterator<Tag> iterator = song.getTags().iterator();

        Tag tagOne = iterator.next();
        assertEquals(tagOne.getId(), tagDtoOne.getId());
        assertEquals(tagOne.getName(), tagDtoOne.getName());

        Tag tagTwo = iterator.next();
        assertEquals(tagTwo.getId(), tagDtoTwo.getId());
        assertEquals(tagTwo.getName(), tagDtoTwo.getName());
    }
    
    @Test
    public void itShouldMapSongsListToSongDtosList() {
        Song songOne = createSong(1L, "Title one", 120, "Content one", "Info 1", "G");
        Song songTwo = createSong(2L, "Title two", 130, "Content two", "Info 2", "E");

        Tag tagOne = new Tag();
        tagOne.setId(1L);
        tagOne.setName("Tag one");

        Tag tagTwo = new Tag();
        tagTwo.setId(2L);
        tagTwo.setName("Tag two");

        songOne.setTags(Collections.singletonList(tagOne));
        songTwo.setTags(Collections.singletonList(tagTwo));

        List<Song> songs = Arrays.asList(songOne, songTwo);
        List<SongDto> songDtos = songMapper.mapAll(songs);

        assertEquals(songDtos.size(), songs.size());
        assertEquals(songDtos.get(0).getId(), songs.get(0).getId());
        assertEquals(songDtos.get(0).getTitle(), songs.get(0).getTitle());
        assertEquals(songDtos.get(0).getTags().size(), songs.get(0).getTags().size());
        assertEquals(songDtos.get(0).getTags().get(0).getId(), songs.get(0).getTags().get(0).getId());

        assertEquals(songDtos.get(1).getId(), songs.get(1).getId());
        assertEquals(songDtos.get(1).getTitle(), songs.get(1).getTitle());
        assertEquals(songDtos.get(1).getTags().size(), songs.get(1).getTags().size());
        assertEquals(songDtos.get(1).getTags().get(0).getId(), songs.get(1).getTags().get(0).getId());
    }

    @Test
    public void itShouldMapSongDtosListToSongList() {
        SongDto songDtoOne = createSongDto(1L, "Title one", 120, "Content one", "Info 1", "G");
        SongDto songDtoTwo = createSongDto(2L, "Title two", 130, "Content two", "Info 2", "E");

        TagDto tagDtoOne = new TagDto();
        tagDtoOne.setId(1L);
        tagDtoOne.setName("Tag one");

        TagDto tagDtoTwo = new TagDto();
        tagDtoTwo.setId(2L);
        tagDtoTwo.setName("Tag two");

        songDtoOne.setTags(Collections.singletonList(tagDtoOne));
        songDtoTwo.setTags(Collections.singletonList(tagDtoTwo));

        List<SongDto> songDtos = Arrays.asList(songDtoOne, songDtoTwo);
        List<Song> songs = songMapper.mapAllDto(songDtos);

        assertEquals(songs.size(), songDtos.size());
        assertEquals(songs.get(0).getId(), songDtos.get(0).getId());
        assertEquals(songs.get(0).getTitle(), songDtos.get(0).getTitle());
        assertEquals(songs.get(0).getTags().size(), songDtos.get(0).getTags().size());
        assertEquals(songs.get(0).getTags().get(0).getId(), songDtos.get(0).getTags().get(0).getId());

        assertEquals(songs.get(1).getId(), songDtos.get(1).getId());
        assertEquals(songs.get(1).getTitle(), songDtos.get(1).getTitle());
        assertEquals(songs.get(1).getTags().size(), songDtos.get(1).getTags().size());
        assertEquals(songs.get(1).getTags().get(0).getId(), songDtos.get(1).getTags().get(0).getId());
    }
    
    private SongDto createSongDto(Long id, String title, int tempo, String content, String info, String tone) {
        SongDto songDto = new SongDto();
        songDto.setId(id);
        songDto.setTitle(title);
        songDto.setTempo(tempo);
        songDto.setContent(content);
        songDto.setInfo(info);
        songDto.setTone(tone);
        
        return songDto;
    }

    private Song createSong(Long id, String title, int tempo, String content, String info, String tone) {
        Song song = new Song();
        song.setId(id);
        song.setTitle(title);
        song.setTempo(tempo);
        song.setContent(content);
        song.setInfo(info);
        song.setTone(tone);

        return song;
    }
}
