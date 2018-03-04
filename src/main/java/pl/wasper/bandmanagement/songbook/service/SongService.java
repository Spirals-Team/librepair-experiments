package pl.wasper.bandmanagement.songbook.service;

import pl.wasper.bandmanagement.songbook.dto.SongDto;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;

import java.util.List;

public interface SongService {
    List<SongDto> getSongs();
    SongDto findOneById(Long id) throws ElementNotFoundException;
    SongDto save(SongDto song);
    SongDto update(SongDto song) throws ElementNotFoundException;
    void delete(Long id) throws ElementNotFoundException;
}
