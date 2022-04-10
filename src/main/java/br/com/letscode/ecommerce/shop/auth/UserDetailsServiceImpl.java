package br.com.letscode.ecommerce.shop.auth;

import br.com.letscode.ecommerce.shop.user.UserEntity;
import br.com.letscode.ecommerce.shop.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username "+ username + " not found."));

        return new User(username, userEntity.getPassword(),
                new ArrayList<>());
//        return repository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Username "+ username + " not found."));
    }
}
