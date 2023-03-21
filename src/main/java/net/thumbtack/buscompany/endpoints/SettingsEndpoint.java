package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.SettingsDtoResponse;
import net.thumbtack.buscompany.services.SettingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@AllArgsConstructor
@RestController
@RequestMapping("/api/settings")
public class SettingsEndpoint {
    private final SettingService settings;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SettingsDtoResponse getSettings(@CookieValue(name = "JAVASESSIONID", required = false) Cookie cookie) {
        return settings.getSettings(cookie);
    }
}
