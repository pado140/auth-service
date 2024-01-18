package com.pado.c3editions.app.editions.auth.logs;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LogsDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String action;
    private String desciption;
    private Class<?> clazz;
    private Long livre;
    private Long projet;
    private UserDto user;

    @Data
    public static class UserDto implements Serializable {
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
        private String permission;
    }
}
