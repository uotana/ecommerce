package br.com.letscode.ecommerce.shop.product;

import br.com.letscode.ecommerce.shop.exception.ManufacturerNotFoundException;
import br.com.letscode.ecommerce.shop.exception.ProductNotFoundException;
import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerEntity;
import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerRepository;
import br.com.letscode.ecommerce.utils.ProductStatus;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final EntityManager entityManager;


    public ProductEntity save(ProductRequest product) {

        Optional<ManufacturerEntity> manufacturerEntityOptional = manufacturerRepository
                .findById(product.getIdManufacturer());

        ManufacturerEntity manufacturer = manufacturerEntityOptional
                .orElseThrow(() -> new ManufacturerNotFoundException("Manufacturer with id "
                        + product.getIdManufacturer() + " not found."));

        ProductEntity productEntity = toEntity(product, manufacturer);
        return productRepository.save(productEntity);

    }

    public ProductEntity update(Long productId, ProductRequest productRequest) {

        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        Optional<ManufacturerEntity> manufacturerEntityOptional = manufacturerRepository
                .findById(productRequest.getIdManufacturer());

        ProductEntity product = productEntityOptional
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found."));

        ManufacturerEntity manufacturer = manufacturerEntityOptional
                .orElseThrow(() -> new ManufacturerNotFoundException("Manufacturer with id "
                        + productRequest.getIdManufacturer() + " not found."));

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productRequest, productEntity);
        productEntity.setId(product.getId());
        productEntity.setCode(product.getCode());
        productEntity.setStatus(product.getStatus());
        productEntity.setManufacturer(manufacturer);
        productEntity.setCreationDate(product.getCreationDate());
        productEntity.setUpdateDate(ZonedDateTime.now());

        return productRepository.save(productEntity);

    }

    private ProductEntity toEntity(ProductRequest product, ManufacturerEntity manufacturer) {
        ProductEntity entity = new ProductEntity();
        BeanUtils.copyProperties(product, entity);

        entity.setCode(UUID.randomUUID());
        entity.setStatus(ProductStatus.AVAILABLE);
        entity.setCreationDate(ZonedDateTime.now());
        entity.setUpdateDate(ZonedDateTime.now());
        entity.setManufacturer(manufacturer);

        return entity;

    }

    public Page<ProductEntity> findAll(ProductFilter filter,
                                       Pageable pageable
    ) {
        return productRepository.findAll(
                Specification.where(ProductSpecification.nameLike(filter.getName()))
                        .and(ProductSpecification.valueGreaterThanOrEqualTo(filter.getMaxValue()))
                        .and(ProductSpecification.valueLessThanOrEqualTo(filter.getMinValue())), pageable
        );
    }

    public String delete(Long id) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        ProductEntity productEntity = productEntityOptional
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        productRepository.delete(productEntity);

        return "Product successfully deleted.";

    }
}
