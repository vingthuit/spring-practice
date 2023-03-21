package net.thumbtack.buscompany.mappers;

import net.thumbtack.buscompany.services.TranslitToLatin;
import net.thumbtack.buscompany.dto.BusDtoResponse;
import net.thumbtack.buscompany.models.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TranslitToLatin.class})
public abstract class BusMapper {

    @Mapping(target = "busName", source = "model.name", qualifiedByName = "transliterate")
    public abstract BusDtoResponse toDTO(Bus model);

}
