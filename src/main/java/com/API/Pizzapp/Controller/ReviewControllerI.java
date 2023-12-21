package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.ReseniaDTO;
import com.API.Pizzapp.Models.ReseñaEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ReviewControllerI {



    public ResponseEntity createReview(HttpServletRequest request,ReseniaDTO reseñaEntity);

    public ResponseEntity updateReview(Long id,ReseniaDTO reseñaEntity);

    public ResponseEntity getAllReview();

    public ResponseEntity getReviewById(Long id);
}
