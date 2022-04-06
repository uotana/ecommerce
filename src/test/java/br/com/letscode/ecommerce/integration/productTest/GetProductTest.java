package br.com.letscode.ecommerce.integration.productTest;

import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerEntity;
import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerRepository;
import br.com.letscode.ecommerce.shop.product.ProductEntity;
import br.com.letscode.ecommerce.shop.product.ProductRepository;
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
import java.util.List;

@AutoConfigureTestDatabase
//@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class GetProductTest {

/*    @MockBean
    private ManufacturerRepository manufacturerRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;*/

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
    void onGetAllProductsFromRepository() {
        var manufacturerEntity = new ManufacturerEntity();
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

        List response = productRepository.findAll();

        Assert.isTrue(response.size() == 1,
                "Expected size: 1, Result:" + response.size());
    }

}
