package net.thumbtack.buscompany.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDtoResponse {
    private int userId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String userType;
}
