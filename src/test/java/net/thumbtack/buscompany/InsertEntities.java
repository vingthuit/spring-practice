package net.thumbtack.buscompany;

import net.thumbtack.buscompany.dto.OrderDtoRequest;
import net.thumbtack.buscompany.dto.OrderDtoResponse;
import net.thumbtack.buscompany.dto.bus.PlaceDtoRequest;
import net.thumbtack.buscompany.dto.bus.PlaceDtoResponse;
import net.thumbtack.buscompany.dto.trip.TripAdminDtoResponse;
import net.thumbtack.buscompany.dto.trip.TripDtoRequest;
import net.thumbtack.buscompany.dto.user.UserDtoRequest;
import net.thumbtack.buscompany.dto.user.UserDtoResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static net.thumbtack.buscompany.InstancesForTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InsertEntities {

    private static final RestTemplate template = new RestTemplate();
    private static final String url = "http://localhost:8888/api/";
    private static int tripId;
    private static int tripId2;

    public static HttpHeaders registerUser(String userType, UserDtoRequest user) {
        HttpEntity<UserDtoRequest> request = new HttpEntity<>(user);
        HttpEntity<UserDtoResponse> registration = template.exchange(url + userType, HttpMethod.POST, request, UserDtoResponse.class);
        String cookie = Objects.requireNonNull(registration.getHeaders().get(HttpHeaders.SET_COOKIE)).stream()
                .filter(c -> c.contains("JAVASESSIONID")).findAny().orElse(null);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        return headers;
    }

    public static int insertTrip(HttpHeaders headers, TripDtoRequest request) {
        HttpEntity<TripDtoRequest> trip = new HttpEntity<>(request, headers);
        ResponseEntity<TripAdminDtoResponse> response = template.exchange(url + "trips",
                HttpMethod.POST, trip, TripAdminDtoResponse.class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        return response.getBody().getId();
    }

    public static void insertApprovedTrips() {
        HttpHeaders headers = registerUser("admins", getAdminRequest());
        tripId = insertTrip(headers, getTripRequest());
        tripId2 = insertTrip(headers, getTripRequest2());
        template.exchange(url + "trips/" + tripId + "/approve", HttpMethod.PUT,
                new HttpEntity<String>(headers), TripAdminDtoResponse.class);
        template.exchange(url + "trips/" + tripId2 + "/approve", HttpMethod.PUT,
                new HttpEntity<String>(headers), TripAdminDtoResponse.class);
    }

    public static int getTripId() {
        return tripId;
    }

    public static int getTripId2() {
        return tripId2;
    }

    public static OrderDtoResponse insertOrder(HttpHeaders headers, OrderDtoRequest orderRequest) {
        ResponseEntity<OrderDtoResponse> response = template.exchange(url + "orders", HttpMethod.POST,
                new HttpEntity<>(orderRequest, headers), OrderDtoResponse.class);
        int orderId = Objects.requireNonNull(response.getBody()).getOrderId();
        assertEquals(200, response.getStatusCode().value());
        OrderDtoResponse orderResponse = getOrderResponse(orderId, orderRequest.getTripId(), orderRequest.getPassengers().size());
        assertEquals(orderResponse, response.getBody());
        return orderResponse;
    }

    public static OrderDtoResponse insertOrder2(HttpHeaders headers, OrderDtoRequest orderRequest) {
        ResponseEntity<OrderDtoResponse> response = template.exchange(url + "orders", HttpMethod.POST,
                new HttpEntity<>(orderRequest, headers), OrderDtoResponse.class);
        int orderId = Objects.requireNonNull(response.getBody()).getOrderId();
        assertEquals(200, response.getStatusCode().value());
        OrderDtoResponse orderResponse = getOrderResponse2(orderId, orderRequest.getTripId(), orderRequest.getPassengers().size());
        assertEquals(orderResponse, response.getBody());
        return orderResponse;
    }

    public static void insertPlace(HttpHeaders headers, OrderDtoResponse order, int place) {
        PlaceDtoRequest request = getPlaceRequest(order.getOrderId(), place);
        ResponseEntity<PlaceDtoResponse> response = template.exchange(url + "places", HttpMethod.POST,
                new HttpEntity<>(request, headers), PlaceDtoResponse.class);
        assertEquals(200, response.getStatusCode().value());
        PlaceDtoResponse placeResponse = getPlaceResponse(order.getOrderId(), order.getTripId(), place);
        assertEquals(placeResponse, response.getBody());
    }
}
