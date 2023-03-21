package net.thumbtack.buscompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDtoRequest {
    private int tripId;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate date;
    @NotNull
    private List<PassengerDto> passengers;
}

