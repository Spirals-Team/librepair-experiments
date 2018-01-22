package py.org.fundacionparaguaya.pspserver.security.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.security.dtos.TermCondPolDTO;
import py.org.fundacionparaguaya.pspserver.security.entities.TermCondPolEntity;

/**
 *
 * @author mgonzalez
 *
 */
@Component
public class TermCondPolMapper implements BaseMapper<TermCondPolEntity,
    TermCondPolDTO> {

    private final ModelMapper modelMapper;

    private PropertyMap<TermCondPolEntity, TermCondPolDTO> skip =
        new PropertyMap<TermCondPolEntity, TermCondPolDTO>() {
        protected void configure() {
           map(destination.getCreatedDate()).setCreatedDate(null);
        }
    };

    public TermCondPolMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        if (skip != null) {
            this.modelMapper.addMappings(skip);
        }
    }

    @Override
    public List<TermCondPolDTO> entityListToDtoList(List<TermCondPolEntity>
        entityList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TermCondPolDTO entityToDto(TermCondPolEntity entity) {

        TermCondPolDTO dto = modelMapper.map(entity, TermCondPolDTO.class);
        dto.setCreatedDate(entity.getCreatedDateAsISOString());
        return dto;
    }

    @Override
    public TermCondPolEntity dtoToEntity(TermCondPolDTO dto) {
        return modelMapper.map(dto, TermCondPolEntity.class);
    }

}
