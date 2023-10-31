package com.API.Pizzapp.controllerTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.API.Pizzapp.Controller.UserControllerImp;
import com.API.Pizzapp.Models.AuthResponse;
import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.ResponseDTO;
import com.API.Pizzapp.Models.UserEntity;
import com.API.Pizzapp.Services.UserServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    @InjectMocks
    private UserControllerImp userControllerImp;

    @Mock
    private UserServiceI userServiceI;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserEntity user = new UserEntity();
        user.setNombre("Test");

        AuthResponse mockAuthResponse = new AuthResponse();
        mockAuthResponse.setToken("testToken");

        when(userServiceI.createUser(any(UserEntity.class))).thenReturn(mockAuthResponse);

        ResponseEntity result = userControllerImp.createUser(user);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.getBody() instanceof AuthResponse);
    }

    @Test
    public void testLoginUser() {
        LoginDTO login = new LoginDTO();
        login.setEmail("test@example.com");
        login.setPassword("password123");

        AuthResponse mockAuthResponse = new AuthResponse();
        mockAuthResponse.setToken("testToken");

        when(userServiceI.loginUser(any(LoginDTO.class))).thenReturn(mockAuthResponse);

        ResponseEntity result = userControllerImp.loginUser(login);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.getBody() instanceof AuthResponse);
    }
    @Test
    public void testUpdateUser() {
        UserEntity user = new UserEntity();
        user.setNombre("Updated Name");

        when(userServiceI.updateUser(any(Long.class), any(UserEntity.class))).thenReturn(user);

        ResponseEntity result = userControllerImp.updateUser(1L, user);

        assertEquals(CREATED, result.getStatusCode());
        assertTrue(result.getBody() instanceof UserEntity);
    }

    @Test
    public void testCreateUserInvalidInput() {
        // Simula un error al intentar crear un usuario
        when(userServiceI.createUser(null)).thenThrow(new RuntimeException());

        ResponseEntity response = userControllerImp.createUser(null);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void testLoginUserInvalidInput() {
        // Simula un error al intentar iniciar sesión
        when(userServiceI.loginUser(null)).thenThrow(new RuntimeException());

        ResponseEntity response = userControllerImp.loginUser(null);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void testUpdateUserInvalidInput() {
        // Simula un error al intentar actualizar un usuario
        Long invalidId = 1L;
        when(userServiceI.updateUser(invalidId, null)).thenThrow(new RuntimeException());

        ResponseEntity response = userControllerImp.updateUser(invalidId, null);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }
}
