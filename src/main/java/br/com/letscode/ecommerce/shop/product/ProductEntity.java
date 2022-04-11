package br.com.letscode.ecommerce.shop.product;

import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerEntity;
import br.com.letscode.ecommerce.shop.product.utils.ProductStatusEnum;
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
@AllArgsConstructor
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductEntity {

    @Id
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
    private ProductStatusEnum status;

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

}
