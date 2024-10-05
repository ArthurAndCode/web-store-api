package Arthur.Code.web_store_api.service;

import Arthur.Code.web_store_api.data.UserRepository;
import Arthur.Code.web_store_api.dto.LoginRequest;
import Arthur.Code.web_store_api.dto.UserDTO;
import Arthur.Code.web_store_api.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(toList());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found."));
    }

    public void createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already taken.");
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);
    }


    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("User with ID " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("User not found."));
    }

    public void updateUser(User updatedUser) {
        userRepository.findById(updatedUser.getId())
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setPhone(updatedUser.getPhone());

                    if (updatedUser.getAddress() != null) {
                        user.setAddress(updatedUser.getAddress());
                    }

                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        user.setPassword(hashPassword(updatedUser.getPassword()));
                    }

                    user.setUpdatedAt(LocalDateTime.now());

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new IllegalStateException("User with ID " + updatedUser.getId() + " does not exist."));

    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public UserDTO loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()
                -> new IllegalStateException("User not found."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Invalid password.");
        }

        return convertToDto(user);
    }

    public UserDTO convertToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        return dto;
    }
}