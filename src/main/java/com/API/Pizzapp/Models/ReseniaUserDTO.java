package com.API.Pizzapp.Models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReseniaUserDTO {


    private Long id;
    private Date dateCreation;

    private String author;

    private String descripcion;

    private  int calificacion;

    private String email;

    private String restaurante;

    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private byte[] profilePicture; // Puede dejarse vacío si así lo deseas

}
