package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.ResponseCodeDTO;
import com.API.Pizzapp.Models.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerI {


    public ResponseEntity createUser(@RequestBody UserEntity userEntity);

    public ResponseEntity loginUser(@RequestBody LoginDTO loginDTO);

    public ResponseEntity updateUser(HttpServletRequest request, @RequestBody UserEntity userEntity);

    public ResponseEntity<?> requestPasswordReset(@RequestBody ResponseCodeDTO responseCodeDTO);

    public ResponseEntity changePassword(String request, @RequestBody UserEntity userEntity);

    public ResponseEntity desactiveUser(HttpServletRequest request);

    public ResponseEntity activeUser(String email);

    public ResponseEntity getImage(HttpServletRequest request);


}