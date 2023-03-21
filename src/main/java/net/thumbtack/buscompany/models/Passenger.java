package net.thumbtack.buscompany.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "passenger")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Passenger {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String passport;
}
