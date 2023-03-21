package net.thumbtack.buscompany.dto.bus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceDtoResponse {
    private int orderId;
    private String ticket;
    private String lastName;
    private String firstName;
    private String passport;
    private int place;
}
