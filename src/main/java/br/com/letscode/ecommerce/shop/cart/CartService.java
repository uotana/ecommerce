package br.com.letscode.ecommerce.shop.cart;

import br.com.letscode.ecommerce.shop.cartitem.CartItemEntity;
import br.com.letscode.ecommerce.shop.cartitem.CartItemRepository;
import br.com.letscode.ecommerce.shop.exception.CartNotFoundException;
import br.com.letscode.ecommerce.shop.exception.ProductNotFoundException;
import br.com.letscode.ecommerce.shop.product.ProductEntity;
import br.com.letscode.ecommerce.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartEntity addProduct(Long cartId, Long productId) {
        log.info("Adding product with id " + productId + " to cart with id " + cartId);
        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);
        CartEntity cartEntity = cartEntityOptional.orElseThrow(() -> new CartNotFoundException(
                "Cart with id " + cartId + " not found"));

        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        ProductEntity productEntity = productEntityOptional.orElseThrow(() -> new ProductNotFoundException(
                "Product with id " + productId + " not found"));

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setProduct(productEntity);
        cartItemEntity.setCartId(cartId);
        cartItemEntity.setCreationDate(ZonedDateTime.now());
        cartItemEntity.setUpdateDate(ZonedDateTime.now());

        List<CartItemEntity> items = cartEntity.getProducts();

        for(CartItemEntity item : items){
            if(item.getProduct().getId().equals(productId)){
                cartItemEntity.setId(item.getId());
                cartItemEntity.setQuantity(item.getQuantity() + 1);
            }else{
                cartItemEntity.setQuantity(1);
            }
        }
        cartEntity.getProducts().add(cartItemRepository.save(cartItemEntity));

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
