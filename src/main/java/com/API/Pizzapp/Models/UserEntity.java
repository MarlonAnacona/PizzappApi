package com.API.Pizzapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nombreUsuario;

    @Column(nullable = false)
    private String password;

    private String apellido;

    @OneToMany(mappedBy = "user")
    private List<ReseñaEntity> reseñas = new ArrayList<>();

    @Column (name="profile_picture")
    private  byte[]profilePicture;


    @Column(columnDefinition = "boolean default false")
    private boolean isBlocked;



    @Column(columnDefinition = "boolean default true")
    private boolean isActive;

    private LocalDateTime blockExpiryTime;

    @Column(columnDefinition = "int default 0")
    private int attemptCount ;

}
