package Arthur.Code.web_store_api.service;

import Arthur.Code.web_store_api.data.AddressRepository;
import Arthur.Code.web_store_api.data.UserRepository;
import Arthur.Code.web_store_api.model.Address;
import Arthur.Code.web_store_api.model.User;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public void addAddress(Long userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found."));

        if (user.getAddress() != null) {
            throw new IllegalStateException("User already has a current address.");
        }

        user.setAddress(address);
        userRepository.save(user);
    }

    public void updateAddress(Long userId, Address updatedAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found."));

        Address existingAddress = user.getAddress();
        if (existingAddress == null) {
            throw new IllegalStateException("User does not have an existing address to update.");
        }

        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());
        existingAddress.setCountry(updatedAddress.getCountry());

        addressRepository.save(existingAddress);
    }

    public void deleteAddress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found."));

        Address address = user.getAddress();
        if (address == null) {
            throw new IllegalStateException("User does not have an address to delete.");
        }

        user.setAddress(null);
        userRepository.save(user);
    }

}

