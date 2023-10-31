package com.API.Pizzapp.Controller;


import com.API.Pizzapp.Models.AuthResponse;
import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.ResponseDTO;
import com.API.Pizzapp.Models.UserEntity;
import com.API.Pizzapp.Services.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/User")
@CrossOrigin("*")
public class UserControllerImp implements  UserControllerI{


    UserServiceI userServiceI;

    @Autowired
    public UserControllerImp(UserServiceI userServiceI) {
        this.userServiceI = userServiceI;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/createUser")
    public ResponseEntity createUser(@RequestBody UserEntity userEntity){

        AuthResponse responseDTO= new AuthResponse();
        try{

            responseDTO=(userServiceI.createUser(userEntity));

            return  new ResponseEntity(responseDTO,HttpStatus.CREATED );
        }catch (Exception e){
            return new ResponseEntity( HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @RequestMapping(method = RequestMethod.POST, path = "/loginUser")
       public ResponseEntity loginUser(@RequestBody LoginDTO loginDTO) {

        AuthResponse responseDTO;
        try {

            responseDTO = (userServiceI.loginUser(loginDTO));

            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }



    @RequestMapping(method = RequestMethod.PATCH, path = "/updateUser/{id}")
    public ResponseEntity updateUser(@PathVariable Long id,@RequestBody UserEntity userEntity) {

        UserEntity responseDTO;
        try {

            responseDTO = (userServiceI.updateUser(id,userEntity));

            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }




}
