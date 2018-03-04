package pl.wasper.bandmanagement.songbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wasper.bandmanagement.songbook.dto.SongDto;
import pl.wasper.bandmanagement.songbook.dto.mapper.SongMapper;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.songbook.model.Song;
import pl.wasper.bandmanagement.songbook.repository.SongRepository;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private SongRepository songRepository;
    private SongMapper mapper;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, SongMapper mapper) {
        this.songRepository = songRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SongDto> getSongs() {
        return mapper.mapAll(songRepository.findAll());
    }

    @Override
    public SongDto findOneById(Long id) throws ElementNotFoundException {
        Song song = songRepository.findOne(id);

        if (song == null) {
            throw new ElementNotFoundException(String.format("Unable to find song with id %d", id));
        }

        return mapper.map(song);
    }

    @Override
    public SongDto save(SongDto song) {
        Song savedSong = songRepository.save(mapper.map(song));

        return mapper.map(savedSong);
    }

    @Override
    public SongDto update(SongDto song) throws ElementNotFoundException {
        if (songRepository.findOne(song.getId()) == null) {
            throw new ElementNotFoundException(String.format("Unable to find song with id %d", song.getId()));
        }

        Song savedSong = songRepository.save(mapper.map(song));

        return mapper.map(savedSong);
    }

    @Override
    public void delete(Long id) throws ElementNotFoundException {
        if (songRepository.findOne(id) == null) {
            throw new ElementNotFoundException(String.format("Unable to find song with id %d", id));
        }
        songRepository.delete(id);
    }
}
