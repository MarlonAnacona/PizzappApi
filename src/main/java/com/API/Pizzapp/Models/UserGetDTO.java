package com.API.Pizzapp.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDTO {

private String nombre;
private String apellido;
    private String nombreUsuario;
    private String email;
    private byte[] profilePicture;
}
