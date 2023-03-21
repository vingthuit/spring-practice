package net.thumbtack.buscompany.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sessionAttributes")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionAttributes {
    @Id
    private String id;
    private int userId;
}
