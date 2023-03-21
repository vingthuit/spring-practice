package net.thumbtack.buscompany.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    private int id;
    private String fromStation;
    private String toStation;
    private LocalTime start;
    private LocalTime duration;
    private BigDecimal price;
    @Transient
    private Bus bus;
    private boolean approved;
    @Transient
    private Schedule schedule;
    @Transient
    private List<TripDate> dates;

}
