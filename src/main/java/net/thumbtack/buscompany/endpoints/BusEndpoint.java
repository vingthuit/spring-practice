package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.BusDtoResponse;
import net.thumbtack.buscompany.services.BusService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/buses")
public class BusEndpoint {
    private final BusService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusDtoResponse> getBuses(@CookieValue("JAVASESSIONID") String cookie) {
        return service.getAllBuses(cookie);
    }

}
