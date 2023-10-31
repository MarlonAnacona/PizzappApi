package com.API.Pizzapp.Services.Impl;


import com.API.Pizzapp.Models.AuthResponse;
import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.UserEntity;
import com.API.Pizzapp.Repository.UserRepository;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.UserServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceI {



    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public AuthResponse createUser(UserEntity userEntity) {
        UserEntity user = UserEntity.builder()
                .email(userEntity.getEmail())
                .password(passwordEncoder.encode( userEntity.getPassword()))
                .nombre(userEntity.getNombre())
                .apellido(userEntity.getApellido())
                .nombreUsuario(userEntity.getNombreUsuario())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

    @Override
    public AuthResponse loginUser(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        UserDetails user= (UserDetails) userRepository.findByEmail(loginDTO.getEmail()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

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
