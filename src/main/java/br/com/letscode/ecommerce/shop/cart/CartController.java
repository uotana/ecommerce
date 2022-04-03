package br.com.letscode.ecommerce.shop.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService service;

    @PostMapping("/{cart-id}/{product-id}")
    public ResponseEntity<CartEntity> addProduct(@PathVariable(value = "cart-id") Long cartId,
                           @PathVariable(value = "product-id") Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(service.addProduct(cartId, productId));
    }
}
