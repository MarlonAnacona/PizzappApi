package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.ReseniaDTO;
import com.API.Pizzapp.Models.ReseñaEntity;
import com.API.Pizzapp.Security.JwtAuthenticationFilter;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.ReviewServiceI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/Review")
@CrossOrigin("*")
public class ReviewControllerImpl implements ReviewControllerI {



     ReviewServiceI reviewServiceI;

     JwtAuthenticationFilter jwtAutorizationFilter;

     JwtService jwtService;


    @Autowired
    public ReviewControllerImpl(ReviewServiceI reviewServiceI, JwtAuthenticationFilter jwtAutorizationFilter, JwtService jwtService) {
        this.reviewServiceI = reviewServiceI;
        this.jwtAutorizationFilter = jwtAutorizationFilter;
        this.jwtService = jwtService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/CreateReview")
    public ResponseEntity createReview(HttpServletRequest request, @RequestBody ReseniaDTO reseniaDTO) {
        try{
            String token= jwtAutorizationFilter.getTokenFromRequest(request);
            String email= jwtService.getUsernameFromToken(token);
            reseniaDTO.setEmail(email);

            ReseñaEntity reseñaEntity1= reviewServiceI.createReview(reseniaDTO);
            return new ResponseEntity(reseñaEntity1, HttpStatus.CREATED);
        }catch  (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        }

    }



    @RequestMapping(method = RequestMethod.PATCH, path = "/UpdateReview/{id}")
    public ResponseEntity updateReview(@PathVariable Long id, @RequestBody  ReseniaDTO reseñaEntity) {
        try{
            return new ResponseEntity(reviewServiceI.updateReview(id,reseñaEntity),HttpStatus.ACCEPTED);
        }catch ( Exception e){
         return  new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/GetReview/{id}")
    public ResponseEntity getReviewById(Long id) {
      try{
          return  new ResponseEntity(reviewServiceI.getReviewById(id),HttpStatus.FOUND);
      }catch ( Exception e){
          return  new ResponseEntity(HttpStatus.NOT_FOUND);
      }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/GetAllReviews")
    public ResponseEntity getAllReview() {
        try{
            return  new ResponseEntity(reviewServiceI.getAllReview(),HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FOUND);
        }
    }


}
