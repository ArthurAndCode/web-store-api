package Arthur.Code.web_store_api.controller;

import Arthur.Code.web_store_api.dto.DataResponseDTO;
import Arthur.Code.web_store_api.dto.LoginRequest;
import Arthur.Code.web_store_api.dto.UserDTO;
import Arthur.Code.web_store_api.model.User;
import Arthur.Code.web_store_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<DataResponseDTO<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        DataResponseDTO<UserDTO> response = new DataResponseDTO<>(users, "users");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userService.convertToDto(user));
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        updatedUser.setId(id);
        userService.updateUser(updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userService.convertToDto(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody LoginRequest loginRequest) {
        UserDTO userDTO = userService.loginUser(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

}

