package net.thumbtack.buscompany.mappers;

import net.thumbtack.buscompany.models.SessionAttributes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.servlet.http.HttpSession;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    @Mapping(target = "userId", expression = "java(Integer.parseInt(dto.getAttribute(\"userId\").toString()))")
    SessionAttributes toModel(HttpSession dto);

}
