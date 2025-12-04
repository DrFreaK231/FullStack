package com.example.demo.Controller;

import com.example.demo.Entity.Dto.OrderDto;
import com.example.demo.Entity.Dto.PaymentDto;
import com.example.demo.Entity.Order;
import com.example.demo.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
            @RequestBody OrderDto dto
    ) {
        Order order = orderService.createOrder(dto);
        return ResponseEntity.ok(order.getUuid());
    }

//    @GetMapping("/{orderId}")
//    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
//        return ResponseEntity.ok("TODO: implement get order logic later"  );
//    }
//    @PutMapping("/{orderId}/status")
//    public ResponseEntity<?> updateOrderStatus(
//            @PathVariable Long orderId,
//            @RequestParam String status
//    ) {
//        return ResponseEntity.ok("TODO: implement update order status logic later");
//    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<PaymentDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

}
