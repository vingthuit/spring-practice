package net.thumbtack.buscompany.mappers;

import net.thumbtack.buscompany.services.TranslitToLatin;
import net.thumbtack.buscompany.dto.user.AdminDtoResponse;
import net.thumbtack.buscompany.dto.user.update.AdminUpdateDtoRequest;
import net.thumbtack.buscompany.dto.user.AdminDtoRequest;
import net.thumbtack.buscompany.models.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TranslitToLatin.class})
public interface AdminMapper {
    @Mapping(target = "userType", constant = "ADMIN")
    Admin toModel(AdminDtoRequest dto, byte[] salt);

    @Mapping(target = "firstName", qualifiedByName = "transliterate")
    @Mapping(target = "lastName", qualifiedByName = "transliterate")
    @Mapping(target = "patronymic", qualifiedByName = "transliterate")
    @Mapping(target = "position", qualifiedByName = "transliterate")
    @Mapping(target = "userType", expression = "java(model.getUserType().name().toLowerCase())")
    AdminDtoResponse toDTO(Admin model);

    @Mapping(target = "password", source = "newPassword")
    void update(AdminUpdateDtoRequest dto, @MappingTarget Admin model);

}
