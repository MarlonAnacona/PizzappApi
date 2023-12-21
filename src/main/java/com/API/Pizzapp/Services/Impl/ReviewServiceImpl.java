package com.API.Pizzapp.Services.Impl;

import com.API.Pizzapp.Models.ReseniaDTO;
import com.API.Pizzapp.Models.ReseniaUserDTO;
import com.API.Pizzapp.Models.ReseñaEntity;
import com.API.Pizzapp.Models.UserEntity;
import com.API.Pizzapp.Repository.ReviewRepository;
import com.API.Pizzapp.Repository.UserRepository;
import com.API.Pizzapp.Services.ReviewServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewServiceI {


    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }
    @Override
    public ReseñaEntity createReview(ReseniaDTO reseñaDTO) {
        ReseñaEntity reseñaEntity = new ReseñaEntity();

        reseñaEntity.setDateCreation(reseñaDTO.getDateCreation());
        reseñaEntity.setDescripcion(reseñaDTO.getDescripcion());
        reseñaEntity.setCalificacion(reseñaDTO.getCalificacion());
        reseñaEntity.setRestaurante(reseñaDTO.getRestaurante());
        UserEntity userEntity = userRepository.findByEmail(reseñaDTO.getEmail()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        reseñaEntity.setAuthor(userEntity.getNombreUsuario());

        reseñaEntity.setUser(userEntity);

        return reviewRepository.save(reseñaEntity);
    }


    @Override
    public ReseñaEntity updateReview(Long id,ReseniaDTO reseñaEntity) {
        ReseñaEntity reseñaEntity1 = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la reseña con el id: " + id));

        // Actualizando los campos
        reseñaEntity1.setDateCreation(reseñaEntity.getDateCreation());
        reseñaEntity1.setAuthor(reseñaEntity.getAuthor());
        reseñaEntity1.setDescripcion(reseñaEntity.getDescripcion());
        reseñaEntity1.setCalificacion(reseñaEntity.getCalificacion());
        reseñaEntity1.setRestaurante(reseñaEntity.getRestaurante());

        // Aquí puedes agregar lógica para actualizar el usuario si es necesario

        return reviewRepository.save(reseñaEntity1);
    }


    @Override
    public List<ReseniaUserDTO> getAllReview() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReseniaUserDTO getReviewById(Long id) {
        ReseñaEntity reseña = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada"));
        return convertToDTO(reseña);
    }

    private ReseniaUserDTO convertToDTO(ReseñaEntity reseña) {
        ReseniaUserDTO dto = new ReseniaUserDTO();
        // Copia la información de la reseña y del usuario al DTO
        UserEntity user = reseña.getUser();
        if (user != null) {
            dto.setNombreUsuario(user.getNombreUsuario());
            dto.setNombre(user.getNombre());
            dto.setApellido(user.getApellido());
            dto.setEmail(user.getEmail());
            dto.setProfilePicture(user.getProfilePicture());
        }
        dto.setId(reseña.getId());
        dto.setAuthor(reseña.getAuthor());
        dto.setCalificacion(reseña.getCalificacion());
        dto.setRestaurante(reseña.getRestaurante());
        dto.setDescripcion(reseña.getDescripcion());
        dto.setDateCreation(reseña.getDateCreation());
        return dto;
    }
}
