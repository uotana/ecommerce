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
    public ResponseEntity<CartEntity> addProduct(@RequestBody CartRequest cartRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.addProduct(cartRequest));
    }

    @DeleteMapping
    public ResponseEntity<CartEntity> deleteProduct(@RequestBody CartRequest cartRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteProduct(cartRequest));
    }

}
