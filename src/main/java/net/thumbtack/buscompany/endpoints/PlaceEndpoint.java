package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.bus.PlaceDtoRequest;
import net.thumbtack.buscompany.dto.bus.PlaceDtoResponse;
import net.thumbtack.buscompany.services.PlaceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/places")
public class PlaceEndpoint {
    private PlaceService placeService;

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> getFreePlaces(@CookieValue("JAVASESSIONID") String cookie, @PathVariable int orderId) {
        return placeService.getFreePlaces(cookie, orderId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PlaceDtoResponse takePlace(@CookieValue("JAVASESSIONID") String cookie, @RequestBody PlaceDtoRequest request) {
        return placeService.takePlace(cookie, request);
    }

}
