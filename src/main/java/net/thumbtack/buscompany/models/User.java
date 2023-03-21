package net.thumbtack.buscompany.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    private int userId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private UserType userType;
    private String login;
    private String password;
    private byte[] salt;
}

