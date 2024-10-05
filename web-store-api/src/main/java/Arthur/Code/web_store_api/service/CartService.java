package Arthur.Code.web_store_api.service;

import Arthur.Code.web_store_api.data.CartRepository;
import Arthur.Code.web_store_api.data.ProductRepository;
import Arthur.Code.web_store_api.model.Cart;
import Arthur.Code.web_store_api.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public List<Cart> getCartByUserId(Long id) {
        return cartRepository.findByUserId(id);
    }

    public void addProductToCart(Long userId, Long productId) {
        Cart cartItem = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseGet(() -> createNewCartItem(userId, productId));

        incrementProductAmount(cartItem);
        cartRepository.save(cartItem);
    }

    public void removeProductFromCart(Long userId, Long productId) {
        Cart cartItem = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new IllegalStateException("Product not found in cart for user with ID " + userId));

        if (cartItem.getAmount() > 1) {
            decrementProductAmount(cartItem);
            cartRepository.save(cartItem);
        } else {
            cartRepository.delete(cartItem);
        }
    }

    private Cart createNewCartItem(Long userId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with ID " + productId + " not found."));

        Cart newCartItem = new Cart();
        newCartItem.setUserId(userId);
        newCartItem.setProduct(product);
        newCartItem.setAmount(0);
        return newCartItem;
    }

    private void incrementProductAmount(Cart cartItem) {
        cartItem.setAmount(cartItem.getAmount() + 1);
    }

    private void decrementProductAmount(Cart cartItem) {
        cartItem.setAmount(cartItem.getAmount() - 1);
    }

    public void clearCart(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        if (!cartItems.isEmpty()) {
            cartRepository.deleteAll(cartItems);
        }
    }

}
