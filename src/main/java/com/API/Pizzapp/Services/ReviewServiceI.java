package com.API.Pizzapp.Services;

import com.API.Pizzapp.Models.ReseñaEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewServiceI {

    public ReseñaEntity createReview(ReseñaEntity reseñaEntity);
    public ReseñaEntity updateReview(Long id,ReseñaEntity reseñaEntity);
    public List<ReseñaEntity> getAllReview();

    public ReseñaEntity getReviewById(Long id);
}
