package net.thumbtack.buscompany;

import net.thumbtack.buscompany.dto.*;
import net.thumbtack.buscompany.dto.bus.PlaceDtoRequest;
import net.thumbtack.buscompany.dto.bus.PlaceDtoResponse;
import net.thumbtack.buscompany.dto.trip.ScheduleDtoRequest;
import net.thumbtack.buscompany.dto.trip.TripAdminDtoResponse;
import net.thumbtack.buscompany.dto.trip.TripDtoRequest;
import net.thumbtack.buscompany.dto.user.*;
import net.thumbtack.buscompany.models.*;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InstancesForTests {
    public static AdminDtoRequest getAdminRequest() {
        return new AdminDtoRequest("Админ", "Админов", "Админович",
                "alogin", "пароль123", "главный");
    }

    public static Admin getAdmin() {
        return new Admin(1, "Админ", "Админов", "Админович",
                UserType.ADMIN, "alogin", "пароль123", new byte[]{4, 15, 2}, "главный");
    }

    public static AdminDtoResponse getAdminResponse() {
        return new AdminDtoResponse(1, "Admin", "Adminov", "Adminovich",
                "admin", "glavnyy");
    }

    public static ClientDtoRequest getClientRequest() {
        return new ClientDtoRequest("Клиент", "Клиентов", "Клиентович",
                "Clogin", "пароль123", "client@mail.ru", "8-900-800-8008");
    }

    public static Client getClient() {
        return new Client(2, "Клиент", "Клиентов", "Клиентович", UserType.CLIENT,
                "Clogin", "пароль123", new byte[]{1, 2, 3}, "client@mail.ru", "89008008008");
    }

    public static ClientDtoResponse getClientResponse() {
        return new ClientDtoResponse(2, "Kliyent", "Kliyentov", "Kliyentovich",
                "CLIENT", "client@mail.ru", "89008008008");
    }

    public static LoginDtoRequest getLoginAdminRequest() {
        return new LoginDtoRequest(getAdminRequest().getLogin(), getAdminRequest().getPassword());
    }

    public static LoginDtoRequest getLoginClientRequest() {
        return new LoginDtoRequest(getClientRequest().getLogin(), getClientRequest().getPassword());
    }

    public static TripDtoRequest getTripRequest() {
        return new TripDtoRequest("марка автобуса", "начальная станция", "конечная станция",
                LocalTime.parse("12:22"), LocalTime.parse("18:22"), BigDecimal.TEN,
                Collections.singletonList(LocalDate.parse("2022-11-30")));
    }

    public static TripDtoRequest getTripRequest2() {
        return new TripDtoRequest("марка другого автобуса", "начальная станция", "конечная станция",
                LocalTime.parse("12:22"), LocalTime.parse("18:22"), BigDecimal.TEN, getScheduleDtoRequest());
    }

    public static Trip getTrip() {
        return new Trip(1, "начальная станция", "конечная станция",
                LocalTime.parse("12:22"), LocalTime.parse("18:22"), BigDecimal.TEN, getBus(),
                false, null, Collections.singletonList(getTripDate(1)));
    }

    public static Trip getTrip2() {
        return new Trip(2, "начальная станция", "конечная станция",
                LocalTime.parse("12:22"), LocalTime.parse("18:22"), BigDecimal.TEN,
                getBus2(), false, getSchedule(), getDates(2));
    }

    private static ScheduleDtoRequest getScheduleDtoRequest() {
        return new ScheduleDtoRequest(LocalDate.parse("2022-11-28"), LocalDate.parse("2022-12-02"), "daily");
    }

    private static Schedule getSchedule() {
        return new Schedule(LocalDate.parse("2022-11-28"), LocalDate.parse("2022-12-02"), "daily");
    }

    public static List<TripDate> getDates(int tripId) {
        return new ArrayList<>() {{
            add(new TripDate(1, tripId, LocalDate.parse("2022-11-28"), getTripResponse(tripId).getBus().getPlaceCount(), null));
            add(new TripDate(2, tripId, LocalDate.parse("2022-11-29"), getTripResponse(tripId).getBus().getPlaceCount(), null));
            add(new TripDate(3, tripId, LocalDate.parse("2022-11-30"), getTripResponse(tripId).getBus().getPlaceCount(), null));
            add(new TripDate(4, tripId, LocalDate.parse("2022-12-01"), getTripResponse(tripId).getBus().getPlaceCount(), null));
            add(new TripDate(5, tripId, LocalDate.parse("2022-12-02"), getTripResponse(tripId).getBus().getPlaceCount(), null));
        }};
    }

    private static List<LocalDate> getResponseDates() {
        return new ArrayList<>() {{
            add(LocalDate.parse("2022-11-28"));
            add(LocalDate.parse("2022-11-29"));
            add(LocalDate.parse("2022-11-30"));
            add(LocalDate.parse("2022-12-01"));
            add(LocalDate.parse("2022-12-02"));
        }};
    }

    public static Bus getBus() {
        return new Bus("марка автобуса", 10);
    }

    public static Bus getBus2() {
        return new Bus("марка другого автобуса", 5);
    }

    public static BusDtoResponse getBusDtoResponse() {
        return new BusDtoResponse("marka avtobusa", 10);
    }

    public static BusDtoResponse getBusDtoResponse2() {
        return new BusDtoResponse("marka drugogo avtobusa", 5);
    }

    public static TripAdminDtoResponse getTripResponse(int tripId) {
        return new TripAdminDtoResponse(tripId, "nachalʹnaya stantsiya", "konechnaya stantsiya",
                LocalTime.parse("12:22"), LocalTime.parse("18:22"),
                BigDecimal.TEN, getBusDtoResponse(), Collections.singletonList(LocalDate.parse("2022-11-30")), false);
    }

    public static TripAdminDtoResponse getTripResponse2() {
        return new TripAdminDtoResponse(2, "nachalʹnaya stantsiya", "konechnaya stantsiya",
                LocalTime.parse("12:22"), LocalTime.parse("18:22"),
                BigDecimal.TEN, getBusDtoResponse2(), getSchedule(), getResponseDates(), false);
    }

    public static List<PassengerDto> getPassengersRequest(int count) {
        List<PassengerDto> passengers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            passengers.add(new PassengerDto("Пассажир", "Пассажиров", String.valueOf(i + 1)));
        }
        return passengers;
    }

    public static List<Passenger> getPassengers(int count) {
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            passengers.add(new Passenger(i + 1, "Пассажир", "Пассажиров", String.valueOf(i + 1)));
        }
        return passengers;
    }

    public static List<PassengerDto> getPassengersResponse(int count) {
        List<PassengerDto> passengers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            passengers.add(new PassengerDto("Passazhir", "Passazhirov", String.valueOf(i + 1)));
        }
        return passengers;
    }

    public static PlaceDtoRequest getPlaceRequest(int orderId, int place) {
        return new PlaceDtoRequest(orderId, "Пассажиров", "Пассажир", String.valueOf(place), place);
    }

    public static PlaceDtoResponse getPlaceResponse(int orderId, int tripId, int place) {
        String ticket = "Билет " + tripId + "_" + place;
        return new PlaceDtoResponse(orderId, ticket, "Passazhirov", "Passazhir", String.valueOf(place), place);
    }

    public static OrderDtoRequest getOrderRequest(int tripId, int passengerCount) {
        return new OrderDtoRequest(tripId, LocalDate.parse("2022-11-30"), getPassengersRequest(passengerCount));
    }

    public static OrderDtoRequest getOrderRequest2(int tripId, int passengerCount) {
        return new OrderDtoRequest(tripId, LocalDate.parse("2022-12-01"), getPassengersRequest(passengerCount));
    }

    public static TripDate getTripDate(int tripId) {
        return new TripDate(1, tripId, LocalDate.parse("2022-11-30"), getTripResponse(tripId).getBus().getPlaceCount(), null);
    }

    public static Order getOrder() {
        return new Order(1, 1, getTripDate(1), BigDecimal.TEN, getPassengers(1));
    }

    private static BigDecimal getTotalPrice(int passengerCount) {
        return new BigDecimal(10 * passengerCount);
    }

    public static OrderDtoResponse getOrderResponse(int orderId, int tripId, int passengerCount) {
        return new OrderDtoResponse(orderId, tripId, "nachalʹnaya stantsiya", "konechnaya stantsiya",
                "marka avtobusa", LocalDate.parse("2022-11-30"), LocalTime.parse("12:22:00"),
                LocalTime.parse("18:22:00"), BigDecimal.TEN, getTotalPrice(passengerCount), getPassengersResponse(passengerCount));
    }

    public static OrderDtoResponse getOrderResponse2(int orderId, int tripId, int passengerCount) {
        return new OrderDtoResponse(orderId, tripId, "nachalʹnaya stantsiya", "konechnaya stantsiya",
                "marka drugogo avtobusa", LocalDate.parse("2022-12-01"), LocalTime.parse("12:22:00"),
                LocalTime.parse("18:22:00"), BigDecimal.TEN, getTotalPrice(passengerCount), getPassengersResponse(passengerCount));
    }

    public static SettingsDtoResponse getSettingsResponse() {
        return new SettingsDtoResponse("50", "8");
    }

    public static List<BusDtoResponse> getBusList() {
        return new ArrayList<>() {{
            add(new BusDtoResponse("marka avtobusa", 10));
            add(new BusDtoResponse("marka drugogo avtobusa", 5));
        }};
    }

    public static Cookie getCookie() {
        return new Cookie("JAVASESSIONID", "1");
    }

}
