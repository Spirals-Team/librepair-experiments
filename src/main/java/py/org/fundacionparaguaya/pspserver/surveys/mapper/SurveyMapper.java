package py.org.fundacionparaguaya.pspserver.surveys.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyDefinition;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by rodrigovillalba on 10/30/17.
 */
@Component
public class SurveyMapper implements BaseMapper<SurveyEntity, SurveyDefinition> {

    private final ModelMapper modelMapper;

    public SurveyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SurveyDefinition> entityListToDtoList(List<SurveyEntity> entityList) {
        return entityList.stream()
                .filter(Objects::nonNull)
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SurveyDefinition entityToDto(SurveyEntity entity) {
        SurveyDefinition dto = modelMapper.map(entity, SurveyDefinition.class);
        return dto;
    }

    @Override
    public SurveyEntity dtoToEntity(SurveyDefinition dto) {
        return null;
    }
}
