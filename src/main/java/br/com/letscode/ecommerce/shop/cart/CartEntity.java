package br.com.letscode.ecommerce.shop.cart;

import br.com.letscode.ecommerce.shop.product.ProductEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name="CART")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CREATION_DATE")
    private ZonedDateTime creationDate;

    @Column(name = "UPDATE_DATE")
    private ZonedDateTime updateDate;

    @OneToMany
    @JoinColumn(name="PRODUCT")
    private List<ProductEntity> products;
}
