package pl.wasper.bandmanagement.songbook.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SongDto {
    private Long id;
    private String title;
    private int tempo;
    private String tone;
    private String info;
    private String content;
    private List<TagDto> tags;
}
