package net.thumbtack.buscompany.mappers;

import net.thumbtack.buscompany.models.Trip;
import net.thumbtack.buscompany.models.TripDate;
import net.thumbtack.buscompany.services.OrderService;
import net.thumbtack.buscompany.services.TranslitToLatin;
import net.thumbtack.buscompany.dao.TripDao;
import net.thumbtack.buscompany.dto.OrderDtoRequest;
import net.thumbtack.buscompany.dto.OrderDtoResponse;
import net.thumbtack.buscompany.models.Order;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {TripDao.class, OrderService.class, TranslitToLatin.class, PassengerMapper.class})
public abstract class OrderMapper {

    @Autowired
    private TripDao tripDao;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tripDate", source = "tripDate")
    public abstract Order toModel(Integer clientId, TripDate tripDate, OrderDtoRequest dto, BigDecimal totalPrice);

    @Mapping(target = "orderId", source = "model.id")
    @Mapping(target = "date", source = "tripDate.date")
    public abstract OrderDtoResponse toDTO(Order model, TripDate tripDate);

    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "fromStation", qualifiedByName = "transliterate")
    @Mapping(target = "toStation", qualifiedByName = "transliterate")
    @Mapping(target = "busName", source = "trip.bus.name", qualifiedByName = "transliterate")
    public abstract OrderDtoResponse toDTO(Trip trip, @MappingTarget OrderDtoResponse response);

    @AfterMapping
    public void getTripId(TripDate tripDate, @MappingTarget OrderDtoResponse response) {
        Trip trip = tripDao.findByKey(tripDate.getTripId());
        toDTO(trip, response);
    }
}