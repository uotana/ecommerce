package br.com.letscode.ecommerce.shop.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request
    )throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    ));
        }catch (DisabledException e){
            throw new Exception("Disabled user", e);
        } catch (BadCredentialsException e){
            throw new Exception("Invalid credentials", e);
        }

        UserDetails userDetails = userDetailsServiceImpl
                .loadUserByUsername(request.getUsername());

        final String token = tokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(
                AuthResponse.builder().token(token).build());
    }
}
