package Arthur.Code.web_store_api.controller;

import Arthur.Code.web_store_api.dto.DataResponseDTO;
import Arthur.Code.web_store_api.model.Cart;
import Arthur.Code.web_store_api.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}/carts")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<DataResponseDTO<Cart>> getCartByUserId(@PathVariable Long id) {
        List<Cart> cartItems = cartService.getCartByUserId(id);
        DataResponseDTO<Cart> response = new DataResponseDTO<>(cartItems, "products");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable(name = "id") Long userId, @PathVariable Long productId) {
        cartService.addProductToCart(userId, productId);
        return ResponseEntity.status(HttpStatus.OK).body("Product added to cart.");
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable(name = "id") Long userId, @PathVariable Long productId) {
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.status(HttpStatus.OK).body("Product removed from cart.");
    }

    @DeleteMapping
    public ResponseEntity<String> clearCart(@PathVariable Long id) {
        cartService.clearCart(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cart cleared.");
    }
}
