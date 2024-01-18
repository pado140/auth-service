package com.pado.c3editions.app.editions.auth.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Objects;

@Component
@Aspect
public class ControllerBehavior {

//    @Autowired
//    private GlobalService service;
//    @Autowired
//    private UserService userService;
//
//    @Pointcut("within(com.c3editions.app.editions.controller.*)")
//    public void methodAfter(){
//        System.out.println("after");
//    }
//
//    @After("methodAfter()")
//    public void action(JoinPoint jp){
//        System.out.println(jp.getSignature());
//        var logbuilder=GlobalLogs
//                .builder();
//        Users connectedUser=null;
//        var path=false;
//        for (Object arg : jp.getArgs()) {
//            if(arg instanceof HttpServletRequest){
//                var request=((HttpServletRequest)arg);
//                logbuilder.method(request.getMethod());
//                logbuilder.path(request.getServletPath());
//                path=true;
//            }
//            if(arg instanceof Principal){
//                var connected=((Principal)arg);
//                connectedUser=userService.getByUsername(connected.getName());
//            }
//        }
//        if(Objects.nonNull(connectedUser) && path) {
//            GlobalLogs log = logbuilder.build();
//            log.setCreatedby(connectedUser);
//            if (!log.getPath().isBlank()) {
//                service.save(log);
//            }
//        }
//    }
}
