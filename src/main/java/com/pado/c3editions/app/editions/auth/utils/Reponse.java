package com.pado.c3editions.app.editions.auth.utils;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class Reponse<T> {
	private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String reson,path,developpermessage;
    private int statuscode;
    private Map<?,T> data;
}