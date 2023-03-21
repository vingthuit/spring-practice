package net.thumbtack.buscompany.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tripDate")
public class TripDate {
    @Id
    private int id;
    private int tripId;
    private LocalDate date;
    private int freePlaces;
    @Transient
    private List<BusPlace> places;

    public TripDate(LocalDate date) {
        this.date = date;
    }

    public void setPlaces(int placeCount) {
        this.places = new ArrayList<>();
        for (int i = 1; i <= placeCount; i++) {
            places.add(new BusPlace(id, i, 0));
        }
    }

    public void setPlaces(List<BusPlace> places) {
        this.places = places;
    }

}
