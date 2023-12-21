package com.API.Pizzapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReseñaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date dateCreation;

    @Column(nullable = false, unique = true)
    private String author;

    @Column(nullable = false, unique = false)
    private String descripcion;

    @Column(nullable = false)
    private  int calificacion;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, unique = false)
    private String restaurante;
}


