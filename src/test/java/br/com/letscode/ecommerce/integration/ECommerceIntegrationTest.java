package br.com.letscode.ecommerce.integration;

import br.com.letscode.ecommerce.shop.manufacturer.ManufacturerRepository;
import br.com.letscode.ecommerce.shop.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ECommerceIntegrationTest {

    @MockBean
    private ManufacturerRepository manufacturerRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;



}
