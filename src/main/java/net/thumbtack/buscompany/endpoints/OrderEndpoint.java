package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.dto.OrderDtoRequest;
import net.thumbtack.buscompany.dto.OrderDtoResponse;
import net.thumbtack.buscompany.services.OrderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderEndpoint {
    private final OrderService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDtoResponse orderTicket(@CookieValue("JAVASESSIONID") String cookie, @RequestBody @Valid OrderDtoRequest orderRequest) {
        return service.orderTicket(cookie, orderRequest);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDtoResponse> getOrderList(@CookieValue("JAVASESSIONID") String cookie, @RequestParam Map<String, String> allParams) {
        return service.getOrderList(cookie, allParams);
    }

    @DeleteMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrder(@CookieValue("JAVASESSIONID") String cookie, @PathVariable int orderId) {
        service.deleteOrder(cookie, orderId);
    }
}
