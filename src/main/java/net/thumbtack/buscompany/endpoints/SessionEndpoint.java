package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.user.LoginDtoRequest;
import net.thumbtack.buscompany.dto.user.UserDtoResponse;
import net.thumbtack.buscompany.services.AccountService;
import net.thumbtack.buscompany.services.SessionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/api/sessions")
public class SessionEndpoint {
    private final SessionService service;
    private final AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse login(@RequestBody LoginDtoRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        String cookie = service.login(loginRequest, request, response);
        return accountService.getUser(cookie);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void logout(@CookieValue("JAVASESSIONID") String cookie, HttpServletRequest request, HttpServletResponse response) {
        service.logout(request, response);
    }

}
