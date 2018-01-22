package py.org.fundacionparaguaya.pspserver.surveys.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicatorPriority;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorPriorityEntity;

@Component
public class SnapshotIndicatorPriorityMapper
        implements BaseMapper<SnapshotIndicatorPriorityEntity, SnapshotIndicatorPriority> {

    private final ModelMapper modelMapper;

    public SnapshotIndicatorPriorityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SnapshotIndicatorPriority> entityListToDtoList(List<SnapshotIndicatorPriorityEntity> entityList) {
        return entityList.stream().filter(Objects::nonNull).map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public SnapshotIndicatorPriority entityToDto(SnapshotIndicatorPriorityEntity entity) {
        SnapshotIndicatorPriority dto = modelMapper.map(entity, SnapshotIndicatorPriority.class);
        dto = dto.estimatedDate(entity.getEstimatedDateAsISOString());
        return dto;
    }

    @Override
    public SnapshotIndicatorPriorityEntity dtoToEntity(SnapshotIndicatorPriority dto) {
        SnapshotIndicatorPriorityEntity entity = modelMapper.map(dto, SnapshotIndicatorPriorityEntity.class);
        entity = entity.setEstimatedDateAsISOString(dto.getEstimatedDate());
        return entity;
    }
    
    public List<SnapshotIndicatorPriorityEntity> dtoListToEntityList(List<SnapshotIndicatorPriority> dtoList) {
        return dtoList.stream().filter(Objects::nonNull).map(this::dtoToEntity).collect(Collectors.toList());
    } 

}
