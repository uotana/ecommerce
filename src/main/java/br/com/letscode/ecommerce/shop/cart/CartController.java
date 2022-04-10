package br.com.letscode.ecommerce.shop.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/carts")
public class CartController {

    private final CartService service;

    @PostMapping
    public ResponseEntity<CartEntity> addProduct(@RequestBody CartRequest cartRequest){
        return ResponseEntity.status(HttpStatus.OK).body(service.addProduct(cartRequest));
    }

    @DeleteMapping("/{cart-id}/{product-id}")
    public ResponseEntity<CartEntity> deleteProduct(@PathVariable(value = "cart-id") Long cartId,
                                         @PathVariable(value = "product-id") Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteProduct(cartId, productId));
    }

}
