package net.thumbtack.buscompany.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDtoRequest {
    private String login;
    private String password;
}
