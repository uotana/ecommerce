package br.com.letscode.ecommerce.shop.user;

import br.com.letscode.ecommerce.shop.product.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    static Specification<UserEntity> nameLike(String name) {
        return name == null ? null : (productEntity, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(productEntity.get("name"), "%" + name + "%");
    }
}
