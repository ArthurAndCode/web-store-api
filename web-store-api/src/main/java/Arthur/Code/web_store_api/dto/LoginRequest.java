package Arthur.Code.web_store_api.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
