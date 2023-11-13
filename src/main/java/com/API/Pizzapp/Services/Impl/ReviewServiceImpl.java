package com.API.Pizzapp.Services.Impl;

import com.API.Pizzapp.Models.ReseñaEntity;
import com.API.Pizzapp.Repository.ReviewRepository;
import com.API.Pizzapp.Services.ReviewServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewServiceI {


    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReseñaEntity createReview(ReseñaEntity reseñaEntity) {

        return reviewRepository.save(reseñaEntity);
    }

    public ReseñaEntity updateReview(Long id,ReseñaEntity reseñaEntity) {
    Optional<ReseñaEntity> reseñaEntity1= reviewRepository.findById(id);
    if(!reseñaEntity1.isEmpty()){
     return    reviewRepository.save(reseñaEntity);
    }
    throw new RuntimeException("No se encontro usuario");
    }

    @Override
    public List<ReseñaEntity> getAllReview() {
        return reviewRepository.findAll();
    }

    @Override
    public ReseñaEntity getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada "));
    }

}
