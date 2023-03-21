package net.thumbtack.buscompany.mappersTests;

import net.thumbtack.buscompany.dao.OrderDao;
import net.thumbtack.buscompany.dao.TripDao;
import net.thumbtack.buscompany.daoImpl.OrderDaoImpl;
import net.thumbtack.buscompany.dto.OrderDtoRequest;
import net.thumbtack.buscompany.dto.OrderDtoResponse;
import net.thumbtack.buscompany.mappers.*;
import net.thumbtack.buscompany.models.Order;
import net.thumbtack.buscompany.services.TranslitToLatin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static net.thumbtack.buscompany.InstancesForTests.getOrderResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = {
        PassengerMapperImpl.class,
        OrderMapperImpl.class,
        OrderDaoImpl.class,
        TranslitToLatin.class})
class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;
    @MockBean
    private OrderDao orderDao;
    @MockBean
    private TripDao tripDao;

    @Test
    public void testOrderMapping() {
        OrderDtoRequest dtoRequest = getOrderRequest(1, 1);
        Order order = orderMapper.toModel(1, getTripDate(1), dtoRequest, BigDecimal.TEN);
        order.setId(1);
        order.getPassengers().get(0).setId(1);
        assertEquals(getOrder(), order);

        doReturn(getTrip()).when(tripDao).findByKey(1);
        OrderDtoResponse response = orderMapper.toDTO(order, getTripDate(1));
        assertEquals(getOrderResponse(1, 1, 1), response);
    }
}
