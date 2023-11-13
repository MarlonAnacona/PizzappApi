package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.RestauranEntity;
import org.springframework.http.ResponseEntity;

public interface RestaurantControllerI {

    public ResponseEntity createRestaurant(RestauranEntity reseñaEntity);


    public ResponseEntity getAllRestaurant();

    public ResponseEntity getRestaurantId(Long id);
}
