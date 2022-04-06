package br.com.letscode.ecommerce.shop.product;

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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final EntityManager entityManager;


    public ProductEntity save(ProductRequest product) {

        Optional<ManufacturerEntity> manufacturer = manufacturerRepository.findById(product.getIdManufacturer());
        if (manufacturer.isPresent()) {
            ProductEntity productEntity = toEntity(product, manufacturer.get());
            return productRepository.save(productEntity);
        } else {
            throw new NoSuchElementException("Manufacturer not found.");
        }
    }

    public Object save(Long id, ProductRequest product) {

        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        Optional<ManufacturerEntity> manufacturerEntityOptional = manufacturerRepository.findById(product.getIdManufacturer());

        if (productEntityOptional.isPresent() && manufacturerEntityOptional.isPresent()) {

            ProductEntity productEntity = new ProductEntity();
            BeanUtils.copyProperties(product, productEntity);
//           productEntity.setCode(productEntityOptional.get().getCode());
            productEntity.setId(productEntityOptional.get().getId());
            productEntity.setCode(productEntityOptional.get().getCode());
            productEntity.setStatus(productEntityOptional.get().getStatus());
            productEntity.setManufacturer(manufacturerEntityOptional.get());
            productEntity.setUpdateDate(ZonedDateTime.now());
            return productRepository.save(productEntity);

        } else {
            throw new RuntimeException("Not found");
        }
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
        ProductEntity productEntity = productEntityOptional.orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        productRepository.delete(productEntity);

        return "Product successfully deleted.";

    }
}
