package br.com.letscode.ecommerce.shop.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductFilter {
    private String name;
    private BigDecimal minValue;
    private BigDecimal maxValue;
//    private String category;
}
