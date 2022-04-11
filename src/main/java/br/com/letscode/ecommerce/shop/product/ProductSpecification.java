package br.com.letscode.ecommerce.shop.product;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    static Specification<ProductEntity> nameLike(String name) {
        return name == null ? null : (productEntity, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(productEntity.get("name"), "%" + name + "%");
    }

    static Specification<ProductEntity> valueLessThanOrEqualTo(BigDecimal value) {
        return value == null ? null : (productEntity, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                lessThanOrEqualTo(productEntity.get("value"), value);
    }

    static Specification<ProductEntity> valueGreaterThanOrEqualTo(BigDecimal value) {
        return value == null ? null : (productEntity, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                greaterThanOrEqualTo(productEntity.get("value"), value);
    }

/*    static Specification<ProductEntity> categoryLike(String category) {
        return category == null ? null : (productEntity, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                equal(productEntity.get("category"), category);
    }*/
}
