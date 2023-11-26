package com.API.Pizzapp.Controller;


import com.API.Pizzapp.Models.*;
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
            ResponseDTO responseDTO1= new ResponseDTO();
            responseDTO1.setResponse("Error al crear usuario");
            return new ResponseEntity<>(responseDTO1.getResponse(), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @RequestMapping(method = RequestMethod.POST, path = "/loginUser")
       public ResponseEntity loginUser(@RequestBody LoginDTO loginDTO) {

        AuthResponse responseDTO;
        try {

            responseDTO = (userServiceI.loginUser(loginDTO));

            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO1= new ResponseDTO();
            responseDTO1.setResponse("Error aL ingresar: "+ e.getMessage() );
            return new ResponseEntity<>(responseDTO1,HttpStatus.NOT_ACCEPTABLE);
        }
    }



    @RequestMapping(method = RequestMethod.PATCH, path = "/updateUser/{id}")
    public ResponseEntity updateUser(@PathVariable Long id,@RequestBody UserEntity userEntity) {

        UserEntity responseDTO;
        try {

            responseDTO = (userServiceI.updateUser(id,userEntity));

            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO1= new ResponseDTO();
            responseDTO1.setResponse("Error aL actualizar: "+ e.getMessage() );
            return new ResponseEntity<>(responseDTO1.getResponse(),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        ResponseDTO responseDTO= new ResponseDTO();
        try{
            userServiceI.sendVerificationCode(forgetPasswordDTO.getEmail());
            responseDTO.setResponse("Código de verificación enviado.");
            return ResponseEntity.ok(responseDTO);
        }catch (Exception e){

            responseDTO.setResponse(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.NOT_ACCEPTABLE);

        }

    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> requestPasswordReset(@RequestBody ResponseCodeDTO responseCodeDTO) {
        ResponseDTO responseDTO= new ResponseDTO();

        try{

            String response = userServiceI.verifyCode(responseCodeDTO.getEmail(), responseCodeDTO.getCode());
            responseDTO.setResponse(response);
            return ResponseEntity.ok(responseDTO);
        }catch (Exception e){
            responseDTO.setResponse(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.NOT_ACCEPTABLE);
        }
    }



}
