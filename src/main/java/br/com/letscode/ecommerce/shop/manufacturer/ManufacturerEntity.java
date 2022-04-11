package br.com.letscode.ecommerce.shop.manufacturer;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity(name = "MANUFACTURER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManufacturerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATION_DATE")
    private ZonedDateTime creationDate;

    @Column(name = "UPDATE_DATE")
    private ZonedDateTime updateDate;

}
