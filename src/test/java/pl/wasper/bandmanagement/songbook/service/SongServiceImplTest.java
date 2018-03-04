package pl.wasper.bandmanagement.songbook.service;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.wasper.bandmanagement.songbook.dto.SongDto;
import pl.wasper.bandmanagement.songbook.dto.mapper.SongMapper;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.helper.FakeFactory;
import pl.wasper.bandmanagement.songbook.model.Song;
import pl.wasper.bandmanagement.songbook.repository.SongRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SongServiceImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private SongRepository repository;

    @Mock
    private SongMapper mapper;

    @Test
    public void itShouldReturnSongDtosList() {
        List<Song> songs = new ArrayList<>();
        songs.add(FakeFactory.prepareSongWithId(1L, "Example song 1", 100));
        songs.add(FakeFactory.prepareSongWithId(2L, "Example song 2", 110));
        songs.add(FakeFactory.prepareSongWithId(3L, "Example song 3", 120));

        List<SongDto> songsDto = new ArrayList<>();
        songsDto.add(FakeFactory.prepareSongDtoWithId(1L, "Example song 1", 100));
        songsDto.add(FakeFactory.prepareSongDtoWithId(2L, "Example song 2", 110));
        songsDto.add(FakeFactory.prepareSongDtoWithId(3L, "Example song 3", 120));

        SongServiceImpl service = new SongServiceImpl(repository, mapper);

        when(repository.findAll()).thenReturn(songs);
        when(mapper.mapAll(songs)).thenReturn(songsDto);

        assertEquals(service.getSongs(), songsDto);
    }

    @Test
    public void itShouldReturnSingleSongById() throws ElementNotFoundException {
        Long id = 1L;
        Song exampleSong = FakeFactory.prepareSongWithId(id, "Example Song", 100);
        SongDto exampleSongDto = FakeFactory.prepareSongDtoWithId(id, "Example Song", 100);

        SongServiceImpl service = new SongServiceImpl(repository, mapper);

        when(repository.findOne(id)).thenReturn(exampleSong);
        when(mapper.map(exampleSong)).thenReturn(exampleSongDto);

        assertEquals(service.findOneById(id), exampleSongDto);
    }

    @Test(expected = ElementNotFoundException.class)
    public void itShouldThrowExceptionWhenSongWithSpecifiedDoesNotExists() throws ElementNotFoundException {
        Long id = 1L;
        SongServiceImpl service = new SongServiceImpl(repository, mapper);

        when(repository.findOne(id)).thenReturn(null);

        service.findOneById(id);
    }

    @Test
    public void itShouldAddedSong() {
        Song exampleSong = FakeFactory.prepareSong("Example Song", 100);
        SongDto exampleSongDto = FakeFactory.prepareSongDto("Example Song", 100);

        Song exampleSongAdded = FakeFactory.prepareSongWithId(1L, "Example Song", 100);
        SongDto exampleSongDtoAdded = FakeFactory.prepareSongDtoWithId(1L, "Example Song", 100);

        SongServiceImpl service = new SongServiceImpl(repository, mapper);

        when(mapper.map(exampleSongDto)).thenReturn(exampleSong);
        when(repository.save(exampleSong)).thenReturn(exampleSongAdded);
        when(mapper.map(exampleSongAdded)).thenReturn(exampleSongDtoAdded);

        assertEquals(service.save(exampleSongDto), exampleSongDtoAdded);
    }

    @Test
    public void itShouldUpdateExistingSong() throws ElementNotFoundException {
        Song exampleSong = FakeFactory.prepareSongWithId(1L, "Example song 1", 100);
        SongDto exampleSongDto = FakeFactory.prepareSongDtoWithId(1L, "Example song 1", 100);
        SongDto exampleSongDtoAfter = FakeFactory.prepareSongDtoWithId(1L, "Example song after", 100);

        SongServiceImpl service = new SongServiceImpl(repository, mapper);

        when(mapper.map(exampleSongDto)).thenReturn(exampleSong);
        when(repository.findOne(exampleSong.getId())).thenReturn(exampleSong);

        exampleSong.setTitle("Example song after");

        when(repository.save(exampleSong)).thenReturn(exampleSong);
        when(mapper.map(exampleSong)).thenReturn(exampleSongDtoAfter);

        assertEquals(service.update(exampleSongDto), exampleSongDtoAfter);
    }

    @Test(expected = ElementNotFoundException.class)
    public void itShouldThrowExceptionDuringUpdateWhenSongDoesNotExists() throws ElementNotFoundException {
        SongDto exampleSongDto = FakeFactory.prepareSongDtoWithId(1L, "Example song 2", 120);

        SongServiceImpl service = new SongServiceImpl(repository, mapper);

        when(repository.findOne(exampleSongDto.getId())).thenReturn(null);

        service.update(exampleSongDto);
    }

    @Test(expected = ElementNotFoundException.class)
    public void itShouldThrowExceptionDuringRemoveWhenSongDoesNotExists() throws ElementNotFoundException {
        SongDto exampleSongDto = FakeFactory.prepareSongDtoWithId(1L, "Example song 2", 120);

        SongServiceImpl service = new SongServiceImpl(repository, mapper);

        when(repository.findOne(exampleSongDto.getId())).thenReturn(null);

        service.delete(exampleSongDto.getId());
    }
}
