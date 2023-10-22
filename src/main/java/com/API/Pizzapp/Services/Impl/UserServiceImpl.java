package com.API.Pizzapp.Services.Impl;


import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.UserEntity;
import com.API.Pizzapp.Repository.UserRepository;
import com.API.Pizzapp.Services.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceI {


    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String createUser(UserEntity userEntity) {
        UserEntity savedUser = userRepository.save(userEntity);
        if (savedUser != null && savedUser.getId() != null) {
            return "Usuario creado exitosamente " ;
        } else {
            return "Error al crear el usuario";
        }
    }

    @Override
    public UserEntity loginUser(LoginDTO loginDTO) {
        return userRepository.findByEmailAndPassword(loginDTO.getEmail(),loginDTO.getPassword());

    }

    @Override
    public UserEntity updateUser(Long id, UserEntity userUpdates) {
        return userRepository.findById(id).map(existingUser -> {

            if (userUpdates.getNombre() != null) {
                existingUser.setNombre(userUpdates.getNombre());
            }

            if (userUpdates.getEmail() != null) {
                existingUser.setEmail(userUpdates.getEmail());
            }

            if (userUpdates.getNombreUsuario() != null) {
                existingUser.setNombreUsuario(userUpdates.getNombreUsuario());
            }

            if (userUpdates.getPassword() != null) {
                existingUser.setPassword(userUpdates.getPassword());
            }

            if (userUpdates.getApellido() != null) {
                existingUser.setApellido(userUpdates.getApellido());
            }

            return userRepository.save(existingUser);

        }).orElse(null);
    }

}
