package pl.wasper.bandmanagement.songbook.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.wasper.bandmanagement.songbook.dto.SongDto;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.songbook.service.SongService;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SongDto> songList() {
        return songService.getSongs();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SongDto addSong(@RequestBody SongDto song) {
        return songService.save(song);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SongDto findSong(@PathVariable Long id) throws ElementNotFoundException {
        return songService.findOneById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SongDto updateSong(@RequestBody SongDto song) throws ElementNotFoundException {
        return songService.update(song);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeSong(@PathVariable Long id) throws ElementNotFoundException {
        songService.delete(id);
    }
}
