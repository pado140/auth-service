package com.pado.c3editions.app.editions.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.c3editions.app.editions.auth.configuration.JWTHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String authorization=request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(authorization);

//        try{
//            String token=authorization.split(" ")[1];
//            Algorithm algorithm=Algorithm.HMAC256(JWTHelper.secretKey.getBytes());
//
//            JWTVerifier verifier= JWT.require(algorithm).build();
//            DecodedJWT decodedJWT= verifier.verify(token);
//            String username= decodedJWT.getSubject();
//            String[]  Authorities=decodedJWT.getClaim("Authorities").asArray(String.class);
//            List<GrantedAuthority> autorities= Arrays.stream(Authorities).map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
//            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
//                    username,null,autorities
//            );
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            response.setHeader("JWT",authorization);
//        }catch ( Exception exception){
////            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            new ObjectMapper().writeValue(response.getOutputStream(), Map.of("error_message", exception.getMessage()));
//        }
    }
}
