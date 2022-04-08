package br.com.letscode.ecommerce.shop.cartitem;

import br.com.letscode.ecommerce.shop.product.ProductEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity(name="CART_ITEM")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

//    @OneToOne
//    @JoinColumn(name="CART_ID")
//    private Long cartId;

    @OneToOne
    @JoinColumn(name="PRODUCT_ID")
    private ProductEntity product;

    @Column(name="QUANTITY")
    private Integer quantity = 0;

    @Column(name = "CREATION_DATE")
    private ZonedDateTime creationDate;

    @Column(name = "UPDATE_DATE")
    private ZonedDateTime updateDate;
}
