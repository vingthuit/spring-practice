package net.thumbtack.buscompany.dto.trip;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ScheduleDtoRequest {
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate fromDate;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate toDate;
    @NotBlank
    private String period;
}
