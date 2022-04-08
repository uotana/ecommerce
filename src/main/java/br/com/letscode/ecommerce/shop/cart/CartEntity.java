package br.com.letscode.ecommerce.shop.cart;

import br.com.letscode.ecommerce.shop.cartitem.CartItemEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name="CART")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @Column(name = "CREATION_DATE")
    private ZonedDateTime creationDate;

    @Column(name = "UPDATE_DATE")
    private ZonedDateTime updateDate;

    @OneToMany
    @JoinColumn(name="CART_ID")
    private List<CartItemEntity> items = new ArrayList<>();
}
