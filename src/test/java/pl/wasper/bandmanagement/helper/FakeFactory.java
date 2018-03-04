package pl.wasper.bandmanagement.helper;

import pl.wasper.bandmanagement.event.dto.EventDto;
import pl.wasper.bandmanagement.event.model.Event;
import pl.wasper.bandmanagement.songbook.dto.SongDto;
import pl.wasper.bandmanagement.songbook.dto.TagDto;
import pl.wasper.bandmanagement.songbook.model.Song;
import pl.wasper.bandmanagement.songbook.model.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FakeFactory {
    public static Song prepareSong(String title, int tempo) {
        Song exampleSong = new Song();
        exampleSong.setTitle(title);
        exampleSong.setTempo(tempo);

        return exampleSong;
    }

    public static SongDto prepareSongDto(String title, int tempo) {
        SongDto exampleSongDto = new SongDto();
        exampleSongDto.setTitle(title);
        exampleSongDto.setTempo(tempo);

        return exampleSongDto;
    }

    public static Song prepareSongWithId(Long id, String title, int tempo) {
        Song song = prepareSong(title, tempo);
        song.setId(id);

        return song;
    }

    public static SongDto prepareSongDtoWithId(Long id, String title, int tempo) {
        SongDto songDto = prepareSongDto(title, tempo);
        songDto.setId(id);

        return songDto;
    }

    public static Tag prepareTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);

        return tag;
    }

    public static TagDto prepareTagDto(String name) {
        TagDto tagDto = new TagDto();
        tagDto.setName(name);

        return tagDto;
    }

    public static Tag prepareTagWithId(Long id, String name) {
        Tag tag = prepareTag(name);
        tag.setId(id);

        return tag;
    }

    public static TagDto prepareTagDtoWithId(Long id, String name) {
        TagDto tagDto = prepareTagDto(name);
        tagDto.setId(id);

        return tagDto;
    }

    public static Event prepareEvent(LocalDateTime date, String place, BigDecimal price) {
        Event event = new Event();
        event.setDate(date);
        event.setPlace(place);
        event.setPrice(price);

        return event;
    }

    public static Event prepareEventWithId(Long id, LocalDateTime date, String place, BigDecimal price) {
        Event event = new Event();
        event.setId(id);
        event.setDate(date);
        event.setPlace(place);
        event.setPrice(price);

        return event;
    }

    public static EventDto prepareEventDto(LocalDateTime date, String place, BigDecimal price) {
        EventDto eventDto = new EventDto();
        eventDto.setDate(date);
        eventDto.setPlace(place);
        eventDto.setPrice(price);

        return eventDto;
    }

    public static EventDto prepareEventDtoWithId(Long id, LocalDateTime date, String place, BigDecimal price) {
        EventDto eventDto = new EventDto();
        eventDto.setId(id);
        eventDto.setDate(date);
        eventDto.setPlace(place);
        eventDto.setPrice(price);

        return eventDto;
    }
}
