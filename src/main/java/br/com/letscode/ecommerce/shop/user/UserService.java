package br.com.letscode.ecommerce.shop.user;

import br.com.letscode.ecommerce.shop.cart.CartEntity;
import br.com.letscode.ecommerce.shop.cart.CartRepository;
import br.com.letscode.ecommerce.shop.exception.ProductNotFoundException;
import br.com.letscode.ecommerce.shop.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserResponse save(UserRequest userRequest) {
        Optional<UserEntity> user = userRepository.findByUsername(userRequest.getUsername());
        if (user.isPresent()) {
            throw new ProductNotFoundException("username " + user.get().getUsername() + " already exists, try another one.");
        }
        log.info("Saving request " + userRequest);

        return fromEntityToUserResponse(userRepository.save(toEntity(userRequest)));

    }

    public UserEntity update(Long id, UserRequest request) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        userEntityOptional.orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        log.info("Updating user with id " + id);
        UserEntity user = new UserEntity();
        user.setId(userEntityOptional.get().getId());
        user.setCart(userEntityOptional.get().getCart());
        user.setCreationDate(userEntityOptional.get().getCreationDate());
        user.setName(request.getName());
        user.setBirthDate(request.getBirthDate());
        user.setUpdateDate(ZonedDateTime.now());
        return userRepository.save(user);
    }

    private UserResponse fromEntityToUserResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userEntity, userResponse);
        return userResponse;
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

    public Page<UserEntity> findAll(UserFilter filter, Pageable pageable) {
        return userRepository.findAll(
                Specification.where(UserSpecification.nameLike(filter.getName()))
                , pageable);
    }

    public String delete(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        userEntityOptional.orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        userRepository.deleteById(id);
        cartRepository.deleteById(userEntityOptional.get().getCart().getCartId());
        return "User deleted.";
    }

    public UserEntity find(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }
}
