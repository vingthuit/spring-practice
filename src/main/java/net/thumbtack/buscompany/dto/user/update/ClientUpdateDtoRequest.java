package net.thumbtack.buscompany.dto.user.update;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.thumbtack.buscompany.security.validators.Phone;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class ClientUpdateDtoRequest extends UserUpdateDtoRequest{
    @NotBlank @Email
    String email;
    @Phone
    String phone;

    public ClientUpdateDtoRequest(String firstName, String lastName, String patronymic,
                                  String oldPassword, String newPassword, String email, String phone) {
        super(firstName, lastName, patronymic, oldPassword, newPassword);
        this.email = email;
        this.phone = phone;
    }
}
