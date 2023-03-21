package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.user.ClientDtoRequest;
import net.thumbtack.buscompany.dto.user.ClientDtoResponse;
import net.thumbtack.buscompany.dto.user.update.ClientUpdateDtoRequest;
import net.thumbtack.buscompany.services.ClientService;
import net.thumbtack.buscompany.services.SessionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientEndpoint {
    private final ClientService service;
    private final SessionService sessionService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientDtoResponse register(@RequestBody @Valid ClientDtoRequest client,
                                      HttpServletRequest request, HttpServletResponse response) {
        ClientDtoResponse clientResponse = service.register(client);
        sessionService.setSessionAttributes(clientResponse.getUserId(), request, response);
        return clientResponse;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientDtoResponse> getAll(@CookieValue("JAVASESSIONID") String cookie) {
        return service.getAll(cookie);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientDtoResponse update(@CookieValue("JAVASESSIONID") String cookie, @RequestBody @Valid ClientUpdateDtoRequest request) {
        return service.update(cookie, request);
    }
}
