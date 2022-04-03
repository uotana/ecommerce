package br.com.letscode.ecommerce.shop.product;

import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerEntity;
import br.com.letscode.ecommerce.utils.ProductStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "PRODUCT")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductEntity {

    @Id
//    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CODIGO")
    private UUID code;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "VALOR")
    private BigDecimal value;

    @Column(name = "CODIGO_BARRA")
    private String barCode;

    @Column(name = "STATUS")
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "ID_MANUFACTURER")
    private ManufacturerEntity manufacturer;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Column(name = "WEIGHT_MEASURING_UNIT")
    private String weightMeasuringUnit;

    @Column(name = "CREATION_DATE")
    private ZonedDateTime creationDate;

    @Column(name = "UPDATE_DATE")
    private ZonedDateTime updateDate;

   /* public ProductEntity(
            String name,
            String description,
            BigDecimal value,
            String barCode,
            ManufacturerEntity manufacturer,
            Integer weight,
            String weightMeasuringUnit) {

        this.name = name;
        this.description = description;
        this.value = value;
        this.barCode = barCode;
        this.weight = weight;
        this.weightMeasuringUnit = weightMeasuringUnit;

        this.manufacturer = manufacturer;
        this.code = UUID.randomUUID();
        this.status = ProductStatus.AVAILABLE;
        this.creationDate = ZonedDateTime.now();
        this.updateDate = ZonedDateTime.now();
    }*/

}
