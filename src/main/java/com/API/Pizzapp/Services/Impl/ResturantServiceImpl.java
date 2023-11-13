package com.API.Pizzapp.Services.Impl;

import com.API.Pizzapp.Models.RestauranEntity;
import com.API.Pizzapp.Repository.ResturantRepository;
import com.API.Pizzapp.Services.RestauranteServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResturantServiceImpl implements RestauranteServiceI {


    private ResturantRepository resturantRepository;

    public ResturantServiceImpl(ResturantRepository resturantRepository) {
        this.resturantRepository = resturantRepository;
    }

    @Override
    public RestauranEntity createRestaurant(RestauranEntity restauranEntity) {
        return resturantRepository.save(restauranEntity);
    }

    @Override
    public List<RestauranEntity> getAllRestaurant() {
        return resturantRepository.findAll();
    }

    @Override
    public RestauranEntity getRestaurantId(Long id) {
        return resturantRepository.findById(id).orElseThrow((() -> new EntityNotFoundException("Restaurante no encontrado ")));
    }
}
