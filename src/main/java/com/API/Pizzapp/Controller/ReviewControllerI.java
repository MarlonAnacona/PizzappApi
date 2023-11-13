package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.ReseñaEntity;
import org.springframework.http.ResponseEntity;

public interface ReviewControllerI {



    public ResponseEntity createReview(ReseñaEntity reseñaEntity);

    public ResponseEntity updateReview(Long id,ReseñaEntity reseñaEntity);

    public ResponseEntity getAllReview();

    public ResponseEntity getReviewById(Long id);
}
