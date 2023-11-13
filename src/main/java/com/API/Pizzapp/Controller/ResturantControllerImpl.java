package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.RestauranEntity;
import com.API.Pizzapp.Services.RestauranteServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/Restaurant")
@CrossOrigin("*")
public class ResturantControllerImpl implements  RestaurantControllerI{


    RestauranteServiceI restauranteServiceI;

    @Autowired
    public ResturantControllerImpl(RestauranteServiceI restauranteServiceI) {
        this.restauranteServiceI = restauranteServiceI;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/CreateRestaurant")
    public ResponseEntity createRestaurant(RestauranEntity restauranEntity) {
        try{
            return  new ResponseEntity(restauranteServiceI.createRestaurant(restauranEntity),HttpStatus.CREATED);

        }catch ( Exception e){
            return  new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getAllRestaurants")
    public ResponseEntity getAllRestaurant() {
        try{
            return new ResponseEntity( restauranteServiceI.getAllRestaurant(), HttpStatus.FOUND);

        }catch ( Exception e){
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getResturant/{id}")
    public ResponseEntity getRestaurantId(Long id) {
        try{
            return new ResponseEntity(restauranteServiceI.getRestaurantId(id),HttpStatus.FOUND);

        }catch ( Exception e){
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }    }
}
