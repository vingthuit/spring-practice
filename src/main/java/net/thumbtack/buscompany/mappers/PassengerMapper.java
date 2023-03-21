package net.thumbtack.buscompany.mappers;

import net.thumbtack.buscompany.dto.PassengerDto;
import net.thumbtack.buscompany.models.Passenger;
import net.thumbtack.buscompany.services.TranslitToLatin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TranslitToLatin.class})
public interface PassengerMapper {

    List<PassengerDto> map(List<Passenger> passengers);

    @Mapping(target = "firstName", qualifiedByName = "transliterate")
    @Mapping(target = "lastName", qualifiedByName = "transliterate")
    @Mapping(target = "passport", qualifiedByName = "transliterate")
    PassengerDto toDTO(Passenger passenger);

}
