package net.thumbtack.buscompany.security.validators;

import net.thumbtack.buscompany.dao.OrderDao;
import net.thumbtack.buscompany.dto.bus.PlaceDtoRequest;
import net.thumbtack.buscompany.models.Passenger;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import net.thumbtack.buscompany.security.exceptions.OrderException;
import net.thumbtack.buscompany.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {
    @Autowired
    private OrderDao orderDao;

    public Order isValid(int orderId) {
        Order order = orderDao.findByKey(orderId);
        if (order == null) {
            throw new OrderException(ErrorCode.NONEXISTENT_ORDER_ID);
        }
        return order;
    }

    public int placeRequestIsValid(Order order, PlaceDtoRequest request) {
        Passenger passenger = order.getPassengers().stream().filter(p -> p.getPassport().equals(request.getPassport()))
                .findAny().orElse(null);
        if (passenger == null || !passenger.getFirstName().equals(request.getFirstName())
                || !passenger.getLastName().equals(request.getLastName())) {
            throw new OrderException(ErrorCode.WRONG_PASSENGER);
        }
        return passenger.getId();
    }

}
