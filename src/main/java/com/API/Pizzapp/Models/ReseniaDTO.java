package com.API.Pizzapp.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReseniaDTO {



    private Date dateCreation;

    private String author;

    private String descripcion;

    private  int calificacion;

    private String email;

    private String restaurante;
}
