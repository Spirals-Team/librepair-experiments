package pl.wasper.bandmanagement.songbook.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagDto {
    private Long id;
    private String name;
    private List<SongDto> songs;
}
