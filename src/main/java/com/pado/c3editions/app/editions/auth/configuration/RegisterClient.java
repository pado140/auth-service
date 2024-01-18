package com.pado.c3editions.app.editions.auth.configuration;

import com.pado.c3editions.app.editions.auth.client.EmployeClient;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RegisterClient {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;
    @Bean
    public WebClient EmployeWebClient(){
        return WebClient
                .builder()
                .baseUrl("lb://HR")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public EmployeClient employeClient(){
        HttpServiceProxyFactory httpServiceProxyFactory=
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(EmployeWebClient())).build();
        return httpServiceProxyFactory.createClient(EmployeClient.class);
    }

    private ExchangeFilterFunction authentificationFilter(){
        return (clientRequest,exchangeFunction)->{
            String jwttoken=clientRequest.headers().get(HttpHeaders.AUTHORIZATION).get(0);
            System.out.println(jwttoken);
            return exchangeFunction.exchange(clientRequest);
        };
    }
}
