package com.pado.c3editions.app.editions.auth.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class NotificationsDto implements Serializable {
    private  Long id;
    private  String message;
    private  String titre;
    private  String lien;
    private  boolean isread;
    private  UsersDto user;
    private  UsersDto createdby;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private  LocalDateTime created;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private  LocalDateTime modified;
}
