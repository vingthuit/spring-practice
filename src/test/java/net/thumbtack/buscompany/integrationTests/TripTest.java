package net.thumbtack.buscompany.integrationTests;

import net.thumbtack.buscompany.dto.trip.TripAdminDtoResponse;
import net.thumbtack.buscompany.dto.trip.TripClientDtoResponse;
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

import java.util.List;
import java.util.Objects;

import static net.thumbtack.buscompany.InsertEntities.*;
import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TripTest {
    private static final RestTemplate template = new RestTemplate();
    private static final String url = "http://localhost:8888/api/";
    private HttpHeaders headers;

    @BeforeAll
    @AfterEach
    public void clearDataBase() {
        template.delete(url + "debug/clear");
    }

    @Test
    void getDeleteTest() {
        headers = registerUser("admins", getAdminRequest());
        int tripId = insertTrip(headers, getTripRequest());
        ResponseEntity<TripAdminDtoResponse> getResponse = template.exchange(url + "trips/" + tripId, HttpMethod.GET,
                new HttpEntity<String>(headers), TripAdminDtoResponse.class);
        assertEquals(200, getResponse.getStatusCode().value());
        assertNotNull(getResponse.getBody());
        assertEquals(getTripResponse(tripId), getResponse.getBody());

        ResponseEntity<TripAdminDtoResponse> deleteResponse = template.exchange(url + "trips/" + tripId, HttpMethod.DELETE,
                new HttpEntity<String>(headers), TripAdminDtoResponse.class);
        assertEquals(200, deleteResponse.getStatusCode().value());

        try {
            template.exchange(url + "trips/" + tripId, HttpMethod.GET,
                    new HttpEntity<String>(headers), TripAdminDtoResponse.class);
            fail();
        } catch (HttpClientErrorException exc) {
            assertEquals(400, exc.getStatusCode().value());
            assertTrue(Objects.requireNonNull(exc.getMessage()).contains(ErrorCode.NONEXISTENT_TRIP_ID.name()));
        }
    }

    @Test
    void approveTest() {
        headers = registerUser("admins", getAdminRequest());
        int tripId = insertTrip(headers, getTripRequest());
        ResponseEntity<TripAdminDtoResponse> response = template.exchange(url + "trips/" + tripId + "/approve", HttpMethod.PUT,
                new HttpEntity<String>(headers), TripAdminDtoResponse.class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        TripAdminDtoResponse tripResponse = getTripResponse(tripId);
        tripResponse.setApproved(true);
        assertEquals(tripResponse, response.getBody());
    }

    @Test
    void getAdminTripListTest() {
        headers = registerUser("admins", getAdminRequest());
        int tripId = insertTrip(headers, getTripRequest());
        insertTrip(headers, getTripRequest2());

        template.exchange(url + "trips/" + tripId + "/approve", HttpMethod.PUT,
                new HttpEntity<String>(headers), TripAdminDtoResponse.class);

        ResponseEntity<List<TripAdminDtoResponse>> adminTripList = template.exchange(url + "trips", HttpMethod.GET,
                new HttpEntity<String>(headers), new ParameterizedTypeReference<>() {});
        assertEquals(200, adminTripList.getStatusCode().value());
        assertEquals(2, Objects.requireNonNull(adminTripList.getBody()).size());

        headers = registerUser("clients", getClientRequest());
        ResponseEntity<List<TripClientDtoResponse>> clientTripList = template.exchange(url + "trips", HttpMethod.GET,
                new HttpEntity<String>(headers), new ParameterizedTypeReference<>() {});
        assertEquals(200, clientTripList.getStatusCode().value());
        assertEquals(1, Objects.requireNonNull(clientTripList.getBody()).size());
    }

}
