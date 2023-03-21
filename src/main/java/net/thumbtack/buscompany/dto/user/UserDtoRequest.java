package net.thumbtack.buscompany.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.buscompany.security.validators.authorization.NewLogin;
import net.thumbtack.buscompany.security.validators.fullname.MaxLength;
import net.thumbtack.buscompany.security.validators.fullname.MinLength;
import net.thumbtack.buscompany.security.validators.fullname.Name;
import net.thumbtack.buscompany.security.validators.fullname.Patronymic;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDtoRequest {
    @Name
    private String firstName;
    @Name
    private String lastName;
    @Patronymic
    private String patronymic;

    @MaxLength @NewLogin
    private String login;
    @NotBlank @MinLength
    private String password;
}
