package br.com.letscode.ecommerce.shop.user;

import br.com.letscode.ecommerce.shop.cart.CartEntity;
import br.com.letscode.ecommerce.shop.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public Object save(UserRequest request) {
        log.info("Saving request " + request);
        return userRepository.save(toEntity(request));
    }

    private UserEntity toEntity(UserRequest request) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(request, userEntity);

        CartEntity cart = new CartEntity();
        cart.setCreationDate(ZonedDateTime.now());
        cart.setUpdateDate(ZonedDateTime.now());

        userEntity.setCart(cartRepository.save(cart));
        userEntity.setCreationDate(ZonedDateTime.now());
        userEntity.setUpdateDate(ZonedDateTime.now());

        return userEntity;
    }
}
