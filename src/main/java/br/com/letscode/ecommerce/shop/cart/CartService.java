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
                "Cart with id " + cartId + " not found."));

        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        ProductEntity productEntity = productEntityOptional.orElseThrow(() -> new ProductNotFoundException(
                "Product with id " + productId + " not found"));

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setProduct(productEntity);
//        cartItemEntity.setCartId(cartId);
        List<CartItemEntity> items = cartEntity.getItems();

        if(!items.isEmpty()){
            for(CartItemEntity item : items){
                if(item.getProduct().getId().equals(productId)){
                    log.info("A similar product is already in the cart");
                    cartItemEntity.setQuantity(item.getQuantity() + 1);
                    cartItemEntity.setCreationDate(item.getCreationDate());
                    cartItemEntity.setItemId(item.getItemId());
                    log.info("Now the cart has "+ cartItemEntity.getQuantity()+" of this product");
                }else{
                    cartItemEntity.setQuantity(1);
                    log.info("First of this product added to cart");
                }
            }
        }else{
            cartItemEntity.setCreationDate(ZonedDateTime.now());
            cartItemEntity.setQuantity(1);
            log.info("First of this product added to cart");
        }

        cartItemEntity.setUpdateDate(ZonedDateTime.now());
        cartEntity.getItems().add(cartItemRepository.save(cartItemEntity));
        return cartRepository.save(cartEntity);
    }

    public CartEntity deleteProduct(Long cartId, Long productId) {

        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);
        CartEntity cartEntity = cartEntityOptional.orElseThrow(() -> new CartNotFoundException(
                "Cart with id " + cartId + " not found"));
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        productEntityOptional.orElseThrow(() -> new ProductNotFoundException(
                "Product with id " + productId + " not found"));
        cartEntity.getItems().remove(productEntityOptional.get());
        return cartRepository.save(cartEntity);
    }
}
