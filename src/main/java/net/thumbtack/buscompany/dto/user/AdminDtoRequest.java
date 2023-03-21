package net.thumbtack.buscompany.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDtoRequest extends UserDtoRequest {
    @NotBlank
    String position;

    public AdminDtoRequest(String firstName, String lastName, String patronymic,
                           String login, String password, String position) {
        super(firstName, lastName, patronymic, login, password);
        this.position = position;
    }
}
