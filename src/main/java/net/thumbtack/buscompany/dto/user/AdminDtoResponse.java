package net.thumbtack.buscompany.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class AdminDtoResponse extends UserDtoResponse {
    private String position;

    public AdminDtoResponse(int id, String firstName, String lastName, String patronymic,
                            String userType, String position) {
        super(id, firstName, lastName, patronymic, userType);
        this.position = position;
    }
}
