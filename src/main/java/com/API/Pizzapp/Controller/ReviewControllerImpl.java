package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.ReseñaEntity;
import com.API.Pizzapp.Services.ReviewServiceI;
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



    private ReviewServiceI reviewServiceI;

    @Autowired
    public ReviewControllerImpl(ReviewServiceI reviewServiceI) {
        this.reviewServiceI = reviewServiceI;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/CreateReview")
    public ResponseEntity createReview(@RequestBody ReseñaEntity reseñaEntity) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            ReseñaEntity reseñaEntity1= reviewServiceI.createReview(reseñaEntity);
            return new ResponseEntity(reseñaEntity1, HttpStatus.CREATED);
        }catch  (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        }

    }



    @RequestMapping(method = RequestMethod.PATCH, path = "/UpdateReview/{id}")
    public ResponseEntity updateReview(@PathVariable Long id, @RequestBody  ReseñaEntity reseñaEntity) {
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
