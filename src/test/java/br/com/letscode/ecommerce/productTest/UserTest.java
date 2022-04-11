package br.com.letscode.ecommerce.integration.productTest;

import br.com.letscode.ecommerce.shop.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addUserSuccessfully() throws Exception{
        UserRequest userRequest = new UserRequest();
        userRequest.setName("joao silva");
        userRequest.setUsername("joao");
        userRequest.setPassword("$2a$12$VQ33tXWF.QuHelLQBl0VWOA8r7ig2uZ8aWbssyUGZeRcQYq8RRfIe");
        ObjectMapper objectMapper = new ObjectMapper();
        String userRequestAsString = objectMapper.writeValueAsString(userRequest);
        mockMvc.perform(post("/v1/users/create").contentType(MediaType.APPLICATION_JSON)
                .content(userRequestAsString))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
