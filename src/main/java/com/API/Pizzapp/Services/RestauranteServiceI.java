package com.API.Pizzapp.Services;

import com.API.Pizzapp.Models.RestauranEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestauranteServiceI {

    public RestauranEntity createRestaurant(RestauranEntity reseñaEntity);


    public List<RestauranEntity > getAllRestaurant();

    public RestauranEntity getRestaurantId(Long id);
}
