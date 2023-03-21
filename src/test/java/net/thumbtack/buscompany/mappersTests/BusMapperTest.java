package net.thumbtack.buscompany.mappersTests;

import net.thumbtack.buscompany.dao.BusDao;
import net.thumbtack.buscompany.dao.OrderDao;
import net.thumbtack.buscompany.daoImpl.BusDaoImpl;
import net.thumbtack.buscompany.daoImpl.OrderDaoImpl;
import net.thumbtack.buscompany.dto.BusDtoResponse;
import net.thumbtack.buscompany.dto.PassengerDto;
import net.thumbtack.buscompany.dto.bus.PlaceDtoRequest;
import net.thumbtack.buscompany.dto.bus.PlaceDtoResponse;
import net.thumbtack.buscompany.mappers.*;
import net.thumbtack.buscompany.models.*;
import net.thumbtack.buscompany.services.TranslitToLatin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = {
        BusMapperImpl.class,
        PassengerMapperImpl.class,
        PlaceMapperImpl.class,
        OrderDaoImpl.class,
        BusDaoImpl.class,
        TranslitToLatin.class})
class BusMapperTest {
    @Autowired
    private BusMapper busMapper;
    @Autowired
    private PassengerMapper passengerMapper;
    @Autowired
    private PlaceMapper placeMapper;
    @MockBean
    private OrderDao orderDao;
    @MockBean
    private BusDao busDao;

    @Test
    public void testBusMapping() {
        Bus bus = new Bus("автобус", 1);
        BusDtoResponse response = busMapper.toDTO(bus);
        assertEquals(new BusDtoResponse("avtobus", 1), response);
    }

    @Test
    public void testPassengerMapping() {
        Passenger passenger = getPassengers(1).get(0);
        PassengerDto passengerDto = passengerMapper.toDTO(passenger);
        assertEquals(getPassengersResponse(1).get(0), passengerDto);

        List<Passenger> passengers = new ArrayList<>() {{
            add(passenger);
            add(passenger);
        }};
        List<PassengerDto> response = passengerMapper.map(passengers);
        assertEquals(2, response.size());
    }

    @Test
    public void testPlaceMapper() {
        doReturn(getOrder()).when(orderDao).findByKey(1);

        PlaceDtoRequest dtoRequest = getPlaceRequest(1, 1);
        PlaceDtoResponse dtoResponse = placeMapper.toDTO(dtoRequest);
        assertEquals(getPlaceResponse(1, 1, 1), dtoResponse);
    }

}
