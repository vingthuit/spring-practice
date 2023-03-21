package net.thumbtack.buscompany.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "busPlace")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusPlace {
    @Id
    private int id;
    private int tripDateId;
    private int place;
    private int passengerId;

    public BusPlace(int tripDateId, int place, int passengerId) {
        this.tripDateId = tripDateId;
        this.place = place;
        this.passengerId = passengerId;
    }

}
