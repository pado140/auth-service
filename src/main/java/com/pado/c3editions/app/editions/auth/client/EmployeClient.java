package com.pado.c3editions.app.editions.auth.client;

import com.pado.c3editions.app.editions.auth.dtos.EmployeDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.security.Principal;

@HttpExchange
public interface EmployeClient {

    @GetExchange("/hr/public/u/{user_id}")
    public EmployeDto getbyuser(@PathVariable("user_id") long employe_id);
}
