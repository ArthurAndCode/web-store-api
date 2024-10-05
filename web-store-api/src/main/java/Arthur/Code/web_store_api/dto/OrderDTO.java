package Arthur.Code.web_store_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private Long orderId;
    private BigDecimal total;
    private String paymentStatus;
    private String orderStatus;
    private LocalDateTime createdAt;
    private UserDTO user;
}
