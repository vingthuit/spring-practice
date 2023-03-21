package net.thumbtack.buscompany.services;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dao.PlaceDao;
import net.thumbtack.buscompany.dto.bus.PlaceDtoRequest;
import net.thumbtack.buscompany.dto.bus.PlaceDtoResponse;
import net.thumbtack.buscompany.mappers.PlaceMapper;
import net.thumbtack.buscompany.models.Order;
import net.thumbtack.buscompany.models.BusPlace;
import net.thumbtack.buscompany.security.validators.AccessValidator;
import net.thumbtack.buscompany.security.validators.OrderValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PlaceService {
    private AccessValidator accessValidator;
    private OrderValidator orderValidator;
    private final PlaceDao placeDao;
    private final PlaceMapper placeMapper;

    public List<Integer> getFreePlaces(String cookie, int orderId) {
        accessValidator.isClient(cookie);
        Order order = orderValidator.isValid(orderId);
        return order.getTripDate().getPlaces().stream().filter(place -> place.getPassengerId() == 0)
                .map(BusPlace::getPlace).collect(Collectors.toList());
    }

    public PlaceDtoResponse takePlace(String cookie, PlaceDtoRequest request) {
        accessValidator.isClient(cookie);
        Order order = orderValidator.isValid(request.getOrderId());
        int passengerId = orderValidator.placeRequestIsValid(order, request);

        BusPlace busPlace = new BusPlace(order.getTripDate().getId(), request.getPlace(), passengerId);
        placeDao.takePlace(busPlace);
        return placeMapper.toDTO(request);
    }

}
