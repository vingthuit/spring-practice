package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.trip.TripAdminDtoResponse;
import net.thumbtack.buscompany.dto.trip.TripClientDtoResponse;
import net.thumbtack.buscompany.dto.trip.TripDtoRequest;
import net.thumbtack.buscompany.services.TripService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/trips")
public class TripEndPoint {
    private final TripService tripService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TripAdminDtoResponse addTrip(@CookieValue("JAVASESSIONID") String cookie, @RequestBody @Valid TripDtoRequest request) {
        return tripService.addTrip(cookie, request);
    }

    @PutMapping(value = "/{tripId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public TripAdminDtoResponse updateTrip(@CookieValue("JAVASESSIONID") String cookie,
                                           @RequestBody @Valid TripDtoRequest trip, @PathVariable int tripId) {
        return tripService.updateTrip(cookie, tripId, trip);
    }

    @PutMapping(value = "/{tripId}/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public TripAdminDtoResponse approveTrip(@CookieValue("JAVASESSIONID") String cookie, @PathVariable int tripId) {
        return tripService.approve(cookie, tripId);
    }

    @DeleteMapping(value = "/{tripId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTrip(@CookieValue("JAVASESSIONID") String cookie, @PathVariable int tripId) {
        tripService.deleteTrip(cookie, tripId);
    }

    @GetMapping(value = "/{tripId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TripAdminDtoResponse getTrip(@CookieValue("JAVASESSIONID") String cookie, @PathVariable int tripId) {
        return tripService.getTrip(cookie, tripId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TripClientDtoResponse> getTripList(@CookieValue("JAVASESSIONID") String cookie,
                                                   @RequestParam Map<String, String> allParams) {
        return tripService.getTripList(cookie, allParams);
    }

}
