package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.user.AdminDtoRequest;
import net.thumbtack.buscompany.dto.user.AdminDtoResponse;
import net.thumbtack.buscompany.dto.user.update.AdminUpdateDtoRequest;
import net.thumbtack.buscompany.services.AdminService;
import net.thumbtack.buscompany.services.SessionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admins")
public class AdminEndpoint {
    private final AdminService adminService;
    private final SessionService sessionService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdminDtoResponse register(@RequestBody @Valid AdminDtoRequest admin, HttpServletRequest request, HttpServletResponse response) {
        AdminDtoResponse adminResponse = adminService.register(admin);
        sessionService.setSessionAttributes(adminResponse.getUserId(), request, response);
        return adminResponse;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdminDtoResponse update(@CookieValue("JAVASESSIONID") String cookie, @RequestBody @Valid AdminUpdateDtoRequest request) {
        return adminService.update(cookie, request);
    }

}
