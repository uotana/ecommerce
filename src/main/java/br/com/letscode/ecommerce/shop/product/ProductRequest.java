package br.com.letscode.ecommerce.shop.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductRequest {

    //    @NotNull
    private String name;

    private String description;
    //    @NotNull
    private BigDecimal value;
    //    @NotNull
    private String barCode;
    //    @NotNull
    private Long idManufacturer;

    private Integer weight;

    private String weightMeasuringUnit;

    public ProductRequest(String name,
                          BigDecimal value,
                          String barCode,
                          Long idManufacturer) {
        this.name = name;
        this.value = value;
        this.barCode = barCode;
        this.idManufacturer = idManufacturer;
    }
}
