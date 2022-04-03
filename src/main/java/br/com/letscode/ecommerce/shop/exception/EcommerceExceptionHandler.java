package br.com.letscode.ecommerce.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EcommerceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<DefaultError> cartNotFound(CartNotFoundException exception){
        DefaultError error = new DefaultError(HttpStatus.BAD_REQUEST.value(),
                                exception.getMessage(),
                                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<DefaultError> productNotFound(ProductNotFoundException exception){
        DefaultError error = new DefaultError(HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
