package net.thumbtack.buscompany.dto.trip;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.buscompany.dto.BusDtoResponse;
import net.thumbtack.buscompany.models.Schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TripClientDtoResponse {
    private int id;
    private String fromStation;
    private String toStation;
    private LocalTime start;
    private LocalTime duration;
    private BigDecimal price;
    private BusDtoResponse bus;
    private Schedule schedule;
    private List<LocalDate> dates;

    public TripClientDtoResponse(int id, String fromStation, String toStation, LocalTime start, LocalTime duration,
                                 BigDecimal price, BusDtoResponse bus, List<LocalDate> dates) {
        this.id = id;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.start = start;
        this.duration = duration;
        this.price = price;
        this.bus = bus;
        this.dates = dates;
    }
}
