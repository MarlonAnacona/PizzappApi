package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerI {



    public ResponseEntity createUser(@RequestBody UserEntity userEntity);

    public ResponseEntity loginUser(@RequestBody LoginDTO loginDTO) ;

    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserEntity userEntity) ;


}
