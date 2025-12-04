package com.example.demo.Controller;

import com.example.demo.Entity.Dto.OrderItemDto;
import com.example.demo.Entity.Order;
import com.example.demo.Service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<?> addItemToOrder(
            @PathVariable Long orderId,
            @RequestBody OrderItemDto dto
    ) {
        Order updatedOrder = orderItemService.addItemToOrder(orderId, dto);
        return ResponseEntity.ok(updatedOrder);
    }
}
