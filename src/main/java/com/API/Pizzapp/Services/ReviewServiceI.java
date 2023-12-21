package com.API.Pizzapp.Services;

import com.API.Pizzapp.Models.ReseniaDTO;
import com.API.Pizzapp.Models.ReseniaUserDTO;
import com.API.Pizzapp.Models.ReseñaEntity;

import java.util.List;

public interface ReviewServiceI {

    public ReseñaEntity createReview(ReseniaDTO reseniaDTO);
    public ReseñaEntity updateReview(Long id,ReseniaDTO reseñaEntity);
    public List<ReseniaUserDTO> getAllReview();

    public ReseniaUserDTO getReviewById(Long id);
}
