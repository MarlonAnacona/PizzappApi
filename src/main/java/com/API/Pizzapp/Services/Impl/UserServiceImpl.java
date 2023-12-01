package com.API.Pizzapp.Services.Impl;


import com.API.Pizzapp.Models.*;
import com.API.Pizzapp.Repository.UserRepository;
import com.API.Pizzapp.Repository.VerificationCodeRepository;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.UserServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserServiceI {



    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private VerificationCodeRepository codeRepository;

    private EmailServiceImpl emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, VerificationCodeRepository codeRepository, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.codeRepository = codeRepository;
        this.emailService = emailService;
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
        user.setActive(true);
        user.setBlocked(false);
        userRepository.save(user);
        UserDetails userDetails = new UserDetailsImpl(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(userDetails))
                .build();

    }

    @Override
    public AuthResponse loginUser(LoginDTO loginDTO) throws Exception {
        UserEntity user = findUserBlock(loginDTO.getEmail());

        String storedPassword = user.getPassword();

        // Compara la contraseña ingresada por el usuario con la contraseña almacenada en la base de datos
        boolean passwordMatches = passwordEncoder.matches(loginDTO.getPassword(), storedPassword);

        if (!passwordMatches) {
            throw new Exception("Contraseña incorrecta");
        }
        UserDetails userDetails = new UserDetailsImpl(user);
        String token = jwtService.getToken(userDetails);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public boolean isUserBlocked(String email) {
        UserEntity user = userRepository.findByEmail(email).orElse(null);
        if (user != null && user.isBlocked()) {
            if (user.getBlockExpiryTime().isAfter(LocalDateTime.now())) {
                return true;
            } else {
                user.setBlocked(false);
                userRepository.save(user);
            }
        }
        return false;
    }


    @Override
    public UserEntity updateUser(String email, UserEntity userUpdates) throws Exception {
        UserEntity user = findUserBlock(email);

        return userRepository.findByEmail(email).map(existingUser -> {

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
                existingUser.setPassword(passwordEncoder.encode(userUpdates.getPassword()));
            }

            if (userUpdates.getApellido() != null) {
                existingUser.setApellido(userUpdates.getApellido());
            }
            if(userUpdates.getProfilePicture() !=null){
            existingUser.setProfilePicture(userUpdates.getProfilePicture());
            }

            return userRepository.save(existingUser);

        }).orElse(null);
    }

    @Override
    public UserEntity deleteUser(String email) throws Exception {
        UserEntity user = findUserBlock(email);

        return userRepository.findByEmail(email).map(existingUser -> {
            if(existingUser.isActive()){
                existingUser.setActive(false);
            }

            return userRepository.save(existingUser);

        }).orElse(null);    }

    @Override
    public UserEntity activeUser(String email ) throws Exception {

        return userRepository.findByEmail(email).map(existingUser -> {
            if(!existingUser.isActive()){
                existingUser.setActive(true);
            }

            return userRepository.save(existingUser);

        }).orElse(null);
    }

    public String sendVerificationCode(String email) throws Exception {
        UserEntity user = findUserBlock(email);

        return userRepository.findByEmail(email).map(existingUser -> {
            CodeVerification code = new CodeVerification();
             code.setUser(existingUser);
             code.setCode(generateRandomCode());
             code.setExpiryDate(LocalDateTime.now().plusMinutes(15));
             code.setUsed(false);
            codeRepository.save(code);

            emailService.sendEmail(
                    email,
                    "Tu código de verificación",
                     code.getCode()
            );
        return  "Envio exitoso";
        }).orElse("No se encuentra el correo registrado");
    }

    public String verifyCode(String email, String code) throws Exception {
        // Encuentra el usuario por email en lugar de buscar por código.
        UserEntity user = findUserBlock(email);

        Optional<CodeVerification> verificationCodeOpt = codeRepository.findByCodeAndUserEmail(code, email);

        if(user.getAttemptCount()>=3) {
            user.setAttemptCount(0);
            userRepository.save(user);
        }
        if (verificationCodeOpt.isPresent() ) {

            CodeVerification vc = verificationCodeOpt.get();

            // Comprobaciones de uso, expiración, etc.
            if (vc.isUsed()) {
                throw new Exception("Código ya utilizado.");
            }

            if (vc.getExpiryDate().isBefore(LocalDateTime.now())) {
                throw new Exception("Código expirado.");
            }

            vc.setUsed(true);
            codeRepository.save(vc);

            user.setAttemptCount(0);
            userRepository.save(user);


            return "Código verificado con éxito.";
        } else {
            int attempts = user.getAttemptCount() + 1;
            user.setAttemptCount(attempts);
            if (attempts >= 3) {
                user.setBlocked(true);
                user.setBlockExpiryTime(LocalDateTime.now().plusMinutes(30));
            }
            userRepository.save(user);
            throw new Exception("Código incorrecto. Intentos restantes: " + (3 - attempts));

        }

    }

    private UserEntity findUserBlock(String email) throws Exception {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuario no encontrado."));

        // Comprueba si el usuario ya está bloqueado.
        if (user.isBlocked() && user.getBlockExpiryTime().isAfter(LocalDateTime.now())) {
            throw new Exception("Usuario bloqueado. Por favor, espera hasta que el bloqueo expire.");
        }

        if(!user.isActive()){
            throw new Exception("Usuario inactivo");

        }


        return user;
    }


    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        int randomCode = 1000 + random.nextInt(9000); // 1000 (inclusive) a 9999 (inclusive)
        return String.valueOf(randomCode);
    }


    public UserGetDTO getUserEmail(String email) throws Exception {
        UserEntity user = findUserBlock(email);
        UserGetDTO userGetDTO= new UserGetDTO();
        userGetDTO.setNombre(user.getNombre());
        userGetDTO.setApellido(user.getApellido());
        userGetDTO.setEmail(user.getEmail());
        userGetDTO.setProfilePicture(user.getProfilePicture());
        userGetDTO.setNombreUsuario(user.getNombreUsuario());


       return  userGetDTO;
    }

}
