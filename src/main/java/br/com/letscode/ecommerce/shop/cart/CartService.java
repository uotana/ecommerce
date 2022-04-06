package br.com.letscode.ecommerce.shop.cart;

import br.com.letscode.ecommerce.shop.exception.CartNotFoundException;
import br.com.letscode.ecommerce.shop.exception.ProductNotFoundException;
import br.com.letscode.ecommerce.shop.product.ProductEntity;
import br.com.letscode.ecommerce.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartEntity addProduct(Long cartId, Long productId) {

        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);
        CartEntity cartEntity = cartEntityOptional.orElseThrow(() -> new CartNotFoundException(
                "Cart with id " + cartId + " not found"));
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        ProductEntity productEntity = productEntityOptional.orElseThrow(() -> new ProductNotFoundException(
                "Product with id " + productId + " not found"));
        cartEntity.getProducts().add(productEntity);
        return cartRepository.save(cartEntity);
    }

    public CartEntity deleteProduct(Long cartId, Long productId) {

        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);
        CartEntity cartEntity = cartEntityOptional.orElseThrow(() -> new CartNotFoundException(
                "Cart with id " + cartId + " not found"));
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        productEntityOptional.orElseThrow(() -> new ProductNotFoundException(
                "Product with id " + productId + " not found"));
        cartEntity.getProducts().remove(productEntityOptional.get());
        return cartRepository.save(cartEntity);
    }
}
