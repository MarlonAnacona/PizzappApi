package com.API.Pizzapp.Services;

import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.UserEntity;


public interface UserServiceI {


    public String createUser(UserEntity userEntity) ;

    public UserEntity loginUser(LoginDTO loginDTO);

    public UserEntity updateUser(Long id, UserEntity userUpdates);

}
