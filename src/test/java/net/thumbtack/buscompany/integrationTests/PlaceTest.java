package net.thumbtack.buscompany.integrationTests;

import net.thumbtack.buscompany.dto.OrderDtoRequest;
import net.thumbtack.buscompany.dto.OrderDtoResponse;
import net.thumbtack.buscompany.dto.bus.PlaceDtoRequest;
import net.thumbtack.buscompany.dto.bus.PlaceDtoResponse;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.thumbtack.buscompany.InsertEntities.*;
import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PlaceTest {
    private static final RestTemplate template = new RestTemplate();
    private static final String url = "http://localhost:8888/api/";
    private HttpHeaders headers;

    @BeforeAll
    @AfterEach
    public void clearDataBase() {
        template.delete(url + "debug/clear");
    }

    @Test
    public void placeTest() {
        insertApprovedTrips();
        headers = registerUser("clients", getClientRequest());

        OrderDtoRequest orderRequest = getOrderRequest(getTripId(), 1);
        OrderDtoResponse response = insertOrder(headers, orderRequest);

        getFreePlacesTest(response.getOrderId(), 0);
        insertPlace(headers, response, 1);
        getFreePlacesTest(response.getOrderId(), 1);
    }

    private void getFreePlacesTest(int orderId, int takenPlace) {
        ResponseEntity<List<Integer>> response = template.exchange(url + "places/" + orderId, HttpMethod.GET,
                new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
        assertEquals(200, response.getStatusCode().value());
        List<Integer> places = new ArrayList<>() {{
            for (int i = 1; i <= 10; i++) {
                if (i != takenPlace){
                    add(i);
                }
            }
        }};
        assertEquals(places, response.getBody());
    }

    @Test
    public void takeAnotherPlaceTest() {
        insertApprovedTrips();
        headers = registerUser("clients", getClientRequest());

        OrderDtoRequest orderRequest = getOrderRequest(getTripId(), 1);
        OrderDtoResponse response = insertOrder(headers, orderRequest);

        insertPlace(headers, response, 1);

        PlaceDtoRequest request = getPlaceRequest(response.getOrderId(), 1);
        request.setPlace(2);
        template.exchange(url + "places", HttpMethod.POST, new HttpEntity<>(request, headers), PlaceDtoResponse.class);

        getFreePlacesTest(response.getOrderId(), 2);
    }

    @Test
    public void takenPlaceTest() {
        insertApprovedTrips();
        headers = registerUser("clients", getClientRequest());

        OrderDtoRequest orderRequest = getOrderRequest(getTripId(), 2);
        OrderDtoResponse response = insertOrder(headers, orderRequest);

        insertPlace(headers, response, 1);
        PlaceDtoRequest request = getPlaceRequest(response.getOrderId(), 1);
        request.setPassport("2");
        try {
            template.exchange(url + "places", HttpMethod.POST, new HttpEntity<>(request, headers), PlaceDtoResponse.class);
            fail();
        }
        catch (HttpClientErrorException exc) {
            assertEquals(400, exc.getStatusCode().value());
            assertTrue(Objects.requireNonNull(exc.getMessage()).contains(ErrorCode.PLACE_TAKEN.name()));
        }
    }

}
