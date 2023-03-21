package net.thumbtack.buscompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDtoResponse {
    private int orderId;
    private int tripId;
    private String fromStation;
    private String toStation;
    private String busName;
    private LocalDate date;
    private LocalTime start;
    private LocalTime duration;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private List<PassengerDto> passengers;
}
