package br.com.letscode.ecommerce.shop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DefaultError {
    private Integer status;
    private String message;
    private Long timestamp;
}
