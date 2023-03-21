package net.thumbtack.buscompany.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.thumbtack.buscompany.security.validators.Phone;

import javax.validation.constraints.Email;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDtoRequest extends UserDtoRequest {
    @Email
    String email;
    @Phone
    String phone;

    public ClientDtoRequest(String firstName, String lastName, String patronymic,
                            String login, String password, String email, String phone) {
        super(firstName, lastName, patronymic, login, password);
        this.email = email;
        this.phone = phone;
    }
}
