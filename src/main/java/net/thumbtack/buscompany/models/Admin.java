package net.thumbtack.buscompany.models;

import lombok.*;
import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin")
@NoArgsConstructor
@Data
public class Admin extends User {
    private String position;

    public Admin(int id, String firstName, String lastName, String patronymic,
                 UserType userType, String login, String password, byte[] salt, String position) {
        super(id, firstName, lastName, patronymic, userType, login, password, salt);
        this.position = position;
    }
}
