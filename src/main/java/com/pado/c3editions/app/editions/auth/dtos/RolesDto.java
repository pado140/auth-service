package com.pado.c3editions.app.editions.auth.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RolesDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String name;
}
