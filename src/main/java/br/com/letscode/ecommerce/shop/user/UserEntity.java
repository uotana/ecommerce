package br.com.letscode.ecommerce.shop.user;

import br.com.letscode.ecommerce.shop.cart.CartEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity(name = "CLIENT")
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name="BIRTH_DATE")
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name="CART")
    private CartEntity cart;

    @Column(name = "CREATION_DATE")
    private ZonedDateTime creationDate;

    @Column(name = "UPDATE_DATE")
    private ZonedDateTime updateDate;
}
