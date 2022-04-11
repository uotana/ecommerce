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

    public CartEntity addProduct(CartRequest cartRequest) {
        log.info("Adding product with id " + cartRequest.getProductId() + " to cart with id " + cartRequest.getCartId());
        validate(cartRequest);
        CartEntity cartEntity = cartRepository.findById(cartRequest.getCartId()).get();
        ProductEntity productEntity = productRepository.findById(cartRequest.getProductId()).get();

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setProduct(productEntity);
        List<CartItemEntity> items = cartEntity.getItems();

        if (!items.isEmpty()) {
            for (CartItemEntity item : items) {
                if (item.getProduct().getId().equals(cartRequest.getProductId())) {
                    log.info("A similar product is already in the cart");
                    cartItemEntity.setQuantity(item.getQuantity() + 1);
                    cartItemEntity.setCreationDate(item.getCreationDate());
                    cartItemEntity.setItemId(item.getItemId());
                    cartEntity.getItems().remove(item);
                    log.info("Now the cart has " + cartItemEntity.getQuantity() + " of this product");
                    break;
                } else {
                    cartItemEntity.setQuantity(1);
                    log.info("First of this product added to cart");
                }
            }
        } else {
            cartItemEntity.setCreationDate(ZonedDateTime.now());
            cartItemEntity.setQuantity(1);
            log.info("First of this product added to cart");
        }
        cartItemEntity.setUpdateDate(ZonedDateTime.now());
        cartEntity.getItems().add(cartItemRepository.save(cartItemEntity));
        return cartRepository.save(cartEntity);
    }

    public CartEntity deleteProduct(CartRequest request) {
        log.info("Removing product with id " + request.getProductId() + " from cart with id " + request.getCartId());
        validate(request);
        CartEntity cartEntity = cartRepository.findById(request.getCartId()).get();

        List<CartItemEntity> items = cartEntity.getItems();

        if (items.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + request.getProductId() + " not found");
        }
        CartItemEntity cartItemEntity = new CartItemEntity();
        for (CartItemEntity item : items) {
            if (item.getProduct().getId().equals(request.getProductId())) {
                log.info("Product found in the cart");
                if (item.getQuantity().equals(1)) {
                    cartItemRepository.deleteById(item.getItemId());
                    cartEntity.getItems().remove(item);
                    return cartRepository.save(cartEntity);
                }
                cartItemEntity.setItemId(item.getItemId());
                cartItemEntity.setProduct(item.getProduct());
                cartItemEntity.setQuantity(item.getQuantity() - 1);
                cartItemEntity.setCreationDate(item.getCreationDate());
                cartItemEntity.setUpdateDate(ZonedDateTime.now());
                cartEntity.getItems().remove(item);
                cartEntity.getItems().add(cartItemRepository.save(cartItemEntity));
                log.info("Now the cart has " + cartItemEntity.getQuantity() + " of this product");
            }
        }
        return cartRepository.save(cartEntity);
    }

    private void validate(CartRequest request){
        cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new CartNotFoundException("Cart with id " + request.getCartId() + " not found"));
        productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + request.getProductId() + " not found"));
    }
}
