package com.API.Pizzapp.Services;

import com.API.Pizzapp.Models.AuthResponse;
import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.UserEntity;


public interface UserServiceI {


    public AuthResponse createUser(UserEntity userEntity) ;

    public AuthResponse loginUser(LoginDTO loginDTO) throws Exception;

    public UserEntity updateUser(Long id, UserEntity userUpdates);


    public String sendVerificationCode(String email);

    public String verifyCode(String email, String code) throws Exception;
}
