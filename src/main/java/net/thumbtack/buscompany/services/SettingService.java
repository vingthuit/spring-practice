package net.thumbtack.buscompany.services;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.SettingsDtoResponse;
import net.thumbtack.buscompany.security.validators.AccessValidator;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@AllArgsConstructor
@Service
public class SettingService {
    private final AccessValidator validator;
    private final SettingsDtoResponse settingsDtoResponse;

    public SettingsDtoResponse getSettings(Cookie cookie) {
        String userType = cookie == null ? "" : validator.isValid(cookie.getValue()).getUserType().name();
        switch (userType) {
            case "ADMIN" : System.out.println("Admin settings"); break;
            case "CLIENT" : System.out.println("Client settings"); break;
            default : System.out.println("Default settings"); break;
        }
        return settingsDtoResponse;
    }

}
