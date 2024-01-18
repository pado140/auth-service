package com.pado.c3editions.app.editions.auth.configuration;


import com.pado.c3editions.app.editions.auth.security.UserAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import com.pado.c3editions.app.editions.auth.security.AuthFilter;
import com.pado.c3editions.app.editions.auth.security.ApiFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@RequiredArgsConstructor
public class SecurityCustomConf{

    @Autowired
    private final AuthenticationProvider daoAuthenticationProvider;
//    @Autowired
//    private AuthenticationManager authenticationManager2;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable().cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/metrics","/metrics/**","/actuator/**","/public/**"
                        ,"/auth/authenticate"
//                        ,"/auth/logged-user"
                        ,"/auth/public/**",
                        "/swagger-ui/**","/swagger-resources/**","/v2/api-docs","/v3/api-docs","/error","/refresh-token","/login")
                .permitAll()
                .requestMatchers(HttpMethod.OPTIONS,"/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
//                .authenticationManager()
                .authenticationProvider(daoAuthenticationProvider)
//                .addFilter(new AuthFilter())
                .addFilterBefore(new ApiFilter(), UsernamePasswordAuthenticationFilter.class).build();

//        super.configure(http);
    }

    @Bean
    protected AuthenticationManager authenticationManager2(AuthenticationConfiguration conf) throws Exception {
        // TODO Auto-generated method stub
//        conf.authenticationManagerBuilder().

        return conf.getAuthenticationManager();
    }

}
