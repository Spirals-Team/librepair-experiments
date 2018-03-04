package pl.wasper.bandmanagement.songbook.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wasper.bandmanagement.songbook.dto.SongDto;
import pl.wasper.bandmanagement.songbook.model.Song;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SongMapper {
    private ModelMapper mapper;

    @Autowired
    public SongMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public SongDto map(Song song) {
        return mapper.map(song, SongDto.class);
    }

    public Song map(SongDto songDto) {
        return mapper.map(songDto, Song.class);
    }

    public List<SongDto> mapAll(List<Song> songs) {
        return songs.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<Song> mapAllDto(List<SongDto> songDtos) {
        return songDtos.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
