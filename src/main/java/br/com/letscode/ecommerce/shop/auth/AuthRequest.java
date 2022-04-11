package br.com.letscode.ecommerce.shop.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
