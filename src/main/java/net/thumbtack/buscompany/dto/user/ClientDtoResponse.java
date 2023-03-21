package net.thumbtack.buscompany.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDtoResponse extends UserDtoResponse {
    private String email;
    private String phone;

    public ClientDtoResponse(int id, String firstName, String lastName, String patronymic,
                             String userType, String email, String phone) {
        super(id, firstName, lastName, patronymic, userType);
        this.email = email;
        this.phone = phone;
    }
}