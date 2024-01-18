package com.pado.c3editions.app.editions.auth.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;
}
