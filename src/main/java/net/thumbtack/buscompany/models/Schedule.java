package net.thumbtack.buscompany.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {
    private LocalDate fromDate;
    private LocalDate toDate;
    private String period;
}
