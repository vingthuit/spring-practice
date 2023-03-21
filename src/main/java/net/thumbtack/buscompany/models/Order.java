package net.thumbtack.buscompany.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orderTable")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
	@Id
    private int id;
    private int clientId;
    @Transient
	private TripDate tripDate;
	private BigDecimal totalPrice;
    @Transient
    private List<Passenger> passengers;
}
