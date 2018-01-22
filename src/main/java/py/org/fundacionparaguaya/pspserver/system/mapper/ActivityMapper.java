package py.org.fundacionparaguaya.pspserver.system.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.system.dtos.ActivityDTO;
import py.org.fundacionparaguaya.pspserver.system.entities.ActivityEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ActivityMapper implements BaseMapper<ActivityEntity, ActivityDTO>{

    private final ModelMapper modelMapper;

    public ActivityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ActivityDTO> entityListToDtoList(List<ActivityEntity> entityList) {
        return entityList.stream()
                .filter(Objects::nonNull)
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public ActivityDTO entityToDto(ActivityEntity entity) {
        ActivityDTO dto = modelMapper.map(entity, ActivityDTO.class);
        return dto;
    }

    @Override
    public ActivityEntity dtoToEntity(ActivityDTO dto) {
        return modelMapper.map(dto, ActivityEntity.class);
    }
}
