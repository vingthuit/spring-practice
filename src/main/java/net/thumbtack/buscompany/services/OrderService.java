package net.thumbtack.buscompany.services;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dao.OrderDao;
import net.thumbtack.buscompany.dao.TripDao;
import net.thumbtack.buscompany.dto.OrderDtoRequest;
import net.thumbtack.buscompany.dto.OrderDtoResponse;
import net.thumbtack.buscompany.mappers.OrderMapper;
import net.thumbtack.buscompany.models.*;
import net.thumbtack.buscompany.security.validators.AccessValidator;
import net.thumbtack.buscompany.security.validators.OrderValidator;
import net.thumbtack.buscompany.security.validators.TripValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {
    private final AccessValidator accessValidator;
    private final TripValidator tripValidator;
    private final OrderValidator orderValidator;

    private final OrderDao orderDao;
    private final TripDao tripDao;
    private final OrderMapper orderMapper;
    private final TripService tripService;

    public OrderDtoResponse orderTicket(String cookie, OrderDtoRequest request) {
        Client client = accessValidator.isClient(cookie);
        tripValidator.isApproved(request.getTripId());
        TripDate tripDate = tripValidator.isValid(request.getTripId(), request.getDate());

        Order order = orderMapper.toModel(client.getUserId(), tripDate, request, getTotalPrice(request));
        orderDao.insert(order);
        return orderMapper.toDTO(order, tripDate);
    }

    public List<OrderDtoResponse> getOrderList(String cookie, Map<String, String> allParams) {
        User user = accessValidator.isValid(cookie);

        List<Order> orderList = orderDao.findAll();
        if (user.getUserType().equals(UserType.CLIENT)) {
            orderList = orderList.stream()
                    .filter(order -> order.getClientId() == user.getUserId()).collect(Collectors.toList());
        }
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            orderList = orderList.stream().filter(order -> {
                int tripId = order.getTripDate().getTripId();
                Trip trip = tripDao.findByKey(tripId);
                return tripService.getByParam(trip, entry.getKey(), entry.getValue());
            }).collect(Collectors.toList());
        }
        return orderList.stream().map(order -> orderMapper.toDTO(order, order.getTripDate())).collect(Collectors.toList());
    }

    public void deleteOrder(String cookie, int orderId) {
        accessValidator.isClient(cookie);
        orderValidator.isValid(orderId);
        orderDao.delete(orderId);
    }

    private BigDecimal getTotalPrice(OrderDtoRequest request) {
        BigDecimal price = tripDao.findByKey(request.getTripId()).getPrice();
        return price.multiply(BigDecimal.valueOf(request.getPassengers().size()));
    }

}
