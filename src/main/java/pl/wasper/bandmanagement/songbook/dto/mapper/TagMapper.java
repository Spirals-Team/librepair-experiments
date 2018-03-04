package pl.wasper.bandmanagement.songbook.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wasper.bandmanagement.songbook.dto.TagDto;
import pl.wasper.bandmanagement.songbook.model.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper {
    private ModelMapper modelMapper;

    @Autowired
    public TagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TagDto map(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }

    public Tag map(TagDto tag) {
        return modelMapper.map(tag, Tag.class);
    }

    public List<TagDto> mapAll(List<Tag> tags) {
        return tags.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<Tag> mapAllDto(List<TagDto> tagDtos) {
        return tagDtos.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
