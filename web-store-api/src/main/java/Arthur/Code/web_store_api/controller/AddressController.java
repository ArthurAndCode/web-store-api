package Arthur.Code.web_store_api.controller;

import Arthur.Code.web_store_api.model.Address;
import Arthur.Code.web_store_api.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{id}/addresses")
public class AddressController {
    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<String> addAddress(@PathVariable Long id, @RequestBody Address address) {
        addressService.addAddress(id, address);
        return ResponseEntity.status(HttpStatus.OK).body("Address added successfully.");
    }
    @PutMapping
    public ResponseEntity<String> updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress) {
        addressService.updateAddress(id, updatedAddress);
        return ResponseEntity.status(HttpStatus.OK).body("Address updated successfully.");
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.OK).body("Address deleted successfully.");
    }
}
