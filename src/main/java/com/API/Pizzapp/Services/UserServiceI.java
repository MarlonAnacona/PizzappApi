package com.API.Pizzapp.Services;

import com.API.Pizzapp.Models.AuthResponse;
import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.UserEntity;
import com.API.Pizzapp.Models.UserGetDTO;


public interface UserServiceI {


    public AuthResponse createUser(UserEntity userEntity) ;

    public AuthResponse loginUser(LoginDTO loginDTO) throws Exception;

    public UserEntity updateUser(String email, UserEntity userUpdates) throws Exception;


    public UserEntity deleteUser(String email ) throws Exception;
    public UserEntity activeUser(String email ) throws Exception;


    public String sendVerificationCode(String email) throws Exception;

    public String verifyCode(String email, String code) throws Exception;

    public UserGetDTO getUserEmail(String email) throws Exception;

}
