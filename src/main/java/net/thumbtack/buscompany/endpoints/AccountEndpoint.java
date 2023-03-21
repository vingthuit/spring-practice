package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.user.UserDtoResponse;
import net.thumbtack.buscompany.services.AccountService;
import net.thumbtack.buscompany.services.SessionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountEndpoint {
    private final AccountService accountService;
    private final SessionService sessionService;

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@CookieValue("JAVASESSIONID") String cookie, HttpServletRequest request, HttpServletResponse response) {
        accountService.deleteUser(cookie);
        sessionService.logout(request, response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse getUserInfo(@CookieValue("JAVASESSIONID") String cookie) {
        return accountService.getUser(cookie);
    }
}
