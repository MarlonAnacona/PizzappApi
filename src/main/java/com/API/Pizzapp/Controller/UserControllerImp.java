package com.API.Pizzapp.Controller;


import com.API.Pizzapp.Models.*;
import com.API.Pizzapp.Security.JWTAutorizationFilter;
import com.API.Pizzapp.Security.JwtAuthenticationFilter;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/User")
@CrossOrigin("*")
public class UserControllerImp implements  UserControllerI{


    UserServiceI userServiceI;

    JwtAuthenticationFilter jwtAutorizationFilter;

    JwtService jwtService;
    @Autowired
    public UserControllerImp(UserServiceI userServiceI, JwtAuthenticationFilter jwtAutorizationFilter, JwtService jwtService) {
        this.userServiceI = userServiceI;
        this.jwtAutorizationFilter = jwtAutorizationFilter;
        this.jwtService = jwtService;
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
            return new ResponseEntity<>(responseDTO1, HttpStatus.NOT_ACCEPTABLE);
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



    @RequestMapping(method = RequestMethod.PATCH, path = "/updateUser")
    public ResponseEntity updateUser(HttpServletRequest request,@RequestBody UserEntity userEntity) {

        UserEntity responseDTO;
        try {
            String token= jwtAutorizationFilter.getTokenFromRequest(request);
            String email= jwtService.getUsernameFromToken(token);
            responseDTO = (userServiceI.updateUser(email,userEntity));

            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO1= new ResponseDTO();
            responseDTO1.setResponse("Error aL actualizar: "+ e.getMessage() );
            return new ResponseEntity<>(responseDTO1,HttpStatus.NOT_ACCEPTABLE);
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


    @RequestMapping(method = RequestMethod.PATCH, path = "/updatePassword/{email}")
    public ResponseEntity changePassword(@PathVariable  String email, @RequestBody UserEntity userEntity) {

        UserEntity responseDTO;
        try {

            responseDTO = (userServiceI.updateUser(email,userEntity));

            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO1= new ResponseDTO();
            responseDTO1.setResponse("Error aL actualizar: "+ e.getMessage() );
            return new ResponseEntity<>(responseDTO1,HttpStatus.NOT_ACCEPTABLE);
        }
    }



    @RequestMapping(method = RequestMethod.GET, path = "/deleteUser")
    public ResponseEntity desactiveUser(HttpServletRequest request) {

        UserEntity responseDTO;
        try {
            String token= jwtAutorizationFilter.getTokenFromRequest(request);
            String email= jwtService.getUsernameFromToken(token);
            responseDTO = (userServiceI.deleteUser(email));

            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO1= new ResponseDTO();
            responseDTO1.setResponse("Error aL actualizar: "+ e.getMessage() );
            return new ResponseEntity<>(responseDTO1,HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/activeUser/{email}")
    public ResponseEntity activeUser(@PathVariable  String email  ) {

        UserEntity responseDTO;
        try {

            responseDTO = (userServiceI.activeUser(email));

            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO1= new ResponseDTO();
            responseDTO1.setResponse("Error aL actualizar: "+ e.getMessage() );
            return new ResponseEntity<>(responseDTO1,HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getUser")
    public ResponseEntity getImage(HttpServletRequest request) {

        UserGetDTO responseDTO;
        try {

            String token= jwtAutorizationFilter.getTokenFromRequest(request);
            String email= jwtService.getUsernameFromToken(token);
            responseDTO = (userServiceI.getUserEmail(email));

            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO1= new ResponseDTO();
            responseDTO1.setResponse("Error al cargar datos: "+ e.getMessage() );
            return new ResponseEntity<>(responseDTO1,HttpStatus.NOT_ACCEPTABLE);
        }
    }


}
