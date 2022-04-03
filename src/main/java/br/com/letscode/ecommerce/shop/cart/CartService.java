package br.com.letscode.ecommerce.shop.cart;

import br.com.letscode.ecommerce.shop.product.ProductEntity;
import br.com.letscode.ecommerce.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        if(cartEntityOptional.isPresent() && productEntityOptional.isPresent()){
            log.info("Adding product " + productId + " to cart " + cartId);
            CartEntity cartEntity = new CartEntity();
            BeanUtils.copyProperties(cartEntityOptional.get(), cartEntity);

            ProductEntity productEntity = new ProductEntity();
            BeanUtils.copyProperties(productEntityOptional.get(), productEntity);
            cartEntity.getProducts().add(productEntity);
            return cartRepository.save(cartEntity);
        }else{
            throw new RuntimeException("Not found");
        }
    }

    public CartEntity deleteProduct(Long cartId, Long productId) {
        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        if(cartEntityOptional.isPresent() && productEntityOptional.isPresent()){
            log.info("Removing product " + productId + " from cart " + cartId);
            CartEntity cartEntity = new CartEntity();
            BeanUtils.copyProperties(cartEntityOptional.get(), cartEntity);
            cartEntity.getProducts().remove(productEntityOptional.get());
            return cartRepository.save(cartEntity);
        }else{
            throw new RuntimeException("Not found");
        }
    }
}
