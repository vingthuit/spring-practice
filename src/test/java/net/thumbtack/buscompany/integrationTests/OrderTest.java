package net.thumbtack.buscompany.integrationTests;

import net.thumbtack.buscompany.dto.OrderDtoRequest;
import net.thumbtack.buscompany.dto.OrderDtoResponse;
import net.thumbtack.buscompany.security.exceptions.ErrorCode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static net.thumbtack.buscompany.InsertEntities.*;
import static net.thumbtack.buscompany.InstancesForTests.*;
import static net.thumbtack.buscompany.InsertEntities.registerUser;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTest {
    private static final RestTemplate template = new RestTemplate();
    private static final String url = "http://localhost:8888/api/";
    private HttpHeaders headers;

    @BeforeAll
    @AfterEach
    void clearDataBase() {
        template.delete(url + "debug/clear");
    }

    @Test
    void orderListTest() {
        insertApprovedTrips();
        headers = registerUser("clients", getClientRequest());

        insertOrder(headers, getOrderRequest(getTripId(), 1));
        insertOrder2(headers, getOrderRequest2(getTripId2(), 1));

        Map<String, String> params = new HashMap<>();
        params.put("fromDate", "2022-11-30");
        requestWithParams(params, 2);

        params = new HashMap<>();
        params.put("toDate", "2022-12-01");
        requestWithParams(params, 1);
    }

    @Test
    void insertOrderFailTest() {
        insertApprovedTrips();
        headers = registerUser("clients", getClientRequest());

        OrderDtoRequest request = getOrderRequest2(getTripId2(), 5);
        OrderDtoResponse order = insertOrder2(headers, request);
        for (int i = 1; i <= 5; i++) {
            insertPlace(headers, order, i);
        }

        try {
            template.exchange(url + "orders", HttpMethod.POST,
                    new HttpEntity<>(getOrderRequest2(getTripId2(), 1), headers), OrderDtoResponse.class);
            fail();
        } catch (HttpClientErrorException exc) {
            assertEquals(400, exc.getStatusCode().value());
            assertTrue(Objects.requireNonNull(exc.getMessage()).contains(ErrorCode.NO_FREE_PLACES.name()));
        }
    }

    private void requestWithParams(Map<String, String> params, int expected) {
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(url + "orders");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            componentsBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        String paramsUrl = componentsBuilder.encode().toUriString();
        ResponseEntity<List<OrderDtoResponse>> orderList = template.exchange(paramsUrl, HttpMethod.GET,
                new HttpEntity<>(headers), new ParameterizedTypeReference<>() {}, params);
        assertEquals(200, orderList.getStatusCode().value());
        assertEquals(expected, Objects.requireNonNull(orderList.getBody()).size());
    }

}
