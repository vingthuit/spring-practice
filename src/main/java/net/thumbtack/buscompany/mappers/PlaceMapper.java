package net.thumbtack.buscompany.mappers;

import net.thumbtack.buscompany.models.Order;
import net.thumbtack.buscompany.services.TranslitToLatin;
import net.thumbtack.buscompany.dao.OrderDao;
import net.thumbtack.buscompany.dto.bus.PlaceDtoRequest;
import net.thumbtack.buscompany.dto.bus.PlaceDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {OrderDao.class, TranslitToLatin.class})
public abstract class PlaceMapper {
    @Autowired
    private OrderDao orderDao;

    @Mapping(target = "ticket", source = "request", qualifiedByName = "setTicket")
    @Mapping(target = "firstName", qualifiedByName = "transliterate")
    @Mapping(target = "lastName", qualifiedByName = "transliterate")
    public abstract PlaceDtoResponse toDTO(PlaceDtoRequest request);

    @Named("setTicket")
    public String setTicket(PlaceDtoRequest request) {
        Order order = orderDao.findByKey(request.getOrderId());
        int tripId = order.getTripDate().getTripId();
        return "Билет " + tripId + '_' + request.getPlace();
    }
}
