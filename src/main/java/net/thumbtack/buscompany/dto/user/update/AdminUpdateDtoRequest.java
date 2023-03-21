package net.thumbtack.buscompany.dto.user.update;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class AdminUpdateDtoRequest extends UserUpdateDtoRequest {
    @NotBlank
    private String position;

    public AdminUpdateDtoRequest(String firstName, String lastName, String patronymic,
                                 String oldPassword, String newPassword, String position) {
        super(firstName, lastName, patronymic, oldPassword, newPassword);
        this.position = position;
    }
}
