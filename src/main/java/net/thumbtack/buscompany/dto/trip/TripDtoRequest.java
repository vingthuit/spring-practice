package net.thumbtack.buscompany.dto.trip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.buscompany.security.validators.Dates;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Dates
public class TripDtoRequest {
    @NotBlank
    private String busName;
    @NotBlank
    private String fromStation;
    @NotBlank
    private String toStation;
    @NotNull @DateTimeFormat(pattern = "HH:MM")
    private LocalTime start;
    @NotNull @DateTimeFormat(pattern = "HH:MM")
    private LocalTime duration;
    @NotNull
    private BigDecimal price;
    private ScheduleDtoRequest schedule;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private List<LocalDate> dates;

    public TripDtoRequest(String busName, String fromStation, String toStation, LocalTime start,
                          LocalTime duration, BigDecimal price, List<LocalDate> dates) {
        this.busName = busName;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.start = start;
        this.duration = duration;
        this.price = price;
        this.dates = dates;
    }

    public TripDtoRequest(String busName, String fromStation, String toStation, LocalTime start,
                          LocalTime duration, BigDecimal price, ScheduleDtoRequest schedule) {
        this.busName = busName;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.start = start;
        this.duration = duration;
        this.price = price;
        this.schedule = schedule;
    }
}
