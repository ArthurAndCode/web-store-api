package Arthur.Code.web_store_api.dto;

import Arthur.Code.web_store_api.model.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Address address;
}