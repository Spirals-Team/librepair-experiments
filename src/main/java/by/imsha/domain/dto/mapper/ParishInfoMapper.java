package by.imsha.domain.dto.mapper;

import by.imsha.domain.Parish;
import by.imsha.domain.dto.ParishInfo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Alena Misan
 */
@Mapper( uses = {LocationInfoMapper.class})
public interface ParishInfoMapper {
    ParishInfoMapper MAPPER = Mappers.getMapper(ParishInfoMapper.class);

    @Mappings({
     @Mapping(source = "id", target = "parishId")
    })
    ParishInfo toParishInfo(Parish parish);
}
