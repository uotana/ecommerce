package br.com.letscode.ecommerce.productTest;

import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerEntity;
import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerRepository;
import br.com.letscode.ecommerce.shop.product.ProductEntity;
import br.com.letscode.ecommerce.shop.product.ProductRepository;
import br.com.letscode.ecommerce.shop.product.ProductRequest;
import br.com.letscode.ecommerce.shop.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductService productService;

    private ProductService service;

    private void createProduct() {

    }

    @BeforeEach
    void initTest() {
        service = new ProductService(productRepository,
                manufacturerRepository,
                entityManager);
    }

    @Test
    void GetAllProductsFromRepository() {

        ManufacturerEntity manufacturerEntity1 = new ManufacturerEntity();
        manufacturerEntity1.setName("Manufacturer X");
        manufacturerEntity1.setCreationDate(ZonedDateTime.now());
        manufacturerEntity1.setUpdateDate(ZonedDateTime.now());
        manufacturerEntity1 = manufacturerRepository.save(manufacturerEntity1);

        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setName("Product Y");
        productEntity1.setValue(BigDecimal.TEN);
        productEntity1.setBarCode("655396");
        productEntity1.setManufacturer(manufacturerEntity1);
        productRepository.save(productEntity1);

        ManufacturerEntity manufacturerEntity2 = new ManufacturerEntity();
        manufacturerEntity2.setName("Manufacturer Y");
        manufacturerEntity2.setCreationDate(ZonedDateTime.now());
        manufacturerEntity2.setUpdateDate(ZonedDateTime.now());
        manufacturerEntity2 = manufacturerRepository.save(manufacturerEntity2);

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setName("Product X");
        productEntity2.setValue(BigDecimal.TEN);
        productEntity2.setBarCode("655396");
        productEntity2.setManufacturer(manufacturerEntity2);
        productRepository.save(productEntity2);

        Page<ProductEntity> result = productRepository.findAll(PageRequest.of(0, 2));
        Assert.isTrue(!result.isEmpty(), "Expected size: 2, Result:" + result.getSize());

    }

    @Test
    void onCreateProductSuccessfully() {

        ManufacturerEntity manufacturerEntity = new ManufacturerEntity();
        manufacturerEntity.setName("Manufacturer X");
        manufacturerEntity.setCreationDate(ZonedDateTime.now());
        manufacturerEntity.setUpdateDate(ZonedDateTime.now());

        manufacturerEntity = manufacturerRepository.save(manufacturerEntity);

        ProductEntity productEntity = new ProductEntity();

        productEntity.setName("Product X");
        productEntity.setValue(BigDecimal.TEN);
        productEntity.setBarCode("655396");
        productEntity.setManufacturer(manufacturerEntity);

        productRepository.save(productEntity);

        Assert.notNull(productEntity.getName(), "Name null");
        Assert.notNull(productEntity.getValue(), "Value null");
        Assert.notNull(productEntity.getBarCode(), "Bar code null");
        Assert.notNull(productEntity.getManufacturer(), "Manufacturer null");

    }

    @Test
    void updateProductWithoutManufacturer() {

        ManufacturerEntity manufacturerEntity1 = new ManufacturerEntity();
        manufacturerEntity1.setName("Manufacturer X");
        manufacturerEntity1.setCreationDate(ZonedDateTime.now());
        manufacturerEntity1.setUpdateDate(ZonedDateTime.now());
        manufacturerEntity1 = manufacturerRepository.save(manufacturerEntity1);

        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setName("Product Y");
        productEntity1.setValue(BigDecimal.ONE);
        productEntity1.setBarCode("655396");
        productEntity1.setManufacturer(manufacturerEntity1);
        productRepository.save(productEntity1);

        log.info("Before update " + productEntity1);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Product X");
        productRequest.setValue(BigDecimal.TEN);
        productRequest.setBarCode("12345");
        productRequest.setIdManufacturer(1L);

        var productUpdated = productService.update(productEntity1.getId(), productRequest);

        log.info("After updated " + productRepository.save(productUpdated));
        Assertions.assertThat(productUpdated.getManufacturer()).isNotNull();
        Assert.notNull(productUpdated.getManufacturer(), "Manufacturer is null.");

    }
}
