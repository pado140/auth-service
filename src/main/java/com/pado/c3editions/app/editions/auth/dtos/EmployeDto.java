package com.pado.c3editions.app.editions.auth.dtos;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EmployeDto implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private SEXE sexe;
    private String telephone;
    private String mail;
    private String adresse;
    private String nif;
    private String niu;
    private MATRIMONIAL matrimonial;

    private String photo;
    private EMPLOYE_STATUS status;

    private SuccursaleDto succursale;

}
