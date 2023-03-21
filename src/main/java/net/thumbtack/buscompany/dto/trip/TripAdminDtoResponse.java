package net.thumbtack.buscompany.dto.trip;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.thumbtack.buscompany.dto.BusDtoResponse;
import net.thumbtack.buscompany.models.Schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@Data
public class TripAdminDtoResponse extends TripClientDtoResponse {
    private boolean approved;

    public TripAdminDtoResponse(int id, String fromStation, String toStation, LocalTime start, LocalTime duration,
                                BigDecimal price, BusDtoResponse bus, Schedule schedule, List<LocalDate> dates, boolean approved) {
        super(id, fromStation, toStation, start, duration, price, bus, schedule, dates);
        this.approved = approved;
    }

    public TripAdminDtoResponse(int id, String fromStation, String toStation, LocalTime start, LocalTime duration,
                                BigDecimal price, BusDtoResponse bus, List<LocalDate> dates, boolean approved) {
        super(id, fromStation, toStation, start, duration, price, bus, dates);
        this.approved = approved;
    }


}
