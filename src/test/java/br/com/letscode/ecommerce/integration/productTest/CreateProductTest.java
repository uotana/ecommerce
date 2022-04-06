package br.com.letscode.ecommerce.integration.productTest;

import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerEntity;
import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerRepository;
import br.com.letscode.ecommerce.shop.product.ProductEntity;
import br.com.letscode.ecommerce.shop.product.ProductRepository;
import br.com.letscode.ecommerce.shop.product.ProductRequest;
import br.com.letscode.ecommerce.shop.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.ZonedDateTime;


@AutoConfigureTestDatabase
//@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class CreateProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private EntityManager entityManager;

    private ProductService service;

    @BeforeEach
    void initTest() {
        service = new ProductService(productRepository,
                manufacturerRepository,
                entityManager);
    }

    @Test
    void onCreateProductSuccessfully() {

        var manufacturerEntity = new ManufacturerEntity();
        manufacturerEntity.setName("Manufacturer X");
        manufacturerEntity.setCreationDate(ZonedDateTime.now());
        manufacturerEntity.setUpdateDate(ZonedDateTime.now());
        manufacturerRepository.save(manufacturerEntity);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Product X");
        productRequest.setValue(BigDecimal.TEN);
        productRequest.setBarCode("12675346");
        productRequest.setIdManufacturer(manufacturerEntity.getId());

        ProductEntity productEntity = service.save(productRequest);
        log.info(productEntity.toString());


        Assert.notNull(productEntity.getName(), "Name null");
        Assert.notNull(productEntity.getValue(), "Value null");
        Assert.notNull(productEntity.getBarCode(), "Bar code null");
        Assert.notNull(productEntity.getManufacturer(), "Manufacturer null");

    }
}
