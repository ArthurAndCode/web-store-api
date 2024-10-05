package Arthur.Code.web_store_api.dto;

import lombok.Getter;

@Getter
public class UpdateOrderStatusRequest {
    private String orderStatus;
    private String paymentStatus;
}
