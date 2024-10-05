package Arthur.Code.web_store_api.service;

import Arthur.Code.web_store_api.data.OrderItemRepository;
import Arthur.Code.web_store_api.data.OrderRepository;
import Arthur.Code.web_store_api.dto.OrderDTO;
import Arthur.Code.web_store_api.dto.UserDTO;
import Arthur.Code.web_store_api.model.Cart;
import Arthur.Code.web_store_api.model.Order;
import Arthur.Code.web_store_api.model.OrderItem;
import Arthur.Code.web_store_api.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    public void createOrder(Long userId) {
        User user = getUserWithAddress(userId);
        List<Cart> cartItems = getCartItemsForUser(userId);
        BigDecimal totalPrice = calculateTotalPrice(cartItems);

        Order order = createOrderEntity(user, totalPrice);
        orderRepository.save(order);
        saveOrderItems(order, cartItems);
        cartService.clearCart(userId);
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToOrderDto)
                .collect(toList());
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllCompletedOrders() {
        return orderRepository.findAll().stream()
                .filter(order -> "COMPLETED".equals(order.getOrderStatus()))
                .map(this::convertToOrderDto)
                .collect(toList());
    }

    public List<OrderDTO> getAllPaidUncompletedOrders() {
        return orderRepository.findAll().stream()
                .filter(order -> "PAID".equals(order.getPaymentStatus()) && !"COMPLETED".equals(order.getOrderStatus()))
                .map(this::convertToOrderDto)
                .collect(toList());
    }


    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    public void updateOrderAndPaymentStatus(Long orderId, String newOrderStatus, String newPaymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID " + orderId));

        if (newOrderStatus != null) {
            order.setOrderStatus(newOrderStatus);
        }

        if (newPaymentStatus != null) {
            order.setPaymentStatus(newPaymentStatus);
        }

        orderRepository.save(order);
    }
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalStateException("Order with ID " + orderId + " does not exist.");
        }
        orderRepository.deleteById(orderId);
    }

    private User getUserWithAddress(Long userId) {
        User user = userService.getUserById(userId);
        if (user.getAddress() == null) {
            throw new IllegalStateException("To order, you must add an address to your account");
        }
        return user;
    }

    private List<Cart> getCartItemsForUser(Long userId) {
        List<Cart> cartItems = cartService.getCartByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("No items in the cart for user with ID " + userId);
        }
        return cartItems;
    }

    private Order createOrderEntity(User user, BigDecimal totalPrice) {
        Order order = new Order();
        order.setUser(user);
        order.setTotal(totalPrice);
        order.setPaymentStatus("PENDING");
        order.setOrderStatus("NEW");
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    private BigDecimal calculateTotalPrice(List<Cart> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void saveOrderItems(Order order, List<Cart> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setAmount(cartItem.getAmount());
            orderItems.add(orderItem);
        }
        orderItemRepository.saveAll(orderItems);
    }

    private OrderDTO convertToOrderDto(Order order) {
        UserDTO userDTO = userService.convertToDto(order.getUser());
        return new OrderDTO(
                order.getId(),
                order.getTotal(),
                order.getPaymentStatus(),
                order.getOrderStatus(),
                order.getCreatedAt(),
                userDTO
        );
    }
}
