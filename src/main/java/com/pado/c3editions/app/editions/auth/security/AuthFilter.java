package com.pado.c3editions.app.editions.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.c3editions.app.editions.auth.configuration.JWTHelper;
import com.pado.c3editions.app.editions.auth.dtos.TokenDto;
import com.pado.c3editions.app.editions.auth.exceptions.LoginException;
import com.pado.c3editions.app.editions.auth.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.pado.c3editions.app.editions.auth.utils.MapperUtils;

import java.io.IOException;

public class AuthFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private AuthenticationManager authenticationManager2;
    @Autowired
    private UsersRepository userservice;
    @Autowired
    private MapperUtils util;

//    @Autowired
//    private JWTHelper jwtHelper;

//    public AuthFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserApp user=(UserApp)authResult.getPrincipal();
//        Algorithm algorithm=Algorithm.HMAC256(JWTHelper.secretKey.getBytes());

        String token= JWTHelper.generateToken(user,request.getRequestURL().toString(),false);

        String refresh_token= JWTHelper.generateToken(user,request.getRequestURL().toString(),true);
        System.out.println(token);
        response.setHeader("access_token",token);
        response.setHeader("refresh_token",refresh_token);
        response.addHeader("Authorization","Bearer "+token);

        response.setContentType("Application/json");
//        userservice.findByUsername(user.getUsername()).get();
//        new ObjectMapper().writeValue(response.getOutputStream(), Map.of("access_token",token,"refresh_token",refresh_token));
        new ObjectMapper().writeValue(response.getOutputStream(), TokenDto.builder().
                access_token(token).
                refresh_token(refresh_token).
                username(user.getUsername()).
//                user(util.asDTO(userservice.findByUsername(user.getUsername()).get(), UsersDto.class)).
                build()
        );

//        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("auth attempt");
        try {

            LoginRequest loginRequest=new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
//        System.out.println(request.getServletPath());
            String username= loginRequest.getUsername();//request.getParameter("username");;
            String password= loginRequest.getPassword();//request.getParameter("password");
            UsernamePasswordAuthenticationToken usertoken=new UsernamePasswordAuthenticationToken(username,password);
            return authenticationManager2.authenticate(usertoken);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error atuh");
//            throw new RuntimeException(e);
            throw new LoginException("Username ou password incorrect");
//            return null;
        }

    }

}
