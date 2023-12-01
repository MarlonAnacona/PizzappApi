package com.API.Pizzapp.controllerTest;

import com.API.Pizzapp.Models.AuthResponse;
import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.UserEntity;
import com.API.Pizzapp.Repository.UserRepository;
import com.API.Pizzapp.Services.Impl.UserServiceImpl;
import com.API.Pizzapp.Services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtService.getToken(any(UserDetails.class))).thenReturn("someToken");

    }

    @Test
    public void testCreateUserSuccess() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        AuthResponse response = userService.createUser(new UserEntity());
        assertNotNull(response.getToken()); // Asegurarse de que el token no sea nulo
    }

    @Test
    public void testCreateUserFailure() {
        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Error al crear el usuario"));

        assertThrows(RuntimeException.class, () -> userService.createUser(new UserEntity()));
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@email.com");
        loginDTO.setPassword("password");

        UserEntity user = new UserEntity();
        user.setActive(true); // Marcar al usuario como activo
        user.setPassword(passwordEncoder.encode("password"));
        when(passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())).thenReturn(true);
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));

        AuthResponse response = userService.loginUser(loginDTO);
        assertNotNull(response.getToken()); // Asegurarse de que el token no sea nulo
    }


    @Test
    public void testLoginUserFailure() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("wrong@email.com");
        loginDTO.setPassword("wrongpassword");

        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.loginUser(loginDTO));
    }

    @Test
    public void testUpdateUserSuccess() throws Exception {
        String id = "test@example.com";
        UserEntity existingUser = new UserEntity();
        existingUser.setNombre("Existing Name");
        existingUser.setEmail("Existing email");
        existingUser.setPassword("Existing password");
        existingUser.setApellido("Existing lastanme");
        existingUser.setNombreUsuario("Existing UserName");
        existingUser.setActive(true);

        UserEntity userUpdates = new UserEntity();
        userUpdates.setNombre("New Name");
        userUpdates.setEmail("New email");
        userUpdates.setPassword("New password");
        userUpdates.setApellido("New lastanme");
        userUpdates.setNombreUsuario("New UserName");

        when(userRepository.findByEmail(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserEntity updatedUser = userService.updateUser(id, userUpdates);
        assertEquals("New Name", updatedUser.getNombre());
    }

    @Test
    public void testUpdateUserNotFound() {
        String id = "null"; // Supongamos que "null" es un email que no existe en la base de datos.

        when(userRepository.findByEmail(id)).thenReturn(Optional.empty());

        // Debes usar assertThrows para verificar que el método arroje una excepción.
        assertThrows(Exception.class, () -> {
            userService.updateUser(id, new UserEntity());
        });
    }

}
