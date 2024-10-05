package Arthur.Code.web_store_api.controller;

import Arthur.Code.web_store_api.dto.DataResponseDTO;
import Arthur.Code.web_store_api.dto.OrderDTO;
import Arthur.Code.web_store_api.dto.UpdateOrderStatusRequest;
import Arthur.Code.web_store_api.model.OrderItem;
import Arthur.Code.web_store_api.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<DataResponseDTO<OrderDTO>> getOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        DataResponseDTO<OrderDTO> response = new DataResponseDTO<>(orders, "orders");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/completed")
    public ResponseEntity<DataResponseDTO<OrderDTO>> getAllCompletedOrders() {
        List<OrderDTO> orders = orderService.getAllCompletedOrders();
        DataResponseDTO<OrderDTO> response = new DataResponseDTO<>(orders, "completedOrders");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/paid")
    public ResponseEntity<DataResponseDTO<OrderDTO>> getAllPaidUncompletedOrders() {
        List<OrderDTO> orders = orderService.getAllPaidUncompletedOrders();
        DataResponseDTO<OrderDTO> response = new DataResponseDTO<>(orders, "paidOrders");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<DataResponseDTO<OrderDTO>> getOrdersByUserId(@PathVariable(name = "id") Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        DataResponseDTO<OrderDTO> response = new DataResponseDTO<>(orders, "orders");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<DataResponseDTO<OrderItem>> getOrderDetails(@PathVariable(name = "id") Long orderId) {
        List<OrderItem> items = orderService.getOrderItems(orderId);
        DataResponseDTO<OrderItem> response = new DataResponseDTO<>(items, "products");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/users/{id}")
    ResponseEntity<String> createOrder(@PathVariable(name = "id") Long userId) {
        orderService.createOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully.");
    }

    @PutMapping("{id}")
    ResponseEntity<String> updateStatus(@PathVariable(name = "id") Long orderId,
                                        @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderAndPaymentStatus(orderId, request.getOrderStatus(), request.getPaymentStatus());
        return ResponseEntity.ok("Order status updated successfully");
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> deleteOrder(@PathVariable(name = "id") Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }
}
