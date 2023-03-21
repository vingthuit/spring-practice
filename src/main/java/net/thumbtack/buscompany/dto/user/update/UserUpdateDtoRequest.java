package net.thumbtack.buscompany.dto.user.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.buscompany.security.validators.fullname.MaxLength;
import net.thumbtack.buscompany.security.validators.fullname.MinLength;
import net.thumbtack.buscompany.security.validators.fullname.Name;
import net.thumbtack.buscompany.security.validators.fullname.Patronymic;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDtoRequest {
    @Name
    private String firstName;
    @Name
    private String lastName;
    @Patronymic
    private String patronymic;
    private String oldPassword;
    @NotBlank @MaxLength @MinLength
    private String newPassword;
}
