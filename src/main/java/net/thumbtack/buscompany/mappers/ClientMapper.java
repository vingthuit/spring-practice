package net.thumbtack.buscompany.mappers;

import net.thumbtack.buscompany.services.TranslitToLatin;
import net.thumbtack.buscompany.dto.user.ClientDtoRequest;
import net.thumbtack.buscompany.dto.user.ClientDtoResponse;
import net.thumbtack.buscompany.dto.user.update.ClientUpdateDtoRequest;
import net.thumbtack.buscompany.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = TranslitToLatin.class)
public interface ClientMapper {

    @Mapping(target = "firstName", qualifiedByName = "transliterate")
    @Mapping(target = "lastName", qualifiedByName = "transliterate")
    @Mapping(target = "patronymic", qualifiedByName = "transliterate")
    ClientDtoResponse toDTO(Client model);

    @Mapping(target = "phone", source = "dto.phone", qualifiedByName = "getPhone")
    @Mapping(target = "userType", constant = "CLIENT")
    Client toModel(ClientDtoRequest dto, byte[] salt);

    @Mapping(target = "phone", source = "dto.phone", qualifiedByName = "getPhone")
    @Mapping(target = "password", source = "newPassword")
    void update(ClientUpdateDtoRequest dto, @MappingTarget Client model);

    @Named("getPhone")
    default String getPhone(String phone) {
        return phone.replaceAll("[()\\-]", "");
    }
}
