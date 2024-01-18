package com.pado.c3editions.app.editions.auth.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UsersDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String username;
    private String password;
    private String recover;
    private boolean locked;
    private boolean disabled;
    private boolean expired;
    private boolean changepassword;
    private RolesDto role;
    private String customid;
    private String permission;
    private EmployeDto employe;
}
