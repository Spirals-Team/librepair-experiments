package guru.bonacci.oogway.oracle.service.beanmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@Mapper
public interface GemMapper {

    GemMapper MAPPER = Mappers.getMapper( GemMapper.class );

    @Mappings({
        @Mapping(source = "saying", target = "saying"),
        @Mapping(source = "saying", target = "id"), //let's not forget...
        @Mapping(source = "author", target = "author")
    })
    Gem toGem(GemCarrier carrier);

    @Mappings({
        @Mapping(source = "saying", target = "saying"),
        @Mapping(source = "author", target = "author")
    })
    GemCarrier fromGem(Gem gem);
}
