package com.API.Pizzapp.controllerTest;

import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.UserEntity;
import com.API.Pizzapp.Repository.UserRepository;
import com.API.Pizzapp.Services.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserSuccess() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        String response = userService.createUser(new UserEntity());
        assertEquals("Usuario creado exitosamente ", response);
    }

    @Test
    public void testCreateUserFailure() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());

        String response = userService.createUser(new UserEntity());
        assertEquals("Error al crear el usuario", response);
    }

    @Test
    public void testLoginUserSuccess() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@email.com");
        loginDTO.setPassword("password");

        UserEntity user = new UserEntity();
        when(userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword())).thenReturn(user);

        UserEntity response = userService.loginUser(loginDTO);
        assertEquals(user, response);
    }

    @Test
    public void testLoginUserFailure() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("wrong@email.com");
        loginDTO.setPassword("wrongpassword");

        when(userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword())).thenReturn(null);

        UserEntity response = userService.loginUser(loginDTO);
        assertEquals(null, response);
    }

    @Test
    public void testUpdateUserSuccess() {
        Long id = 1L;
        UserEntity existingUser = new UserEntity();
        existingUser.setNombre("Existing Name");
        existingUser.setEmail("Existing email");
        existingUser.setPassword("Existing password");
        existingUser.setApellido("Existing lastanme");
        existingUser.setNombreUsuario("Existing UserName");


        UserEntity userUpdates = new UserEntity();
        userUpdates.setNombre("New Name");
        userUpdates.setEmail("New email");
        userUpdates.setPassword("New password");
        userUpdates.setApellido("New lastanme");
        userUpdates.setNombreUsuario("New UserName");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserEntity updatedUser = userService.updateUser(id, userUpdates);
        assertEquals("New Name", updatedUser.getNombre());
    }

    @Test
    public void testUpdateUserNotFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserEntity response = userService.updateUser(id, new UserEntity());
        assertEquals(null, response);
    }
}
